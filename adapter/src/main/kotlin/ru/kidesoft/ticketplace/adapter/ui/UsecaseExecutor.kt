package ru.kidesoft.ticketplace.adapter.ui

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
import ru.kidesoft.ticketplace.adapter.ui.handler.Handler
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

object UsecaseExecutor {
    lateinit var defaultHandler: DefaultHandler

    private val logger = LogManager.getLogger(this.javaClass)

    val usecaseMap = mutableMapOf<KClass<out Usecase<*, *>>, Usecase<*, *>>()

    fun registerUsecase(vararg usecase: Usecase<*, *>) {
        usecase.forEach { uc -> usecaseMap[uc::class] = uc }
    }

    class Executor() {
        var handler: Handler? = defaultHandler
        var maxAttempts: Int = 1

        fun <I : Usecase.Input, O : Usecase.Output, UC : Usecase<I, O>> execute(
            usecaseClass: KClass<UC>,
            input: I? = null,
            sceneManager: SceneManager? = null
        ): O? {
            val useCase: UC = usecaseMap[usecaseClass] as? UC ?: run {
                val usecaseClassName = usecaseClass.qualifiedName
                val errorMessage = "Usecase is not registered: $usecaseClassName"
                logger.error("Во время выполнения метода варианта использования $usecaseClassName произошла ошибка: $errorMessage")
                throw RuntimeException(errorMessage)
            }

            measureTimeMillis {
                return execute(useCase, input, sceneManager)
            }.let {
                logger.trace("Время выполнения метода варианта использования: ${usecaseClass.qualifiedName} через executor = $it милисекунд")
            }
        }

        private fun <I : Usecase.Input, O : Usecase.Output, UC : Usecase<I, O>> execute(
            usecase: Usecase<I, O>,
            input: I? = null, sceneManager: SceneManager? = null
        ): O? {


            for (i in 1..maxAttempts) {
                try {
                    return runBlocking {
                        usecase.invoke(input, sceneManager)
                    }
                } catch (e: Throwable) {
                    if (i == maxAttempts) {
                        logger.error("Во время выполнения use case произошла ошибка", e)
                        handler?.handle(e)?:logger.warn("Не установлен handler для обработки ошибки")
                        return null
                    }
                }
            }


            throw RuntimeException("Reach unreachable state")
        }
    }


}