package ru.kidesoft.ticketplace.adapter

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.flywaydb.core.api.logging.Log
import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.Alert
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartSessionUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.UpdateSessionStateUsecase


import ru.kidesoft.ticketplace.adapter.application.usecase.login.GetAllLoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.LoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.LogoutUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetCashierListUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetProfileByCurrentUser
import ru.kidesoft.ticketplace.adapter.application.usecase.session.IsActiveSessionUsecase
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.KktFactory
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.WebServerApiFactory
import ru.kidesoft.ticketplace.adapter.ui.StageManager

import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.Database
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock.DatabaseMock
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.properties.ApplicationProperties
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
import ru.kidesoft.ticketplace.adapter.ui.view.*
import java.util.*


// Класс-наследник JavaFX Application, выполняет все в потоке JavaFX
class Main : Application() {
    override fun start(stage: Stage) {
        var lock = SocketLock(12111)

        if (!lock.tryLock()) {
                StageManager.showErrorAlert(
                    "Приложение уже запущено",
                    "Во время запуска приложения произошла ошибка",
                    IllegalStateException("Application is already running")
                )

                Platform.exit()
                return
        }

        try {


            val appProps = ru.kidesoft.ticketplace.adapter.ApplicationProperties()


            val apiFactory = WebServerApiFactory()
            val h2DatabaseRepository = Database(appProps.database)
            val mock = DatabaseMock()
            val kktFactory = KktFactory()


            val loginUsecase = LoginUsecase(apiFactory, h2DatabaseRepository)  // mock/h2DatabaseRepository)
            val logoutUsecase = LogoutUsecase(h2DatabaseRepository) // mock/h2DatabaseRepository)
            val getAllLoginUsecase = GetAllLoginUsecase(h2DatabaseRepository, apiFactory) // mock/h2DatabaseRepository,
            val getCashierListUsecase = GetCashierListUsecase(h2DatabaseRepository) // mock/h2DatabaseRepository
            val isActiveSessionUsecase = IsActiveSessionUsecase(h2DatabaseRepository) // mock/h2DatabaseRepository
            val getProfileByCurrentUser = GetProfileByCurrentUser(h2DatabaseRepository) // mock/h2DatabaseRepository
            val startSessionUsecase = StartSessionUsecase(h2DatabaseRepository, kktFactory)
            val updateSessionStateUsecase = UpdateSessionStateUsecase(h2DatabaseRepository, kktFactory)

            UsecaseExecutor.registerUsecase(
                loginUsecase,
                logoutUsecase,
                getAllLoginUsecase,
                getCashierListUsecase,
                isActiveSessionUsecase,
                getProfileByCurrentUser,
                startSessionUsecase,
                updateSessionStateUsecase
            )



            // Устанавливаем тему от AtlantaFX
            setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)

            val applicationRepository = ApplicationProperties()

            // Инициализация View/Presenter окна авторизации
            val authViewController = AuthViewController()

            // Инициализация ViewController главной сцены
            val mainViewController = MainViewController()
            // Инициализация ViewController сцены "О приложении"
            val aboutViewController = AboutViewController()
            // Инициализация ViewController сцены истории печати пользователей
            val historyViewController = HistoryViewController()
            // Инициализация ViewController сцены настроек
            val settingViewController = SettingViewController()
            // Инициализация ViewController дополнительных возможностей для администратора системы
            val adminViewController = AdminViewController()
            // Инициализация ViewController сцены обновления приложения
            val updateViewController = UpdateViewController()

            // Инициализация View/Presenter базовой формы сцены
            val baseViewController = BaseViewController()

            // Инициализируем менеджер окон
            val stageManager = StageManager(stage, baseViewController)

            // Добавляем сцену в менеджер
            stageManager.addScene(authViewController)
            stageManager.addScene(mainViewController)
            stageManager.addScene(aboutViewController)
            stageManager.addScene(historyViewController)
            stageManager.addScene(settingViewController)
            stageManager.addScene(adminViewController)
            stageManager.addScene(updateViewController)

            stageManager.openScene(
                if (runBlocking {
                        isActiveSessionUsecase.execute(IsActiveSessionUsecase.Input()).isActive
                    }
                ) {
                    UsecaseExecutor.Executor(mainViewController as MainPresenter)
                        .present(StartSessionUsecase::class, StartSessionUsecase.Input())
                    ru.kidesoft.ticketplace.adapter.application.presenter.Scene.MAIN
                } else ru.kidesoft.ticketplace.adapter.application.presenter.Scene.AUTH
            )

            // Делаем окно видимым
            stage.show()
        } catch (e: Exception) {
            StageManager.showErrorAlert(
                "${e.cause?.message ?: e.message}",
                "Во время запуска произошла критическая ошибка",
                e
            )
            super.stop()
            println("${Thread.getAllStackTraces()}")
            Platform.exit()
        }
    }
}
class Launcher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val logger = LogManager.getLogger()

            logger.trace("Запуск приложения: ${args.joinToString { " " }}")

            Application.launch(Main::class.java)
        }
    }
}
