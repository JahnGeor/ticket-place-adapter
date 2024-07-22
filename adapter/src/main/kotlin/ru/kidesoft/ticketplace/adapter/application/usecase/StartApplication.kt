package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.login.ReactivateAuthorization
import ru.kidesoft.ticketplace.adapter.application.usecase.web.GetWebPort
import java.time.ZonedDateTime

class StartApplication(commonPort: CommonPort) : Usecase<StartApplication.Input, StartApplication.Output>(commonPort) {

    class Input
    class Output {
        var isActive : Boolean = false
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        try {
            val sessionActive = commonPort.databasePort.getSession().getActive() // COMM: получаем текущий сеанс.

            if (sessionActive == null) { // COMM: если сеанс нулевой, значит, выставляем isActive = false
                output.isActive = false
            } else { // COMM: если сеанс существует в базе, нужно проверить время истечения
                sessionActive.token?.expiredTime?.isBefore(ZonedDateTime.now())?.let { // COMM: если сеанс истек, то...
                    commonPort.databasePort.getSetting().getByCurrent() ?:
                    let { commonPort.databasePort.getSetting().createDefault(sessionActive.loginId) } // TODO: в дальнейшем - вынести в handler

                    if (it) {
                        ReactivateAuthorization(
                            commonPort
                        ).invoke(sceneManager = sceneManager) // COMM: ...выполняем обновление сеанса (для этого используем Usecase -> ReactivateAuthorization)
                        output.isActive = true // COMM: ... и выставляем значение как true
                    } else {
                        output.isActive = true // COMM: если сеанс не истек - просто выставляем значение true
                    }
                    // TODO: Переавторизуем
                } ?: throw NullPointerException("Some field of sessionActive is null") // COMM: если нет данных внутри сеанса - выставляем значение false
            }

        } catch (e : Exception) {
            logger.warn("Возникла некритичная ошибка старта приложения: $e")
            output.isActive = false
        }

        // COMM: Проверяем наличие настроек по активному пользователю:


        if (output.isActive) {
            val click = GetWebPort(commonPort).invoke(sceneManager = sceneManager).webPort.getClick().mapToEntity()
            commonPort.databasePort.getClick().save(click)

            try {
                StartKktSession(commonPort).invoke()
            } catch (e : Exception) {
                logger.error("Во время активации сессии ККТ произошла ошибка: $e")
            }

            try {
                PoolingService(commonPort).invoke()
            } catch (e: Throwable) {
                logger.error("Во время старта службы прослушивания удаленного сервера произошла ошибка: $e")
            }


        }


        

        sceneManager?.let {
            present(output, it)
        }

        return output


    }

    override fun present(output: Output, sceneManager: SceneManager) {
        if (output.isActive) sceneManager.openScene(Scene.MAIN) else sceneManager.openScene(Scene.AUTH)
    }
}