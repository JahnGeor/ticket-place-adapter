package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.Card
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign2.MaterialDesignC
import org.kordamp.ikonli.materialdesign2.MaterialDesignP
import org.kordamp.ikonli.materialdesign2.MaterialDesignR
import ru.kidesoft.ticketplace.adapter.Main
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.PoolingService
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.CashIncomeAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.ChangeShift
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.PrintLastReceipt
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.PrintXReportAction
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetProfileByCurrentUser
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateMain
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.awt.Font
import java.net.URL
import java.util.*


@FxmlView("main.fxml", Scene.MAIN)
class MainViewController() : ViewController(), MainPresenter {
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
        UsecaseExecutor.Executor().execute(PoolingService::class, sceneManager = stageManager)
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
        UsecaseExecutor.Executor().execute(ChangeShift::class, sceneManager = stageManager)
    }

    private fun onRefreshMainAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(UpdateMain::class, sceneManager = stageManager)
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        UsecaseExecutor.Executor().execute(UpdateMain::class, sceneManager = stageManager)
        setActions()
    }

    override fun setProfile(profile: Profile) {
        fullNameLabel.text = profile.cashier.fullName
        innLabel.text = profile.cashier.inn.toString()
    }

    override fun setShiftState(shiftState: ShiftState) {
        kktShiftStateLabel.text = when (shiftState) {
            ShiftState.UNDEFINED -> "Смена неопределена"
            ShiftState.CLOSED -> "Смена закрыта"
            ShiftState.EXPIRED -> "Смена истекла"
            ShiftState.OPENED -> "Смена открыта"
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
            true -> "Соединение с кассой: да"
            false -> "Соединение с кассой: нет"
        }
    }

    override fun setPoolingStatus(poolingStatus: Boolean) {
        listenerButton.graphic = FontIcon(
            if (poolingStatus) MaterialDesignC.CLOUD_CHECK_OUTLINE else MaterialDesignC.CLOUD_OFF_OUTLINE
        )

        listenStateLabel.text = when (poolingStatus) {
            true -> "Соединение с сервером: да"
            false -> "Соединение с сервером: нет"
        }
    }
}