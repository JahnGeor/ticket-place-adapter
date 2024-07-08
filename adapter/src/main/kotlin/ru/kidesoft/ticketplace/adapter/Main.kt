package ru.kidesoft.ticketplace.adapter

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.ui.StageManager

import ru.kidesoft.ticketplace.adapter.ui.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.ui.presenter.BasePresenter
import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene
import ru.kidesoft.ticketplace.adapter.ui.view.AuthView
import ru.kidesoft.ticketplace.adapter.ui.view.BaseView

class Main : Application() {
    override fun start(stage: Stage) {
        val basePresenter = BasePresenter()
        val baseView = BaseView(basePresenter)

        val authPresenter = AuthPresenter()
        val authView = AuthView(authPresenter)

        val stageManager = StageManager(stage, baseView)

        stageManager.addScene(Scene.AUTH, authView)

        stage.show()
    }
}

fun main() {
    Application.launch(Main::class.java)
}