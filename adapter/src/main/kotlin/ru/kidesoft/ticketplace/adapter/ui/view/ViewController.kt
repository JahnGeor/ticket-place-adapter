package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.Initializable
import ru.kidesoft.ticketplace.adapter.application.presenter.ApplicationManager
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.ui.StageManager


abstract class ViewController() : Initializable, ru.kidesoft.ticketplace.adapter.application.presenter.Presenter {
    lateinit var stageManager : StageManager

    override fun getSceneManager(): SceneManager {
        return stageManager
    }

    override fun getApplicationManager(): ApplicationManager {
        return stageManager
    }

    abstract fun setActions()
}