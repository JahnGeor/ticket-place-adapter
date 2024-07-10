package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.layout.StackPane
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.BasePresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.BaseView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*


@FxmlView("base.fxml", Scene.BASE)
class BaseViewController(private val presenter: BasePresenter, private val authPresenter: AuthPresenter) : ViewController(), BaseView {
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
        updateMenuItem.setOnAction { presenter.openScene(Scene.UPDATE, stageManager) }
        logoutMenuItem.setOnAction { authPresenter.logout(this) }
        exitMenuItem.setOnAction { presenter.exit(this) }
        settingMenuItem.setOnAction { presenter.openScene(Scene.SETTING, stageManager) }
        homeMenuItem.setOnAction { presenter.openScene(Scene.MAIN, stageManager) }
        historyMenuItem.setOnAction { presenter.openScene(Scene.HISTORY, stageManager) }
        adminMenuItem.setOnAction { presenter.openScene(Scene.ADMIN, stageManager) }
        aboutMenuItem.setOnAction { presenter.openScene(Scene.ABOUT, stageManager) }
        diagnosticMenuItem.setOnAction { presenter.runDiagnostic(this) }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setActions()
    }

}