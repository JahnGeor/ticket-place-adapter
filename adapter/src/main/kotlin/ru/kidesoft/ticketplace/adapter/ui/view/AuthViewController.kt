package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.PasswordTextField
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.util.StringConverter
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.Main
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.StartSessionUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.GetAllLoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.login.LoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetCashierListUsecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.mock.Login

import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import ru.kidesoft.ticketplace.adapter.ui.handler.DefaultHandler
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

                return Cashier().apply {
                    this.inn = inn.toLong()
                    this.fullName = fullName
                }
            }

        }

        UsecaseExecutor.Executor(this as AuthPresenter).present(GetAllLoginUsecase::class, GetAllLoginUsecase.GetAllLoginUsecaseInput())
        UsecaseExecutor.Executor(this as AuthPresenter).present(GetCashierListUsecase::class, GetCashierListUsecase.GetCashierListInput())
        setActions()
    }

    // --- Action section

    override fun setActions() {
        loginButton.setOnAction(::onLoginButtonClick)
        printLastButton.setOnAction(::onPrintLastButtonClick)
        shiftButton.setOnAction(::onShiftButtonClick)
    }

    private fun onLoginButtonClick(event: ActionEvent) {
        try {
            validateLoginAction()
        } catch (e: IllegalArgumentException) {
            DefaultHandler().handle(e, this)
            return
        }

        val input = LoginUsecase.Input().apply {
            email = emailFields.value
            password = passwordField.password
            url = urlFields.value
        }


        UsecaseExecutor.Executor(this).get(LoginUsecase::class, input)?.let {
            UsecaseExecutor.Executor(this).get(StartSessionUsecase::class, StartSessionUsecase.Input())
            getSceneManager().openScene(Scene.MAIN)
        }

//        UsecaseExecutor.Executor<Presenter>(this).get(LoginUsecase::class, input)?.let {
//
//            getSceneManager().getViewController(MainPresenter::class)?.let {
//                UsecaseExecutor.Executor(it as MainPresenter).get(StartSessionUsecase::class, StartSessionUsecase.Input()).let {
//
//                }
//            }
//
//            getSceneManager().openScene(Scene.MAIN)
//
//        }

        // UsecaseExecutor.Executor(this).present(StartSessionUsecase::class, StartSessionUsecase.Input())
    }

    fun onPrintLastButtonClick(event: ActionEvent) {

    }

    fun onShiftButtonClick(event: ActionEvent) {

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
        cashierList.items.addAll(cashiers)
        cashierList.selectionModel.selectFirst()
    }

    // Validate

    fun validateLoginAction() {
        this.emailFields.selectionModel.selectedItem?: throw IllegalArgumentException("Поле \"Логин\" пустое")
        this.urlFields.selectionModel.selectedItem?: throw IllegalArgumentException("Поле \"Адрес\" пустое")
        if (this.passwordField.password.isEmpty()) throw IllegalArgumentException("Поле \"Пароль\" пустое")
    }
}