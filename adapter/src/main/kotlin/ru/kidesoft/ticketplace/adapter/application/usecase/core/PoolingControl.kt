package ru.kidesoft.ticketplace.adapter.application.usecase.core

import kotlinx.coroutines.*
import net.datafaker.providers.base.Bool
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import java.time.Duration
import kotlin.math.log

// Значение ключа для текущей Job-операции
const val PoolingServiceJobName = "poolingServiceJob"

class PoolingServiceControl(commonPort: CommonPort) :
    Usecase<PoolingServiceControl.PoolingCommand, Boolean>(commonPort) {

    enum class PoolingCommand {
        STOP, START, RESTART, SMART, CHECKSTATE
    }

    private fun Job.cancelPool() {
        val cancelException = CancellationException("завершение работы службы $PoolingServiceJobName пользователем: ${Job.Key}")
        this.cancel(cancelException)
        commonPort.jobPort.deleteJob(PoolingServiceJobName)
        logger.warn("Завершение работы задачи опроса сервера: $cancelException")

    }

    override suspend fun invoke(input: PoolingCommand?, sceneManager: SceneManager?): Boolean {
        val command = input ?: PoolingCommand.SMART

        when (command) {
            PoolingCommand.STOP -> kotlin.runCatching { stop(sceneManager) }
                .onFailure { logger.warn("Закрытие службы завершилось неуспешно: ${it.message}") }

            PoolingCommand.RESTART -> kotlin.runCatching { restart(sceneManager) }
                .onFailure { logger.warn("Перезагрузка службы завершилась неудачно: ${it.message}") }

            PoolingCommand.START -> kotlin.runCatching { start(sceneManager) }
                .onFailure { logger.warn("Запуск службы завершился неудачно: ${it.message}") }

            PoolingCommand.SMART -> kotlin.runCatching { smart(sceneManager) }
                .onFailure { logger.warn("Умное переключение состояния службы завершилось неудачно: ${it.message}") }

            PoolingCommand.CHECKSTATE -> return checkState()
        }

        val state = commonPort.jobPort.getJob(PoolingServiceJobName)?.isActive ?: false

        sceneManager?.let {
            present(state, sceneManager)
        }

        return state
    }

    override fun present(output: Boolean, sceneManager: SceneManager) {
        // sceneManager.getPresenter(MainPresenter::class)?.setPoolingStatus(output)
    }

    private fun checkState(): Boolean {
        return commonPort.jobPort.getJob(PoolingServiceJobName)?.isActive ?: false
    }

    private fun start(sceneManager: SceneManager? = null) {
        val setting = commonPort.databasePort.getSetting().getByCurrent() ?: run {
            throw IllegalArgumentException("Не найдены настройки по текущему пользователю")
        }

        val job = createJob(sceneManager, setting.serverSetting.requestInterval)

        job.start()

        commonPort.jobPort.saveJob(PoolingServiceJobName, job)

        sceneManager?.showNotification(
            NotificationType.INFO,
            "Служба опроса сервера",
            "Служба опроса сервера успешно запущена"
        )


        logger.info("Задача опроса сервера успешно запущена. Настройки интервала: ${setting.serverSetting.requestInterval}")
    }

    private fun stop(sceneManager: SceneManager? = null) {
        val job = commonPort.jobPort.getJob(PoolingServiceJobName)?.cancelPool() ?: throw IllegalStateException("Служба не найдена и/или уже остановлена")

        sceneManager?.showNotification(
            NotificationType.INFO,
            "Служба опроса сервера",
            "Служба опроса сервера успешно остановлена"
        )

    }

    private fun restart(sceneManager: SceneManager?) {

        if (commonPort.jobPort.getJob(PoolingServiceJobName)?.isActive == true) {
            stop()
        }

        start()

        sceneManager?.showNotification(
            NotificationType.INFO,
            "Служба опроса сервера",
            "Служба опроса сервера успешно перезапущена"
        )
    }

    private fun smart(sceneManager: SceneManager?) {
        if (commonPort.jobPort.getJob(PoolingServiceJobName) == null)
            start(sceneManager)
        else
            stop(sceneManager)
    }

    private fun createJob(sceneManager: SceneManager?, interval: Duration): Job {
        val job =  CoroutineScope(Dispatchers.Default).launch {
            var counter = 0

            val setting = commonPort.databasePort.getSetting().getByCurrent() ?: throw IllegalStateException("Нет текущего активного пользователя")

            while (isActive) {
                kotlin.runCatching {
                    ClickProcess(commonPort).invoke(sceneManager = sceneManager)
                    runBlocking { delay(setting.serverSetting.requestInterval.toMillis()) }
                }.onFailure {
                    logger.warn("Во время работы службы опроса удаленного сервера произошла ошибка: ${it.message}")
                    if (counter >= 5) {
                        this.cancel("Превышено количество неудачных обращений, служба отключена", it)
                    } else {
                        counter++
                    }
                }.onSuccess {
                    counter = 0
                }
            }
        }

        return job

        //.also { job ->
        //            job.invokeOnCompletion {
        //                commonPort.jobPort.deleteJob(PoolingServiceJobName)
        //                sceneManager?.getPresenter(MainPresenter::class)?.setPoolingStatus(false)
        //                logger.warn("Завершение работы задачи опроса сервера: $it")
        //            }
        //
        //        }
    }
}