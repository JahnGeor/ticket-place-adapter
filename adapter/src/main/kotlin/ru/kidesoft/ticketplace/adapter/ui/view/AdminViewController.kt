package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AdminPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AdminView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("admin.fxml", Scene.ADMIN)
class AdminViewController(private val presenter : AdminPresenter) : ViewController(), AdminView  {
    @FXML
    private lateinit var printRefundFromCheckButton: Button

    @FXML
    private lateinit var printRefundFromCheckLabel: TextField

    @FXML
    private lateinit var printSellFromCheckButton: Button

    @FXML
    private lateinit var printSellFromCheckLabel: TextField

    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        stageManager.stage.scene.root.requestFocus()
    }
}