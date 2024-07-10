package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.PasswordTextField
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import ru.kidesoft.ticketplace.adapter.domain.Cashier
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AuthView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("auth.fxml", Scene.AUTH)
class AuthViewController(private val presenter: AuthPresenter) : AuthView, ViewController() {
    @FXML private lateinit var emailFields: ComboBox<String>
    @FXML private lateinit var passwordField: PasswordTextField
    @FXML private lateinit var urlFields : ComboBox<String>
    @FXML private lateinit var cashierList: ComboBox<Cashier>

    @FXML private lateinit var loginButton: Button
    @FXML private lateinit var printLastButton: Button
    @FXML private lateinit var shiftButton: Button

    override fun setActions() {
        loginButton.setOnAction { presenter.login(emailFields.value, passwordField.text, urlFields.value, this) }
        printLastButton.setOnAction {  } // TODO: Добавить метод
        shiftButton.setOnAction {  } // TODO: Добавить метод
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        presenter.update(this)



        setActions()
    }

    override fun setEmails(urlList: List<String>) {
        emailFields.items.setAll(urlList)
    }

    override fun setUrls(emailList: List<String>) {
        urlFields.items.setAll(emailList)
    }

    override fun setCashiers(cashiers: List<Cashier>) {
        cashierList.items.setAll(cashiers)
    }
}