package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintAction
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

@FxmlView("admin.fxml", Scene.ADMIN)
class AdminViewController() : ViewController() {
    @FXML
    private lateinit var printRefundFromOrderButton: Button

    @FXML
    private lateinit var printRefundFromOrderField: TextField

    @FXML
    private lateinit var printSellFromOrderButton: Button

    @FXML
    private lateinit var printSellFromOrderField: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        stageManager.stage.scene.root.requestFocus()
        setActions()
    }

    // --- Action section

    private fun onPrintRefundFromOrderAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(
            PrintAction::class, PrintAction.Input(
                printRefundFromOrderField.text.toInt(), SourceType.ORDER, OperationType.REFUND
            )
        )
    }

    private fun onPrintSellFromOrderAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(
            PrintAction::class, PrintAction.Input(
                printSellFromOrderField.text.toInt(), SourceType.ORDER, OperationType.ORDER
            )
        )
    }

    override fun setActions() {
        printSellFromOrderButton.setOnAction(::onPrintSellFromOrderAction)
        printRefundFromOrderButton.setOnAction(::onPrintRefundFromOrderAction)
    }

    // --- View section


}