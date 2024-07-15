package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene

import ru.kidesoft.ticketplace.adapter.ui.UsecaseExecutor
import java.net.URL
import java.util.*

@FxmlView("about.fxml", Scene.ABOUT)
class AboutViewController() : ViewController(), ru.kidesoft.ticketplace.adapter.application.presenter.AboutPresenter {
    @FXML lateinit var logoImageView : ImageView
    @FXML lateinit var versionLabel : Label

    override fun setActions() {}

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //UsecaseExecutor.Executor(this).present(TestUsecase::class, TestUsecase.Input())
    }

    // --- View section

    override fun setVersion(version: String) {
        versionLabel.text = "Версия приложения: $version"
    }
}

