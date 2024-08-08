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
import ru.kidesoft.ticketplace.adapter.domain.setting.*
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

class PageOrientationStringConverter : StringConverter<PageOrientation>() {
    override fun toString(`object`: PageOrientation): String {
        return when (`object`) {
            PageOrientation.LANDSCAPE -> "Альбомная"
            PageOrientation.PORTRAIT -> "Портретная"
            else -> ""
        }
    }

    override fun fromString(string: String?): PageOrientation {

        if (string == null) {
            return PageOrientation.UNDEFINED
        }

        return when (string) {
            "Портретная" -> PageOrientation.PORTRAIT
            "Альбомная" -> PageOrientation.LANDSCAPE
            else -> PageOrientation.UNDEFINED
        }
    }
}

@FxmlView("setting.fxml", Scene.SETTING)
class SettingViewController() : ViewController(), SettingPresenter {

    @FXML
    private lateinit var updateTab: Tab

    @FXML
    private lateinit var autoReconnectLabel: Label

    @FXML
    private lateinit var autoUpdateBox: CheckBox

    @FXML
    private lateinit var kktAutoReconnectBox: CheckBox

    @FXML
    private lateinit var kktDriverPathButton: Button

    @FXML
    private lateinit var kktDriverPathField: TextField

    @FXML private lateinit var printTimeCheckBox: CheckBox

    @FXML
    private lateinit var kktPathLabel: Label

    @FXML
    private lateinit var pageOrientationBox: ComboBox<PageOrientation>

    @FXML
    private lateinit var pageSizeBox: ComboBox<PageSize>

    @FXML
    private lateinit var intervalBox: ComboBox<Duration>

    @FXML
    private lateinit var printCheckBox: CheckBox

    @FXML
    private lateinit var printCheckLabel: Label

    @FXML
    private lateinit var printTicketBox: CheckBox

    @FXML
    private lateinit var printTicketLabel: Label

    @FXML
    private lateinit var printerNameField: ComboBox<String>

    @FXML
    private lateinit var repoPathField: TextField

    @FXML
    private lateinit var saveButton: Button

    @FXML
    private lateinit var timeoutBox: ComboBox<Duration>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        UsecaseExecutor.Executor().execute(UpdateSetting::class, sceneManager = stageManager)

        intervalBox.converter = DurationConverter()
        timeoutBox.converter = DurationConverter()
        pageOrientationBox.converter = PageOrientationStringConverter()

        printTicketBox.isDisable = true

        setActions()
    }

    // --- Action session

    override fun setActions() {
        saveButton.setOnAction(::onSaveButtonClick)
        kktDriverPathButton.setOnAction(::onChooseDriverPathAction)
    }

    private fun onSaveButtonClick(actionEvent: ActionEvent) {
        val input = SaveSettingAction.Input(
            SettingInfo(
                kkt = KktSetting(path = kktDriverPathField.text, autoReconnect = kktAutoReconnectBox.isSelected, printTimeCheck = printTimeCheckBox.isSelected),
                server = ServerSetting(intervalBox.selectionModel.selectedItem, timeoutBox.selectionModel.selectedItem),
                print = PrintSetting(
                    isPrintingCheck = printCheckBox.isSelected,
                    isPrintingTicket = printTicketBox.isSelected,
                    pageSize = pageSizeBox.selectionModel.selectedItem,
                    pageOrientation = pageOrientationBox.selectionModel.selectedItem,
                    printerName = printerNameField.value
                ),
                update = UpdateSetting(
                    isAuto = autoUpdateBox.isSelected,
                    updateUrl = repoPathField.text
                )
            )
        )
        UsecaseExecutor.Executor().execute(SaveSettingAction::class, input, stageManager)
    }


    private fun onChooseDriverPathAction(actionEvent: ActionEvent) {
        val selectedDirectory = DirectoryChooser().showDialog(stageManager.stage)

        if (selectedDirectory != null) {
            kktDriverPathField.text = selectedDirectory.absolutePath
        }
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

        this.intervalBox.selectionModel.select(setting.serverSetting.requestInterval)
        this.timeoutBox.selectionModel.select(setting.serverSetting.requestTimeout)

        kktDriverPathField.text = setting.kktSetting.path
        autoUpdateBox.isSelected = setting.updateSetting.isAuto
        kktAutoReconnectBox.isSelected = setting.kktSetting.autoReconnect
        pageOrientationBox.items.addAll(PageOrientation.PORTRAIT, PageOrientation.LANDSCAPE)
        pageOrientationBox.selectionModel.select(PageOrientation.PORTRAIT)
        pageSizeBox.selectionModel.select(PageSize.A4)
        pageSizeBox.items.addAll(PageSize.A4, PageSize.A5)
        printCheckBox.isSelected = setting.print.isPrintingCheck
        printTicketBox.isSelected = setting.print.isPrintingTicket
        printTimeCheckBox.isSelected = setting.kktSetting.printTimeCheck

        val printers = Printer.getAllPrinters().map { it.name }

        printerNameField.items.addAll(FXCollections.observableList(printers))

        val printerName =
            setting.print.printerName.ifEmpty { Printer.getDefaultPrinter().name }

        printerName?.let {
            if (!printers.contains(it)) {
                printerNameField.pseudoClassStateChanged(Styles.STATE_WARNING, true)
            }

            printerNameField.selectionModel.select(it)
        } ?: {
            printerNameField.selectionModel.select("Неопределен")
        }

        repoPathField.text = setting.updateSetting.updateUrl

        updateTab.isDisable = true // TODO: убрать при внедрении функционала "Обновление"
    }

// --- View section


}