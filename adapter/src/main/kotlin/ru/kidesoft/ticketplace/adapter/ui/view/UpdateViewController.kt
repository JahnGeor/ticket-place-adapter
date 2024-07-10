package ru.kidesoft.ticketplace.adapter.ui.view

import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.UpdatePresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.UpdateView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("update.fxml", Scene.UPDATE)
class UpdateViewController(private val updatePresenter: UpdatePresenter) : ViewController(), UpdateView {
    override fun setActions() {
        TODO("Not yet implemented")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}