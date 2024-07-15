package ru.kidesoft.ticketplace.adapter.application.usecase.login

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.LoginData
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

class LoginUsecase(private val apiFactory: ApiFactory, private val databasePort: DatabasePort) :
    _Usecase<LoginUsecase.Input, LoginUsecase.Output, Presenter>() {
    private val logger = LogManager.getLogger(LoginUsecase::class.java)

    class Input : _Usecase.Input {
        lateinit var email: String
        lateinit var password: String
        lateinit var url: String
    }

    class Output : _Usecase.Output {}

    override suspend fun execute(inputValues: Input): Output {

        val async = GlobalScope.async {
            logger.trace("Начато выполнение метода async@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")
            apiFactory.getInstance(inputValues.url).login(inputValues.email, inputValues.password)
        }

        async.invokeOnCompletion {
            logger.trace("Завершено выполнение метода async@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")
        }

        val loginExposed = LoginExposed().apply {
            email = inputValues.email
            password = inputValues.password
            url = inputValues.url
        }

        val asyncLogin = GlobalScope.async {
            logger.trace("Начато выполнение метода asyncLogin@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")

            databasePort.getLogin().GetLoginId(inputValues.email, inputValues.url)?.let {
                databasePort.getLogin()
                    .Update(it, loginExposed)
            } ?: run {
                databasePort.getLogin()
                    .Create(loginExposed)
            }
        }

        asyncLogin.invokeOnCompletion {
            logger.trace("Завершено выполнение метода asyncLogin@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")
        }


        val profileExposed = async.await().mapToProfile().apply {
            loginId = asyncLogin.await().id
        }

        val profileAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода profileAsync@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")

            databasePort.getProfile().getByLoginId(asyncLogin.await().id)?.let {
                databasePort.getProfile().Update(it.id, profileExposed)
            } ?: databasePort.getProfile().Create(profileExposed)
        }

        profileAsync.invokeOnCompletion {
            logger.trace("Завершено выполнение метода profileAsync@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")
        }

        val sessionAsync = GlobalScope.async {
            logger.trace("Начато выполнение метода sessionAsync@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")

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
            logger.trace("Завершено выполнение метода sessionAsync@LoginUsecase : ${LocalDateTime.now().toLocalTime()}")
        }

        databasePort.getSession().setActive(sessionAsync.await().id)

        // TODO: обновление профиля пользователя
        // TODO: databasePort.getProfile().Merge(profile)

        // TODO: обновление сеанса пользователя
        // TODO: databasePort.getSession().Merge(session)

        return Output()
    }

    override fun present(output: Output, presenter: Presenter) {
        presenter.getSceneManager().openScene(Scene.MAIN)
    }


}