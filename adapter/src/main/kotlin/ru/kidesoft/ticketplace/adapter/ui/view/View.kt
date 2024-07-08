package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.ui.StageManager

abstract class View : Initializable {
    lateinit var stageManager : StageManager
    abstract fun setActions()
}