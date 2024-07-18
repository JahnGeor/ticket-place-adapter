package ru.kidesoft.ticketplace.adapter.application.usecase

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login
import ru.kidesoft.ticketplace.adapter.application.usecase.login.ReactivateAuthorization
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateAbout.Output
import java.time.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime

class StartApplication(val databasePort: DatabasePort, val apiFactory: ApiFactory, val kktPortFactory: KktPortFactory) : _Usecase<StartApplication.Input, StartApplication.Output>() {

    private val logger = LogManager.getLogger()

    class Input : _Usecase.Input {}
    class Output : _Usecase.Output {
        var isActive : Boolean = false
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        try {
            val sessionActive = databasePort.getSession().getActive() // COMM: получаем текущий сеанс.

            if (sessionActive == null) { // COMM: если сеанс нулевой, значит, выставляем isActive = false
                output.isActive = false
            } else { // COMM: если сеанс существует в базе, нужно проверить время истечения
                sessionActive.token?.expiredTime?.isBefore(ZonedDateTime.now())?.let { // COMM: если сеанс истек, то...
                    databasePort.getSetting().getByCurrentUser() ?:
                    let { databasePort.getSetting().createDefault(sessionActive.loginId) } // TODO: в дальнейшем - вынести в handler

                    if (it) {
                        ReactivateAuthorization(
                            databasePort,
                            apiFactory
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
            StartKktSession(databasePort, kktPortFactory).invoke()
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