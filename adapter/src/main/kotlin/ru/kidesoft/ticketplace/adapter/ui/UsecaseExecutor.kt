package ru.kidesoft.ticketplace.adapter.ui

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
import ru.kidesoft.ticketplace.adapter.ui.handler.Handler
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

object UsecaseExecutor {
    lateinit var commonPort: CommonPort

    lateinit var defaultHandler: DefaultHandler

    private val logger = LogManager.getLogger(this.javaClass)

    val usecaseMap = mutableMapOf<KClass<out Usecase<*, *>>, Usecase<*, *>>()

    fun registerUsecase(vararg usecase: Usecase<*, *>) {
        usecase.forEach { uc -> usecaseMap[uc::class] = uc }
    }

    class Executor() {
        var handler: Handler? = defaultHandler
        var maxAttempts: Int = 1

        fun <I,O> execute(clazz: KClass<out Usecase<I, O>>, input : I? = null, sceneManager: SceneManager? = null) : O? {
            val usecase = clazz.java.getConstructor(CommonPort::class.java).newInstance(commonPort)
            return execute(usecase, input, sceneManager)
        }

        private fun <I, O> execute(
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