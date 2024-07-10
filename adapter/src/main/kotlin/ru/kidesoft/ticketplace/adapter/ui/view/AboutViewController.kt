package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AboutPresenter
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.AboutView
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import java.net.URL
import java.util.*

@FxmlView("about.fxml", Scene.ABOUT)
class AboutViewController(private val aboutPresenter: AboutPresenter) : ViewController(), AboutView {
    @FXML lateinit var logoImageView : ImageView
    @FXML lateinit var versionLabel : Label

    override fun setActions() {}

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        aboutPresenter.update(this)
    }

    override fun setVersion(version: String) {
        versionLabel.text = "Версия приложения: $version"
    }

}