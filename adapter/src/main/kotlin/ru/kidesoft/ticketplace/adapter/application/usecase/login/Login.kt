package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

import java.time.LocalDateTime

class Login(commonPort: CommonPort) :
    Usecase<Login.Input, Login.Output>(commonPort) {

    class Input {
        lateinit var email: String
        lateinit var password: String
        lateinit var url: String
    }

    class Output(var session: Session, var profile: Profile, var setting: Setting) {

    }

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {

        if (inputValues == null) {
            throw NullPointerException("${this::class.simpleName} Input cannot be null.")
        }

        // TODO: Сделать валидацию внутри метода

        val async = GlobalScope.async {
            logger.trace("Начато выполнение метода async@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
            commonPort.apiPortFactory.getInstance(inputValues.url).login(inputValues.email, inputValues.password)
        }

        async.invokeOnCompletion {
            logger.trace("Завершено выполнение метода async@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        val loginExposed = LoginExposed().apply {
            email = inputValues.email
            password = inputValues.password
            url = inputValues.url
        }

        val asyncLogin = GlobalScope.async {
            logger.trace("Начато выполнение метода asyncLogin@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            commonPort.databasePort.getLogin().getLoginId(inputValues.email, inputValues.url)?.let {
                commonPort.databasePort.getLogin()
                    .update(it, loginExposed)
            } ?: run {
                commonPort.databasePort.getLogin()
                    .create(loginExposed)
            }
        }

        asyncLogin.invokeOnCompletion {
            logger.trace("Завершено выполнение метода asyncLogin@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        val loginId = asyncLogin.await().id

        val profileExposed = async.await().mapToProfile().apply {
            this.loginId = loginId
        }

        val profileAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода profileAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            commonPort.databasePort.getProfile().getByLoginId(loginId)?.let {
                commonPort.databasePort.getProfile().update(it.id, profileExposed)
            } ?: commonPort.databasePort.getProfile().create(profileExposed)
        }

        profileAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода profileAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        val sessionAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            val sessionExposed = async.await().mapToSession().apply {
                this.loginId = loginId
            }

            commonPort.databasePort.getSession().getByLoginId(loginId)?.let {
                commonPort.databasePort.getSession().update(it.id, sessionExposed)
            } ?: let {
                commonPort.databasePort.getSession().create(sessionExposed)
            }
        }

        sessionAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        commonPort.databasePort.getSession().setActive(sessionAsync.await().id)

        var setting = commonPort.databasePort.getSetting().getByLoginId(loginId) ?: commonPort.databasePort.getSetting().createDefault(loginId) // Инициализация настроек по-умолчанию

        var output = Output(sessionAsync.getCompleted(), profileAsync.getCompleted(), setting)

        return output
    }


}