package ru.kidesoft.ticketplace.adapter

import atlantafx.base.theme.CupertinoLight
import javafx.application.Application
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.application.port.PropertiesPort
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.*
import ru.kidesoft.ticketplace.adapter.ui.StageManager

import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.properties.ApplicationProperties
import ru.kidesoft.ticketplace.adapter.ui.view.*

// Класс-наследник JavaFX Application, выполняет все в потоке JavaFX
class Main : Application() {
    override fun start(stage: Stage) {
        // Устанавливаем тему от AtlantaFX
        setUserAgentStylesheet(CupertinoLight().userAgentStylesheet)

        val applicationRepository = ApplicationProperties()

        // Инициализация View/Presenter окна авторизации
        val authPresenter = AuthPresenter()
        val authViewController = AuthViewController(authPresenter).apply {  }

        // Инициализация View/Presenter главного
        val mainPresenter = MainPresenter()
        val mainViewController = MainViewController(mainPresenter)

        val aboutPresenter = AboutPresenter(applicationRepository)
        val aboutViewController = AboutViewController(aboutPresenter)

        val historyPresenter = HistoryPresenter()
        val historyViewController = HistoryViewController(historyPresenter)

        val settingPresenter = SettingPresenter()
        val settingViewController = SettingViewController(settingPresenter)

        val adminPresenter = AdminPresenter()
        val adminViewController = AdminViewController(adminPresenter)

        val updatePresenter = UpdatePresenter()
        val updateViewController = UpdateViewController(updatePresenter)

        // Инициализация View/Presenter базовой формы сцены
        val basePresenter = BasePresenter()
        val baseViewController = BaseViewController(basePresenter, authPresenter)

        // Инициализируем менеджер окон
        val stageManager = StageManager(stage, baseViewController)

        // Добавляем сцену в менеджер
        stageManager.addScene(Scene.AUTH, authViewController)
        stageManager.addScene(Scene.MAIN, mainViewController)
        stageManager.addScene(Scene.ABOUT, aboutViewController)
        stageManager.addScene(Scene.HISTORY, historyViewController)
        stageManager.addScene(Scene.SETTING, settingViewController)
        stageManager.addScene(Scene.ADMIN, adminViewController)
        stageManager.addScene(Scene.UPDATE, updateViewController)

        // Делаем окно видимым
        stage.show()

    }
}

fun main() {
    Application.launch(Main::class.java)
}