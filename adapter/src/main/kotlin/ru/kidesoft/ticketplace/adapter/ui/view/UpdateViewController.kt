package ru.kidesoft.ticketplace.adapter.ui.view

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene


import java.net.URL
import java.util.*

@FxmlView("update.fxml", Scene.UPDATE)
class UpdateViewController() : ViewController(), ru.kidesoft.ticketplace.adapter.application.presenter.UpdatePresenter {

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

    // Action session

    override fun setActions() {
        TODO("Not yet implemented")
    }
}