package ru.kidesoft.ticketplace.adapter.ui.view

import atlantafx.base.controls.Card
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import ru.kidesoft.ticketplace.adapter.Main
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.UpdateSessionStateUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetProfileByCurrentUser
import ru.kidesoft.ticketplace.adapter.domain.ShiftState
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile
import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

@FxmlView("main.fxml", Scene.MAIN)
class MainViewController() : ViewController(), MainPresenter {
    @FXML
    private lateinit var card: Card

    @FXML
    private lateinit var fullNameLabel: Label

    @FXML
    private lateinit var incomeButton: Button

    @FXML
    private lateinit var incomeField: TextField

    @FXML
    private lateinit var innLabel: Label

    @FXML
    private lateinit var kktShiftStateLabel: Label

    @FXML
    private lateinit var kktStateLabel: Label

    @FXML
    private lateinit var listenStateLabel: Label

    @FXML
    private lateinit var listenerButton: Button

    @FXML
    private lateinit var logoImage: ImageView

    @FXML
    private lateinit var printLastCheck: Button

    @FXML
    private lateinit var printXReport: Button

    @FXML
    private lateinit var shiftButton: Button

    @FXML
    private lateinit var timeLabel: Label


    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        UsecaseExecutor.Executor(this as MainPresenter).present(GetProfileByCurrentUser::class, GetProfileByCurrentUser.Input())
        UsecaseExecutor.Executor(this as MainPresenter).present(UpdateSessionStateUsecase::class, UpdateSessionStateUsecase.Input())
    }

    override fun setProfile(profile: Profile) {
        fullNameLabel.text = profile.cashier.fullName
        innLabel.text = profile.cashier.inn.toString()
    }

    override fun setKktState(isConnected: Boolean, shiftState: ShiftState) {
        kktShiftStateLabel.text = when(shiftState) {
            ShiftState.UNDEFINED -> "Смена неопределена"
            ShiftState.CLOSED -> "Смена закрыта"
            ShiftState.EXPIRED -> "Смена истекла"
            ShiftState.OPENED -> "Смена открыта"
        }

        kktStateLabel.text = when(isConnected) {
            true -> "Соединение с кассой: да"
            false -> "Соединение с кассой: нет"
        }
    }
}