package ru.kidesoft.ticketplace.adapter.ui

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
import ru.kidesoft.ticketplace.adapter.ui.handler.Handler
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

object UsecaseExecutor {
    private val logger = LogManager.getLogger(this.javaClass)

    val usecaseMap = mutableMapOf<KClass<out _Usecase<*, *>>, _Usecase<*, *>>()

    fun registerUsecase(vararg usecase: _Usecase<*, *>) {
        usecase.forEach { uc -> usecaseMap[uc::class] = uc }
    }

    class Executor(val presenter: Presenter) {
        var handler: Handler? = DefaultHandler() // TODO: DefaultHandler()
        var maxAttempts: Int = 1

        // Функция возвращает данные от Usecase
        fun <I : _Usecase.Input, O : _Usecase.Output, UC : _Usecase<I, O>> get(
            usecaseClass: KClass<UC>,
            input: I? = null
        ): O? {
            var result: O? = null
            measureTimeMillis {
                val usecase: UC =
                    usecaseMap[usecaseClass] as? UC
                        ?: throw RuntimeException("Usecase is not registered: $usecaseClass")


                result = execute(usecase, input)
            }.let {
                logger.trace("Время выполнения метода варианта использования: ${usecaseClass.qualifiedName} через executor GET = $it милисекунд")
            }

            return result
        }


        // Функция с presenter
        fun <I : _Usecase.Input, O : _Usecase.Output, UC : _Usecase<I, O>> present(
            usecaseClass: KClass<UC>,
            input: I? = null,
        ) {
            measureTimeMillis {
                logger.trace("Запускается executor: $usecaseClass, параметры: input: $input")

                val usecase =
                    usecaseMap[usecaseClass] as? UC
                        ?: throw RuntimeException("Usecase is not registered: $usecaseClass")

                execute(usecase, input)?.let {
                    usecase.present(it, presenter.getSceneManager())
                }
            }.let {
                logger. trace("Время выполнения метода варианта использования: ${usecaseClass.qualifiedName} через executor PRESENT = $it милисекунд")
            }

        }


        private fun <I : _Usecase.Input, O : _Usecase.Output, UC : _Usecase<I, O>> execute(
            usecase: _Usecase<I, O>,
            input: I? = null
        ): O? {


            for (i in 1..maxAttempts) {
                try {
                    return runBlocking {
                        usecase.execute(input)
                    }
                } catch (e: Throwable) {
                    if (i == maxAttempts) {
                        logger.error("Во время выполнения use case произошла ошибка", e)
                        handler?.handle(e, presenter)
                        return null
                    }
                }
            }


            throw RuntimeException("Reach unreachable state")
        }
    }


}