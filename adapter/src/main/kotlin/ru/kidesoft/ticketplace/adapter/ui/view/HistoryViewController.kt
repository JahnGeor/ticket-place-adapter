package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.control.cell.CheckBoxTableCell
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintAction
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateHistory
import ru.kidesoft.ticketplace.adapter.domain.history.ErrorStatus
import ru.kidesoft.ticketplace.adapter.domain.history.History
import ru.kidesoft.ticketplace.adapter.domain.history.Step
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.Executors

class HistoryPrinterTask(val histories: List<HistoryDto>, val isPrintingCheck: Boolean, val isPrintingTicket: Boolean) : Task<Unit>() {
    val totalHistoriesCount = histories.size
    override fun call() {
        var errorCounter = 0
        var successCounter = 0

        for ((currentHistoryCount, history) in histories.withIndex()) {
            if (isCancelled) {
                updateProgress(1, 1)
                return
            }

            kotlin.runCatching {
                UsecaseExecutor.Executor().apply { handler = null }.execute(PrintAction::class, PrintAction.Input(
                    history.history.orderId,
                    history.history.historyExposed.sourceType,
                    history.history.historyExposed.operationType,
                    isPrintingCheck,
                    isPrintingTicket)
                )
            }.onFailure {
                errorCounter++
            }.onSuccess {
                successCounter++
            }

            updateProgress(currentHistoryCount.toLong() + 1, totalHistoriesCount.toLong())
            updateMessage("Успешно: ${successCounter}. С ошибкой: ${errorCounter}. Текущий: ${currentHistoryCount + 1}. Всего: ${histories.size}.")
        }
    }
}

class HistoryDto(var check: SimpleBooleanProperty, var history: History) {}

@FxmlView("history.fxml", Scene.HISTORY)
class HistoryViewController() : ViewController(),
    ru.kidesoft.ticketplace.adapter.application.presenter.HistoryPresenter {
    @FXML
    private lateinit var checkCol: TableColumn<HistoryDto, Boolean>

    @FXML
    private lateinit var dateCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var errorCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var filterMenuButton: MenuButton

    @FXML
    private lateinit var onlyErrorFilterLabel: Label

    @FXML
    private lateinit var historyTable: TableView<HistoryDto>

    @FXML
    private lateinit var orderIdCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var operationTypeCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var printButton: Button

    @FXML
    private lateinit var printCheckBox: CheckBox

    @FXML
    private lateinit var printTicketBox: CheckBox

    @FXML
    private lateinit var sourceCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var statusTypeCol: TableColumn<HistoryDto, String>

    @FXML
    private lateinit var stepTypeCol: TableColumn<HistoryDto, String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setActions()

        val checkBox = CheckBox()
        checkBox.setOnAction { historyTable.items.forEach { it.check.value = checkBox.isSelected } }
        checkCol.graphic = checkBox
        checkCol.cellFactory = CheckBoxTableCell.forTableColumn(checkCol)
        checkCol.isSortable = false
        checkCol.isEditable = true

        checkCol.setCellValueFactory { it.value.check }

        dateCol.setCellValueFactory {
            SimpleStringProperty(
                it.value.history.historyExposed.createdAt.format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                )
            )
        }
        orderIdCol.setCellValueFactory { SimpleStringProperty(it.value.history.orderId.toString()) }


        sourceCol.setCellValueFactory {
            SimpleStringProperty(
                when (it.value.history.historyExposed.sourceType) {
                    SourceType.REFUND -> "Возврат"
                    SourceType.ORDER -> "Заказ"
                    SourceType.UNDEFINED -> "Неопределено"
                }
            )
        }

        operationTypeCol.setCellValueFactory {
            SimpleStringProperty(
                when (it.value.history.historyExposed.operationType) {
                    OperationType.REFUND -> "Возврат"
                    OperationType.ORDER -> "Заказ"
                    OperationType.UNDEFINED -> "Неопределена"
                }
            )
        }

        stepTypeCol.setCellValueFactory {
            SimpleStringProperty(
                when (it.value.history.stepType) {
                    Step.PRINT_CHECK -> "Печать чека"
                    Step.GET_ORDER -> "Запрос данных"
                    Step.PRINT_TICKET -> "Печать билета"
                    Step.UNDEFINED -> "Неизвестный"
                }
            )
        }

        errorCol.setCellValueFactory { SimpleStringProperty(it.value.history.historyExposed.error) }

        statusTypeCol.setCellValueFactory {
            SimpleStringProperty(
                when (it.value.history.historyExposed.errorStatus) {
                    ErrorStatus.ERROR -> "Ошибка"
                    ErrorStatus.SUCCESS -> "Успешно"
                    ErrorStatus.UNDEFINED -> "Неопределен"
                }
            )
        }

        filterMenuButton.isDisable = true

        UsecaseExecutor.Executor().execute(
            UpdateHistory::class,
            sceneManager = stageManager,
            input = UpdateHistory.Input(from = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS))
        )
    }


    // --- Action section

    override fun setActions() {
        printButton.setOnAction(::onPrintButtonAction)
    }

    private fun callDialog(histories: List<HistoryDto>) {
        val task = HistoryPrinterTask(histories, printCheckBox.isSelected, printTicketBox.isSelected)

        val progressStage = Stage()

        progressStage.initModality(Modality.WINDOW_MODAL)
        progressStage.initOwner(this.stageManager.stage)

        val progressIndicator = ProgressIndicator()
        progressIndicator.style = "-fx-fill: white;"
        progressIndicator.progressProperty().bind(task.progressProperty())
        val label = Label("Обработка печати")
        val message = Label("Начало обработки запроса")

        message.textProperty().bind(task.messageProperty())

        val button = Button("Отменить")

        button.setOnAction {
            if (task.isRunning) {
                task.cancel()
            }

            UsecaseExecutor.Executor().execute(UpdateHistory::class, sceneManager = getSceneManager())

            progressStage.close()
        }

        val vbox = VBox(10.0)
        vbox.alignment = Pos.CENTER
        vbox.padding = Insets(20.0)

        vbox.children.addAll(label, progressIndicator, message, button)

        val scene = javafx.scene.Scene(vbox, 400.0, 200.0)
        progressStage.scene = scene

        task.setOnFailed {
            button.text = "Закрыть"
        }

        task.setOnSucceeded {
            button.text = "Закрыть"
        }

        task.setOnCancelled {
            println("Отменен")
        }

        Executors.newSingleThreadExecutor().submit(task)

        progressStage.show()
    }

    private fun onPrintButtonAction(actionEvent: ActionEvent) {
        if (validateStartPrinting()) {
            callDialog(histories = historyTable.items.filter { it.check.value })
        }
    }




    fun validateStartPrinting() : Boolean {
        if (!(printTicketBox.isSelected || printCheckBox.isSelected)) {
            getSceneManager().showNotification(NotificationType.WARN, "Предупреждение во время печати", "Необходимо выбрать хотя бы один параметр печати")
            return false
        }

        if (historyTable.items.none { it.check.value }) {
            getSceneManager().showNotification(NotificationType.WARN, "Предупреждение во время печати", "Необходимо выбрать хотя бы один заказ для печати")
            return false
        }

        return true
    }

    override fun setHistory(history: List<History>) {

        runCatching { (checkCol.graphic as CheckBox).isSelected = false }

        val uniqueOrderIds = history.groupBy { it.orderId }.mapValues { (_, history) ->
            history.sortedByDescending { it.historyExposed.createdAt }.drop(1)
        }.flatMap { it.value }

        val result = history - uniqueOrderIds.toSet()

        val historyDtoList = result.map { HistoryDto(SimpleBooleanProperty(false), it) }

        historyTable.items = FXCollections.observableList(historyDtoList)
        historyTable.sortOrder.remove(dateCol)
        historyTable.sortOrder.add(dateCol)
        dateCol.sortType = TableColumn.SortType.DESCENDING
        historyTable.sort()
    }

    override fun addHistory(history: History) {

    }

    // --- View section
}