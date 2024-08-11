package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.PasswordTextField
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.kordamp.ikonli.javafx.FontIcon
import org.kordamp.ikonli.materialdesign2.MaterialDesignE
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.action.main.DiagnosticAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.KktSyncTime
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Logout
import ru.kidesoft.ticketplace.adapter.ui.StageManager
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

const val TICKET_ADMIN_PASSWORD = "ticket_admin" // FIXME: Временное решение

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
    }

    // --- Action section

    override fun setActions() {
        updateMenuItem.setOnAction(::onUpdateMenuItemAction)
        logoutMenuItem.setOnAction(::onLogoutMenuItemAction)
        exitMenuItem.setOnAction(::onExitMenuItemAction)
        settingMenuItem.setOnAction(::onSettingMenuItemAction)
        homeMenuItem.setOnAction(::onHomeMenuItemAction)
        historyMenuItem.setOnAction(::onHistoryMenuItemAction)
        adminMenuItem.setOnAction(::onAdminMenuItemAction)
        aboutMenuItem.setOnAction(::onAboutMenuItemAction)
        syncKktMenuItem.setOnAction(::syncKktMenuItemAction)
        diagnosticMenuItem.setOnAction(::onDiagnosticMenuItemAction)
    }

    private fun syncKktMenuItemAction(event: ActionEvent) {
        UsecaseExecutor.Executor().execute(KktSyncTime::class, sceneManager = stageManager)
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
        val result = showPasswordDialog()

        if (result.isPresent) {
            if (result.get() == TICKET_ADMIN_PASSWORD) {
                getSceneManager().openScene(Scene.ADMIN)
            } else {
                getSceneManager().showNotification(
                    NotificationType.WARN,
                    "Попытка входа в раздел неудачна",
                    "Неверный пароль, повторите попытку снова."
                )

            }
        }
    }

    private fun onAboutMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.ABOUT)
    }

    private fun onDiagnosticMenuItemAction(actionEvent: ActionEvent) {
        getSceneManager().openScene(Scene.DIAGNOSTIC)
    }


    private fun showPasswordDialog() : Optional<String>{
        val dialog: Dialog<String> = Dialog()

        StageManager.setOwnerProperties(dialog.dialogPane.scene.window as Stage)

        dialog.headerText =
            "Для входа в раздел \"Дополнительные возможности\", пожалуйста, введите пароль администратора."

        val enterButton = ButtonType("Войти", ButtonBar.ButtonData.OK_DONE)
        val cancelButton = ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE)

        dialog.dialogPane.buttonTypes.addAll(enterButton, cancelButton)

        val pwd = PasswordTextField()
        pwd.minWidth = 450.0
        pwd.prefWidth = 450.0

        val fontEyeIcon = FontIcon(MaterialDesignE.EYE_OUTLINE)

        pwd.right = fontEyeIcon
        fontEyeIcon.cursor = Cursor.HAND
        fontEyeIcon.setOnMouseClicked {
            pwd.revealPassword = !pwd.revealPassword
        }

        val content = HBox()
        content.alignment = Pos.CENTER_LEFT
        content.spacing = 10.0
        content.children.addAll(Label("Введите пароль:"), pwd)
        dialog.dialogPane.content = content
        dialog.setResultConverter { dialogButton ->
            if (dialogButton === enterButton) {
                return@setResultConverter pwd.password
            }
            return@setResultConverter null
        }

        return dialog.showAndWait()
    }

}


