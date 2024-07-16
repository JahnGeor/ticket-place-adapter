package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartSessionUsecase

import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import java.time.LocalDateTime

class LoginUsecase(private val apiFactory: ApiFactory, private val databasePort: DatabasePort, val kktPortFactory: KktPortFactory) :
    _Usecase<LoginUsecase.Input, LoginUsecase.Output>() {
    private val logger = LogManager.getLogger(LoginUsecase::class.java)

    class Input : _Usecase.Input {
        lateinit var email: String
        lateinit var password: String
        lateinit var url: String
    }

    class Output : _Usecase.Output {}

    override suspend fun execute(inputValues: Input?, sceneManager: SceneManager?): Output {

        if (inputValues == null) {
            throw NullPointerException("${this::class.simpleName} Input cannot be null.")
        }

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


        val profileExposed = async.await().mapToProfile().apply {
            loginId = asyncLogin.await().id
        }

        val profileAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода profileAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            databasePort.getProfile().getByLoginId(asyncLogin.await().id)?.let {
                databasePort.getProfile().Update(it.id, profileExposed)
            } ?: databasePort.getProfile().Create(profileExposed)
        }

        profileAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода profileAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        val sessionAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")

            val sessionExposed = async.await().mapToSession().apply {
                loginId = asyncLogin.await().id
            }

            databasePort.getSession().getByLoginId(asyncLogin.await().id)?.let {
                databasePort.getSession().update(it.id, sessionExposed)
            } ?: let {
                databasePort.getSession().create(sessionExposed)
            }
        }

        sessionAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода sessionAsync@${this::class.simpleName} : ${LocalDateTime.now().toLocalTime()}")
        }

        databasePort.getSession().setActive(sessionAsync.await().id)

        StartSessionUsecase(databasePort, kktPortFactory).execute() // Start Kkt Session

        return Output()
    }


}