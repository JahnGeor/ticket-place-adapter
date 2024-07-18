package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession

import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import java.time.LocalDateTime

class Login(private val databasePort: DatabasePort, private val apiFactory: ApiFactory) :
    _Usecase<Login.Input, Login.Output>() {
    private val logger = LogManager.getLogger(Login::class.java)

    class Input : _Usecase.Input {
        lateinit var email: String
        lateinit var password: String
        lateinit var url: String
    }

    class Output : _Usecase.Output {}

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {

        if (inputValues == null) {
            throw NullPointerException("${this::class.simpleName} Input cannot be null.")
        }

        // TODO: Сделать валидацию внутри метода

        val async = GlobalScope.async {
            logger.trace("Начато выполнение метода async@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
            apiFactory.getInstance(inputValues.url).login(inputValues.email, inputValues.password)
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

            databasePort.getLogin().GetLoginId(inputValues.email, inputValues.url)?.let {
                databasePort.getLogin()
                    .Update(it, loginExposed)
            } ?: run {
                databasePort.getLogin()
                    .Create(loginExposed)
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

            databasePort.getProfile().getByLoginId(loginId)?.let {
                databasePort.getProfile().Update(it.id, profileExposed)
            } ?: databasePort.getProfile().Create(profileExposed)
        }

        profileAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода profileAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        val sessionAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            val sessionExposed = async.await().mapToSession().apply {
                this.loginId = loginId
            }

            databasePort.getSession().getByLoginId(loginId)?.let {
                databasePort.getSession().update(it.id, sessionExposed)
            } ?: let {
                databasePort.getSession().create(sessionExposed)
            }
        }

        sessionAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        databasePort.getSession().setActive(sessionAsync.await().id)

        databasePort.getSetting().getByLoginId(loginId)?: databasePort.getSetting().createDefault(loginId) // Инициализация настроек по-умолчанию

        return Output()
    }


}