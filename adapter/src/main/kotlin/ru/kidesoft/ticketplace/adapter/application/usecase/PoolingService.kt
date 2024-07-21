package ru.kidesoft.ticketplace.adapter.application.usecase

import kotlinx.coroutines.*
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager

// Значение ключа для текущей Job-операции
const val PoolingServiceJobName = "poolingServiceJob"

/** Вариант использования, обеспечивающий прослушивание удаленного сервера текущим пользователем, обработку полученных данных и печать
 *
 */
//class PoolingServiceStart(commonPort: CommonPort) : Usecase<PoolingServiceStart.Input, PoolingServiceStart.Output>(commonPort) {
//    class Input : Usecase.Input
//
//    class Output : Usecase.Output
//
//    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
//
//        commonPort.jobPort.saveJob(PoolingServiceJobName, job)
//
//        val output = Output()
//
//        sceneManager?.let {
//            present(output, sceneManager)
//        }
//
//        return output
//    }
//
//
//    override fun present(output: Output, sceneManager: SceneManager) {
//        sceneManager.getPresenter(MainPresenter::class)?.setPoolingStatus(true)
//    }
//}
//
//class PoolingServiceStop(commonPort: CommonPort) : Usecase<PoolingServiceStop.Input, PoolingServiceStop.Output>(commonPort) {
//    class Input : Usecase.Input
//    class Output() : Usecase.Output
//
//    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
//        val output = Output()
//
//        commonPort.jobPort.getJob(PoolingServiceJobName)?.cancel()?: throw NullPointerException("Не удалось найти $PoolingServiceJobName задачу в репозитории задач")
//
//        sceneManager?.let {
//            present(output, it)
//        }
//
//        return output
//    }
//
//    override fun present(output: Output, sceneManager: SceneManager) {
//        sceneManager.getPresenter(MainPresenter::class)?.setPoolingStatus(false)
//    }
//}

class PoolingService(commonPort: CommonPort) : Usecase<PoolingService.Input, PoolingService.Output>(commonPort) {
    class Input : Usecase.Input {
        var tumbler: Boolean? = null
    }
    class Output : Usecase.Output {
        var state: Boolean? = null
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        var output = Output()

        commonPort.jobPort.getJob(PoolingServiceJobName)?.let {
            if (it.isActive) {
                stop(it).also { output.state = false }
            }
            else restart(it, sceneManager).also { output.state = true }
        } ?: start(sceneManager).also { output.state = true }

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    fun start(sceneManager: SceneManager?) {
        val job = CoroutineScope(Dispatchers.Default).launch {
            var counter = 0

            // Получаем настройки

            while (isActive) {
                kotlin.runCatching {
                    ClickProcess(commonPort).invoke(sceneManager = sceneManager)
                    println("Hello $counter")
                    delay(2000)
                }.onFailure {
                    if (counter >= 5) {
                        this.cancel("Превышено количество попыток", it)
                    } else {
                        counter++
                    }
                }.onSuccess {
                    counter = 0
                }

            }
        }

        job.invokeOnCompletion {
            it?.printStackTrace() // present error
        }

        commonPort.jobPort.saveJob(PoolingServiceJobName, job)
    }

    private fun stop(job : Job) {
        job.cancel()
        commonPort.jobPort.deleteJob(PoolingServiceJobName)
    }

    private fun restart(job: Job, sceneManager: SceneManager?) {
        job.cancel()
        start(sceneManager)
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        if (output.state == true) {
            sceneManager.showNotification(NotificationType.INFO, "Служба опроса удаленного сервера", "Служба запущена")
            sceneManager.getPresenter(MainPresenter::class)?.setPoolingStatus(true)
        } else {
            sceneManager.showNotification(NotificationType.INFO, "Служба опроса удаленного сервера", "Служба остановлена")
            sceneManager.getPresenter(MainPresenter::class)?.setPoolingStatus(false)
        }

    //sceneManager.showNotification(NotificationType.INFO, "Служба опроса удаленного сервера", "Служба остановлена")
    }

}

// Нужно написать общий кейс для запуска и остановки