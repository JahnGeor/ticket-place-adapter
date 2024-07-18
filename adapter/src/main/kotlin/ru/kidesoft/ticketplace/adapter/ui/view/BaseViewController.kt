package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.event.ActionEvent
import javafx.fxml.FXML

import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.layout.StackPane

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Logout
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*


@FxmlView("base.fxml", Scene.BASE)
class BaseViewController() : ViewController() {
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

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setActions()

        updateMenuItem.isDisable = true // TODO: pre-release version
    }

    // --- Action section

    override fun setActions() {
        updateMenuItem.setOnAction(::onUpdateMenuItemAction)
        logoutMenuItem.setOnAction(::onLogoutMenuItemAction)
        exitMenuItem.setOnAction(::onExitMenuItemAction)
        settingMenuItem.setOnAction(::onSettingMenuItemAction)
        homeMenuItem.setOnAction(::onHomeMenuItemAction)
        historyMenuItem.setOnAction(::onHistoryMenuItemAction)
        adminMenuItem.setOnAction (::onAdminMenuItemAction)
        aboutMenuItem.setOnAction(::onAboutMenuItemAction)
        diagnosticMenuItem.setOnAction(::onDiagnosticMenuItemAction)
    }

    private fun onUpdateMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.UPDATE)
    }

    private fun onLogoutMenuItemAction(actionEvent: ActionEvent) {
        UsecaseExecutor.Executor().execute(Logout::class, sceneManager = stageManager)
    }

    private fun onExitMenuItemAction(actionEvent: ActionEvent) {
        getApplicationManager().closeApplication(0)
    }
    private fun onSettingMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.SETTING)
    }

    private fun onHomeMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.MAIN)
    }

    private fun onHistoryMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.HISTORY)
    }

    private fun onAdminMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.ADMIN)
    }

    private fun onAboutMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.ABOUT)
    }

    private fun onDiagnosticMenuItemAction(actionEvent: ActionEvent) {

    }



    // --- View section




//    override fun showPasswordRequest() {
////        var dialog = javafx.scene.control.Dialog<Node>()
////
////        dialog.graphic = VBox().apply {
////            children.addAll(
////                Label("Hello"), Label("Text")
////            )
////        }
//
//
//            stageManager.createNewStage().apply {}
//
//
//
//        //dialog.initOwner(stageManager.createNewStage())
//
//
//        //dialog.showAndWait()
//    }

}