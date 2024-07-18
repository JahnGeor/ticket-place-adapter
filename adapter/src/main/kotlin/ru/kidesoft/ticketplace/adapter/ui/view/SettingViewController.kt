package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.theme.Styles
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.print.Printer
import javafx.scene.control.*
import javafx.stage.DirectoryChooser
import javafx.util.StringConverter
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SettingPresenter
import ru.kidesoft.ticketplace.adapter.application.usecase.action.SaveSettingAction
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateSetting
import ru.kidesoft.ticketplace.adapter.domain.setting.PageOrientation
import ru.kidesoft.ticketplace.adapter.domain.setting.PageSize
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class DurationConverter : StringConverter<Duration>() {
    override fun toString(`object`: Duration?): String {
        return `object`?.let { return "${it.toSeconds()} секунд" } ?: "Неизвестно"
    }

    override fun fromString(string: String?): Duration {
        if (string == null) {
            throw NullPointerException("String cannot be null")
        }
        val regex = """(\d+)\s*секунд""".toRegex()
        val match = regex.matchEntire(string)
        if (match != null) {
            val seconds = match.groupValues[1].toLong()
            return Duration.ofSeconds(seconds)
        } else {
            throw IllegalArgumentException("Invalid duration string: $string")
        }
    }

}

@FxmlView("setting.fxml", Scene.SETTING)
class SettingViewController() : ViewController(), SettingPresenter {

    @FXML private lateinit var autoReconnectLabel: Label

    @FXML private lateinit var autoUpdateBox: CheckBox

    @FXML private lateinit var kktAutoReconnectBox: CheckBox

    @FXML private lateinit var kktDriverPathButton: Button

    @FXML private lateinit var kktDriverPathField: TextField

    @FXML private lateinit var kktPathLabel: Label

    @FXML private lateinit var pageOrientationBox: ComboBox<PageOrientation>

    @FXML private lateinit var pageSizeBox: ComboBox<PageSize>

    @FXML private lateinit var intervalBox: ComboBox<Duration>

    @FXML private lateinit var printCheckBox: CheckBox

    @FXML private lateinit var printCheckLabel: Label

    @FXML private lateinit var printTicketBox: CheckBox

    @FXML private lateinit var printTicketLabel: Label

    @FXML private lateinit var printerNameField: ComboBox<String>

    @FXML private lateinit var repoPathField: TextField

    @FXML private lateinit var saveButton: Button

    @FXML private lateinit var timeoutBox: ComboBox<Duration>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        UsecaseExecutor.Executor().execute(UpdateSetting::class, sceneManager = stageManager)

        intervalBox.converter = DurationConverter()
        timeoutBox.converter = DurationConverter()

        setActions()
    }

    // --- Action session

    override fun setActions() {
        saveButton.setOnAction(::onSaveButtonClick)
        kktDriverPathButton.setOnAction(::onChooseDriverPathAction)
    }

    private fun onSaveButtonClick(actionEvent: ActionEvent) {
        val input = SaveSettingAction.Input(SettingExposed().apply {
            kkt.path = kktPathLabel.text
            kkt.autoRecconect = kktAutoReconnectBox.isSelected
            server.requestTimeout = timeoutBox.selectionModel.selectedItem
            server.requestInterval = intervalBox.selectionModel.selectedItem
            print.pageSize = pageSizeBox.selectionModel.selectedItem
            print.pageOrientation = pageOrientationBox.selectionModel.selectedItem
            print.isPrintingCheck = printCheckBox.isSelected
            print.isPrintingTicket = printTicketBox.isSelected
            print.printerName = printerNameField.value
            update.isAuto = autoUpdateBox.isSelected
            update.repositoryUrl = repoPathField.text
        })

        UsecaseExecutor.Executor().execute(SaveSettingAction::class, input, stageManager)
    }

    private fun onChooseDriverPathAction(actionEvent: ActionEvent) {
        val selectedDirectory = DirectoryChooser().showDialog(stageManager.stage)

        if (selectedDirectory != null) {kktDriverPathField.text = selectedDirectory.absolutePath}
    }

    override fun setSetting(setting: Setting) {
        this.timeoutBox.items.addAll(
            Duration.of(1, ChronoUnit.SECONDS),
            Duration.of(5, ChronoUnit.SECONDS),
            Duration.of(10, ChronoUnit.SECONDS),
            Duration.of(15, ChronoUnit.SECONDS),
            Duration.of(30, ChronoUnit.SECONDS),
            Duration.of(60, ChronoUnit.SECONDS)
        )

        this.intervalBox.items.addAll(
            Duration.of(1, ChronoUnit.SECONDS),
            Duration.of(5, ChronoUnit.SECONDS),
            Duration.of(10, ChronoUnit.SECONDS),
            Duration.of(15, ChronoUnit.SECONDS),
            Duration.of(30, ChronoUnit.SECONDS),
            Duration.of(60, ChronoUnit.SECONDS)
        )

        this.intervalBox.selectionModel.select(setting.server.requestInterval)
        this.timeoutBox.selectionModel.select(setting.server.requestTimeout)

        kktDriverPathField.text = setting.kkt.path
        autoUpdateBox.isSelected = setting.update.isAuto
        kktAutoReconnectBox.isSelected = setting.kkt.autoRecconect
        pageOrientationBox.items.addAll(PageOrientation.PORTRAIT, PageOrientation.LANDSCAPE)
        pageOrientationBox.selectionModel.select(PageOrientation.PORTRAIT)
        pageSizeBox.selectionModel.select(PageSize.A4)
        pageSizeBox.items.addAll(PageSize.A4, PageSize.A5)
        printCheckBox.isSelected = setting.print.isPrintingCheck
        printTicketBox.isSelected = setting.print.isPrintingTicket

        val printers = Printer.getAllPrinters().map { it.name }

        printerNameField.items.addAll(FXCollections.observableList(printers))

        val printerName = if (setting.print.printerName.isEmpty()) Printer.getDefaultPrinter().name else setting.print.printerName

        printerName?.let {
            if (!printers.contains(it)) {
                printerNameField.pseudoClassStateChanged(Styles.STATE_WARNING, true)
            }

            printerNameField.selectionModel.select(it)
        } ?: {
            printerNameField.selectionModel.select("Неопределен")
        }

        repoPathField.text = setting.update.repositoryUrl

    }

    // --- View section


}