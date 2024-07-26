package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.Card
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.beans.binding.ObjectBinding
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.paint.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign2.MaterialDesignC
import org.kordamp.ikonli.materialdesign2.MaterialDesignP
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.action.main.DiagnosticAction
import ru.kidesoft.ticketplace.adapter.application.usecase.action.main.PoolingServiceAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.CashIncomeAction
import ru.kidesoft.ticketplace.adapter.application.usecase.action.main.ChangeShiftAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.PrintLastReceipt
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintXReportAction
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateMain
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.sql.Time
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.event.ChangeListener
import kotlin.concurrent.timerTask

class TimeService : Service<String>() {
    override fun createTask(): Task<String> {
        return object : Task<String>() {
            override fun call(): String {
                while (true) {
                    if (isCancelled) {
                        cancel()
                    }
                    val currentTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss")
                        .withLocale(Locale("ru")) // Set locale to Russian
                    val formattedTime = currentTime.format(formatter)

                    updateValue(formattedTime)

                    runBlocking { delay(1000) }
                }

            }
        }
    }
}

@FxmlView("main.fxml", Scene.MAIN)
class MainViewController() : ViewController(), MainPresenter {
    private val poolerStateProperty = SimpleBooleanProperty()

    @FXML
    private lateinit var card: Card

    @FXML
    private lateinit var fullNameLabel: Label

    @FXML
    private lateinit var incomeButton: Button

    @FXML
    private lateinit var incomeField: TextField

    @FXML
    private lateinit var innLabel: Label

    @FXML
    private lateinit var kktShiftStateLabel: Label

    @FXML
    private lateinit var kktStateLabel: Label

    @FXML
    private lateinit var listenStateLabel: Label

    @FXML
    private lateinit var listenerButton: Button

    @FXML
    private lateinit var logoImage: ImageView

    @FXML
    private lateinit var printLastCheck: Button

    @FXML
    private lateinit var refreshButton: Button

    @FXML
    private lateinit var printXReport: Button

    @FXML
    private lateinit var shiftButton: Button

    @FXML
    private lateinit var timeLabel: Label


    // --- Action section

    override fun setActions() {
        shiftButton.setOnAction(::onShiftChangeAction)
        refreshButton.setOnAction(::onRefreshMainAction)
        printLastCheck.setOnAction(::onPrintLastCheckAction)
        printXReport.setOnAction(::onPrintXReportAction)
        incomeButton.setOnAction(::onIncomeAction)
        listenerButton.setOnAction(::onPoolerChangeStateAction)

    }

    private fun onPoolerChangeStateAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(PoolingServiceAction::class, sceneManager = stageManager)
    }

    private fun onIncomeAction(actionEvent: ActionEvent) {

        val input = CashIncomeAction.Input().apply { float = incomeField.text.toFloat() }

        UsecaseExecutor.Executor().execute(CashIncomeAction::class, sceneManager = stageManager, input = input)
    }

    private fun onPrintLastCheckAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(PrintLastReceipt::class, sceneManager = stageManager)
    }

    private fun onPrintXReportAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(PrintXReportAction::class, sceneManager = stageManager)
    }

    private fun onShiftChangeAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(
            ChangeShiftAction::class,
            sceneManager = stageManager,
            input = ChangeShiftAction.Input(isPoolerControl = true)
        )
    }

    private fun onRefreshMainAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(DiagnosticAction::class, sceneManager = stageManager)
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {

        val timeService = TimeService()

        timeLabel.textProperty().bind(timeService.valueProperty())

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Platform.runLater {
                    poolerStateProperty.set(checkState())
                }
            }
        }, 0, 200)


        listenerButton.graphicProperty().bind(
            Bindings.`when`(poolerStateProperty).then(FontIcon(MaterialDesignC.CLOUD_CHECK_OUTLINE))
                .otherwise(FontIcon(MaterialDesignC.CLOUD_OFF_OUTLINE))
        )

        listenStateLabel.textProperty()
            .bind(Bindings.`when`(poolerStateProperty).then("Подключен к серверу").otherwise("Не подключен к серверу"))

        listenStateLabel.textFillProperty().bind(Bindings.`when`(poolerStateProperty).then(Color.GREEN).otherwise(Color.RED))



        if (!timeService.isRunning) {
            timeService.start()
        }

        UsecaseExecutor.Executor().execute(UpdateMain::class, sceneManager = stageManager)
        setActions()
    }

    private fun checkState(): Boolean {
        return UsecaseExecutor.Executor()
            .execute(PoolingServiceControl::class, PoolingServiceControl.PoolingCommand.CHECKSTATE) ?: false
    }

    override fun setProfile(profile: Profile) {
        fullNameLabel.text = profile.fullName
        innLabel.text = profile.inn.toString()
    }

    override fun setShiftState(shiftState: ShiftState) {
        kktShiftStateLabel.text = when (shiftState) {
            ShiftState.UNDEFINED -> {
                kktShiftStateLabel.textFill = Color.RED
                "Смена неопределена"
            }

            ShiftState.CLOSED -> {
                kktShiftStateLabel.textFill = Color.RED
                "Смена закрыта"
            }

            ShiftState.EXPIRED -> {
                kktShiftStateLabel.textFill = Color.ORANGE
                "Смена истекла"
            }

            ShiftState.OPENED -> {
                kktShiftStateLabel.textFill = Color.GREEN
                "Смена открыта"
            }
        }

        this.shiftButton.graphic = when (shiftState) {
            ShiftState.UNDEFINED -> FontIcon(MaterialDesignP.PRINTER_EYE)
            ShiftState.CLOSED -> FontIcon(MaterialDesignP.PRINTER_OFF)
            ShiftState.EXPIRED -> FontIcon(MaterialDesignP.PRINTER_ALERT)
            ShiftState.OPENED -> FontIcon(MaterialDesignP.PRINTER_CHECK)
        }
    }

    override fun setKktConnectionStatus(connectionStatus: Boolean) {
        kktStateLabel.text = when (connectionStatus) {
            true -> {
                kktStateLabel.textFill = Color.GREEN
                "Касса подключена"
            }

            false -> {
                kktStateLabel.textFill = Color.RED
                "Проблема с кассой"
            }
        }
    }

    override fun setPoolingStatus(poolingStatus: Boolean) {

    }
}