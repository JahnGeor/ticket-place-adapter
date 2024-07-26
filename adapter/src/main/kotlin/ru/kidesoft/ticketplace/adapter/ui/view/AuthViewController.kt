package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.PasswordTextField
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import org.kordamp.ikonli.javafx.FontIcon
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase.action.auth.LoginAction
import ru.kidesoft.ticketplace.adapter.application.usecase.action.main.ChangeShiftAction
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.PrintLastReceipt
import ru.kidesoft.ticketplace.adapter.application.usecase.login.Login
import ru.kidesoft.ticketplace.adapter.application.usecase.updater.UpdateAuth
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier

import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

@FxmlView("auth.fxml", Scene.AUTH)
class AuthViewController : ViewController(), ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter {
    private val logger = LogManager.getLogger()

    @FXML
    private lateinit var emailFields: ComboBox<String>

    @FXML
    private lateinit var passwordField: PasswordTextField

    @FXML
    private lateinit var urlFields: ComboBox<String>

    @FXML
    private lateinit var cashierList: ComboBox<Cashier>

    @FXML
    private lateinit var loginButton: Button

    @FXML
    private lateinit var printLastButton: Button

    @FXML
    private lateinit var shiftButton: Button

    @FXML
    private lateinit var fontEyeIcon: FontIcon

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        cashierList.converter = object : StringConverter<Cashier>() {
            override fun toString(item: Cashier?): String {
                return "${item?.inn} ${item?.fullName}"
            }

            override fun fromString(str: String?): Cashier? {
                if (str == null) {
                    return null
                }

                val parts = str.split(" ")
                if (parts.size == 2) {
                    return null
                }

                val inn = parts[0]
                val fullName = parts.subList(1, parts.size).joinToString(" ")

                return Cashier(fullName, inn.toLong())
            }

        }

        fontEyeIcon.cursor = Cursor.HAND
        fontEyeIcon.setOnMouseClicked {
            passwordField.revealPassword = !passwordField.revealPassword
        }

        UsecaseExecutor.Executor().execute(UpdateAuth::class, sceneManager = stageManager)

        setActions()
    }

    // --- Action section

    override fun setActions() {
        loginButton.setOnAction(::onLoginButtonClick)
        printLastButton.setOnAction(::onPrintLastButtonClick)
        shiftButton.setOnAction(::onShiftButtonClick)
    }

    private fun onLoginButtonClick(event: ActionEvent) {
        val input = Login.Input(
            email = emailFields.value,
            password = passwordField.password,
            url = urlFields.value
        )

        UsecaseExecutor.Executor().execute(LoginAction::class, input, stageManager)
    }

    private fun onPrintLastButtonClick(event: ActionEvent) {
        val cashier = cashierList.selectionModel.selectedItem ?: run {
            getSceneManager().showNotification(
                NotificationType.ERROR,
                "Ошибка валидации",
                "Перед выполнением операции необходимо выбрать пользователя"
            )
            return
        }

        UsecaseExecutor.Executor()
            .execute(PrintLastReceipt::class, PrintLastReceipt.Input(cashier), sceneManager = stageManager)
    }

    private fun onShiftButtonClick(event: ActionEvent) {
        val cashier = cashierList.selectionModel.selectedItem ?: run {
            getSceneManager().showNotification(
                NotificationType.ERROR,
                "Ошибка валидации",
                "Перед выполнением операции необходимо выбрать пользователя"
            )
            return
        }
        UsecaseExecutor.Executor()
            .execute(ChangeShiftAction::class, ChangeShiftAction.Input(cashier, ChangeShiftAction.ShiftAction.CLOSE, deletePortAfterUse = true), sceneManager = stageManager)
    }

    // --- View section

    override fun setEmails(emailList: List<String>) {
        emailFields.items.addAll(emailList)
        emailFields.selectionModel.selectFirst()
    }

    override fun setUrls(urlList: List<String>) {
        urlFields.items.addAll(urlList)
        urlFields.selectionModel.selectFirst()
    }

    override fun setCashiers(cashiers: List<Cashier>) {
        cashierList.items.addAll(cashiers.distinctBy { it.fullName to it.inn })
        cashierList.selectionModel.selectFirst()
    }

    // Validate

    fun validateLoginAction() {
        this.emailFields.selectionModel.selectedItem ?: throw IllegalArgumentException("Поле \"Логин\" пустое")
        this.urlFields.selectionModel.selectedItem ?: throw IllegalArgumentException("Поле \"Адрес\" пустое")
        if (this.passwordField.password.isEmpty()) throw IllegalArgumentException("Поле \"Пароль\" пустое")
    }
}