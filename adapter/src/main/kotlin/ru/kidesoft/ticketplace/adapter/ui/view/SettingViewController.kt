package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.SettingPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.SettingView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("setting.fxml", Scene.SETTING)
class SettingViewController(private val settingPresenter: SettingPresenter) : ViewController(), SettingView {

    @FXML
    private lateinit var kktDriverPathButton: Button

    @FXML
    private lateinit var kktDriverPathField: TextField

    @FXML
    private lateinit var kktPathLabel: Label

    @FXML
    private lateinit var autoReconnectLabel: Label

    @FXML
    private lateinit var autoUpdateLabelBox: Label

    @FXML
    private lateinit var pageOrientationBox: ComboBox<Any>

    @FXML
    private lateinit var pageSizeBox: ComboBox<Any>

    @FXML
    private lateinit var periodBox: ComboBox<Any>

    @FXML
    private lateinit var printCheckLabel: Label

    @FXML
    private lateinit var printTicketLabel: Label

    @FXML
    private lateinit var printerNameField: ComboBox<Any>

    @FXML
    private lateinit var repoPathField: TextField

    @FXML
    private lateinit var saveButton: Button

    @FXML
    private lateinit var timeoutBox: ComboBox<Any>

    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }
}