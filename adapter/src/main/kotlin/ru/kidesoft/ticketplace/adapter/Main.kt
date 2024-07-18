package ru.kidesoft.ticketplace.adapter

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.usecase.StartApplication
import ru.kidesoft.ticketplace.adapter.application.usecase.action.LoginAction
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintAction
import ru.kidesoft.ticketplace.adapter.application.usecase.action.SaveSettingAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartKktSession
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.UpdateSessionStateUsecase


import ru.kidesoft.ticketplace.adapter.application.usecase.login.GetAllLoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Logout
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetCashierListUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetProfileByCurrentUser
import ru.kidesoft.ticketplace.adapter.application.usecase.session.IsActiveSessionUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.*
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.KktFactory
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.WebServerApiFactory
import ru.kidesoft.ticketplace.adapter.ui.StageManager

import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.Database
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.properties.ApplicationProperties
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
import ru.kidesoft.ticketplace.adapter.ui.view.*


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

            val applicationRepository = ApplicationProperties()
            val apiFactory = WebServerApiFactory()
            val h2DatabaseRepository = Database(appProps.database)
            // val mock = DatabaseMock() // COMM: Mock-database
            val kktFactory = KktFactory()


            val login = Login(h2DatabaseRepository, apiFactory)  // mock/h2DatabaseRepository)
            val logout = Logout(h2DatabaseRepository, kktFactory) // mock/h2DatabaseRepository)
            val getAllLoginUsecase = GetAllLoginUsecase(h2DatabaseRepository, apiFactory) // mock/h2DatabaseRepository,
            val getCashierListUsecase = GetCashierListUsecase(h2DatabaseRepository) // mock/h2DatabaseRepository
            val isActiveSessionUsecase = IsActiveSessionUsecase(h2DatabaseRepository) // mock/h2DatabaseRepository
            val getProfileByCurrentUser = GetProfileByCurrentUser(h2DatabaseRepository) // mock/h2DatabaseRepository
            val startKktSession = StartKktSession(h2DatabaseRepository, kktFactory)
            val updateSessionStateUsecase = UpdateSessionStateUsecase(h2DatabaseRepository, kktFactory)
            val startApplication = StartApplication(h2DatabaseRepository, apiFactory, kktFactory)
            val loginAction = LoginAction(databasePort = h2DatabaseRepository, apiFactory, kktFactory)
            val saveSettingAction = SaveSettingAction(h2DatabaseRepository)
            val printAction = PrintAction(h2DatabaseRepository, apiFactory, kktFactory)

            // COMM: Варианты использования обновления сцены
            val updateMain = UpdateMain(h2DatabaseRepository, kktFactory) // COMM: обновление главного окна
            val updateAbout = UpdateAbout(applicationRepository) // COMM: обновление окна о программе
            val updateSetting = UpdateSetting(h2DatabaseRepository) // COMM: обновление окна настроек
            val updateHistory = UpdateHistory(h2DatabaseRepository) // COMM: обновление окна истории
            val updateAuth = UpdateAuth(h2DatabaseRepository, apiFactory) // COMM: обновление окна авторизации


            UsecaseExecutor.registerUsecase( // FIXME: Необходимо переделать данный метод, в него нужно передавать лишь port implementation
                login, // FIXME:
                logout, // FIXME:
                getAllLoginUsecase, // FIXME:
                getCashierListUsecase, // FIXME:
                isActiveSessionUsecase, // FIXME:
                getProfileByCurrentUser, // FIXME:
                startKktSession, // FIXME:
                updateSessionStateUsecase, // FIXME:
                startApplication, // FIXME:
                updateAuth, // FIXME:
                updateHistory, // FIXME:
                updateSetting, // FIXME:
                updateAbout, // FIXME:
                updateMain, // FIXME:
                loginAction,
                saveSettingAction,
                printAction

            ) // FIXME:



            // COMM: Устанавливаем тему от AtlantaFX
            setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)


            // COMM: Инициализация View/Presenter окна авторизации
            val authViewController = AuthViewController()

            // COMM: Инициализация ViewController главной сцены
            val mainViewController = MainViewController()
            // COMM: Инициализация ViewController сцены "О приложении"
            val aboutViewController = AboutViewController()
            // COMM: Инициализация ViewController сцены истории печати пользователей
            val historyViewController = HistoryViewController()
            // COMM: Инициализация ViewController сцены настроек
            val settingViewController = SettingViewController()
            // COMM: Инициализация ViewController дополнительных возможностей для администратора системы
            val adminViewController = AdminViewController()
            // COMM: Инициализация ViewController сцены обновления приложения
            val updateViewController = UpdateViewController()

            // COMM: Инициализация View/Presenter базовой формы сцены
            val baseViewController = BaseViewController()


            // COMM: Инициализируем менеджер окон
            val stageManager = StageManager(stage, baseViewController)
            UsecaseExecutor.defaultHandler = DefaultHandler(stageManager)

            // COMM: Добавляем сцену в менеджер
            stageManager.addScene(authViewController)
            stageManager.addScene(mainViewController)
            stageManager.addScene(aboutViewController)
            stageManager.addScene(historyViewController)
            stageManager.addScene(settingViewController)
            stageManager.addScene(adminViewController)
            stageManager.addScene(updateViewController)

            // COMM: stage -> делаем окно видимым
            stage.show()

            UsecaseExecutor.Executor().execute(StartApplication::class, sceneManager = stageManager)
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
