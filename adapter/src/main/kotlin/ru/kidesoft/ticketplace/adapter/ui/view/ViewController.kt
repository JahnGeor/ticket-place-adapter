package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.Initializable
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AdminPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.ui.StageManager
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.SceneManager

abstract class ViewController() : Initializable {
    lateinit var stageManager : StageManager

    fun getSceneManager() : SceneManager {
        return stageManager
    }

    abstract fun setActions()
}