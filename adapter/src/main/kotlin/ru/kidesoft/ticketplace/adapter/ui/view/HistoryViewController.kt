package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

@FxmlView("history.fxml", Scene.HISTORY)
class HistoryViewController() : ViewController(), ru.kidesoft.ticketplace.adapter.application.presenter.HistoryPresenter {
    @FXML
    private lateinit var checkCol: TableColumn<Any, Any>

    @FXML
    private lateinit var dateCol: TableColumn<Any, Any>

    @FXML
    private lateinit var errorCol: TableColumn<Any, Any>

    @FXML
    private lateinit var filterMenuButton: MenuButton

    @FXML
    private lateinit var historyTable: TableView<Any>

    @FXML
    private lateinit var numberCol: TableColumn<Any, Any>

    @FXML
    private lateinit var onlyErrorFilterLabel: Label

    @FXML
    private lateinit var operationTypeCol: TableColumn<Any, Any>

    @FXML
    private lateinit var printButton: Button

    @FXML
    private lateinit var printCheckBox: CheckBox

    @FXML
    private lateinit var printTicketBox: CheckBox

    @FXML
    private lateinit var sourceCol: TableColumn<Any, Any>

    @FXML
    private lateinit var statusTypeCol: TableColumn<Any, Any>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }

    // --- Action section

    override fun setActions() {
        printButton.setOnAction(::onPrintButtonAction)
    }

    private fun onPrintButtonAction(actionEvent: ActionEvent) {

    }

    override fun setHistory(history: List<History>) {

    }

    override fun addHistory(history: History) {

    }

    // --- View section
}