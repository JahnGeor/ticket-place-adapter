package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.ui.StageManager
import ru.kidesoft.ticketplace.adapter.ui.presenter.SceneManager

abstract class View : Initializable {
    lateinit var stageManager : StageManager

    fun getSceneManager() : SceneManager {
        return stageManager
    }

    abstract fun setActions()
}