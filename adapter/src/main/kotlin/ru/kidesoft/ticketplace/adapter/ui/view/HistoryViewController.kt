package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.*
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.HistoryView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("history.fxml", Scene.HISTORY)
class HistoryViewController(private val presenter: Presenter) : ViewController(), HistoryView {
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

    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
       // TODO("Not yet implemented")
    }
}