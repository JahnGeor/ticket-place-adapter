package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.layout.StackPane
import org.kordamp.ikonli.fluentui.FluentUiRegularAL
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ
import org.kordamp.ikonli.javafx.FontIcon
import ru.kidesoft.ticketplace.adapter.ui.presenter.BasePresenter
import ru.kidesoft.ticketplace.adapter.ui.presenter.BaseView
import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene
import java.net.URL
import java.util.*


@FxmlView("base.fxml", Scene.BASE)
class BaseView(val presenter: BasePresenter) : View(), BaseView {
    @FXML
    private lateinit var aboutMenuItem: MenuItem

    @FXML
    private lateinit var adminMenuItem: MenuItem

    @FXML
    private lateinit var diagnosticMenuItem: MenuItem

    @FXML
    private lateinit var exitMenuItem: MenuItem

    @FXML
    private lateinit var fileMenu: Menu

    @FXML
    private lateinit var helpMenu: Menu

    @FXML
    private lateinit var historyMenuItem: MenuItem

    @FXML
    private lateinit var homeMenuItem: MenuItem

    @FXML
    private lateinit var logoutMenuItem: MenuItem

    @FXML
    private lateinit var mainPane: StackPane

    @FXML
    private lateinit var serviceMenu: Menu

    @FXML
    private lateinit var settingMenuItem: MenuItem

    @FXML
    private lateinit var syncKktMenuItem: MenuItem

    @FXML
    private lateinit var updateMenuItem: MenuItem

    override fun setActions() {
        updateMenuItem.setOnAction { presenter.openScene(Scene.MAIN, stageManager) }
        exitMenuItem.setOnAction { presenter.exit(this) }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setActions()
    }

}