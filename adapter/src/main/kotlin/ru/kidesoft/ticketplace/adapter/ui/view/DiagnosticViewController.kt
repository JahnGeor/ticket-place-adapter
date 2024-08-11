package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import org.kordamp.ikonli.javafx.FontIcon
import ru.kidesoft.ticketplace.adapter.application.presenter.DiagnosticPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import org.kordamp.ikonli.materialdesign2.MaterialDesignM
import java.net.URL
import java.util.*

@FxmlView("diagnostic.fxml", Scene.DIAGNOSTIC)
class DiagnosticViewController : ViewController(), DiagnosticPresenter{
    @FXML private lateinit var diagnosticButton : Button
    @FXML private lateinit var exitButton : Button

    @FXML private lateinit var listenerConnectionState : FontIcon
    @FXML private  lateinit var kktShiftState : FontIcon
    @FXML private  lateinit var kktConnectionState : FontIcon

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        diagnosticButton.onAction = EventHandler(::runDiagnosticButtonAction)
        exitButton.onAction = EventHandler(::exitButtonAction)
    }

    private fun exitButtonAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.MAIN)
    }

    private fun runDiagnosticButtonAction(actionEvent: ActionEvent) {

    }

    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun setStates() {
        TODO("Not yet implemented")
    }

    override fun setRecommendation(recommendation: String) {
        TODO("Not yet implemented")
    }

    override fun setReason(reason: String) {
        TODO("Not yet implemented")
    }

    override fun setRecoveryState(recoveryState: Boolean) {
        TODO("Not yet implemented")
    }
}