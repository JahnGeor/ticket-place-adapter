package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.Card
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.MainView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("main.fxml", Scene.MAIN)
class MainViewController(private val mainPresenter : MainPresenter) : ViewController(), MainView {
    @FXML private lateinit var card: Card

    @FXML private lateinit var incomeButton: Button

    @FXML private lateinit var incomeField: TextField

    @FXML private lateinit var listenerButton: Button

    @FXML private lateinit var logoImage: ImageView

    @FXML private lateinit var printLastCheck: Button

    @FXML private lateinit var printXReport: Button

    @FXML private lateinit var shiftButton: Button

    @FXML private lateinit var timeLabel: Label


    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}