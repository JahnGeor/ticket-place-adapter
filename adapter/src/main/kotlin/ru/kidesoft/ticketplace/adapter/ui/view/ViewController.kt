package ru.kidesoft.ticketplace.adapter.ui.view

import javafx.fxml.Initializable
import ru.kidesoft.ticketplace.adapter.application.presenter.ApplicationManager
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.ui.StageManager
import java.net.URL
import java.util.*


abstract class ViewController() : Initializable, ru.kidesoft.ticketplace.adapter.application.presenter.Presenter {
    lateinit var stageManager : StageManager

    override fun getSceneManager(): StageManager {
        return stageManager
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        setActions()
    }

//    override fun getSceneManager(): SceneManager {
//        return stageManager
//    }

    override fun getApplicationManager(): ApplicationManager {
        return stageManager
    }

    abstract fun setActions()
}

abstract class DialogController : Presenter {
    lateinit var stageManager: StageManager

    override fun getSceneManager(): StageManager {
        return stageManager
    }

    override fun getApplicationManager(): ApplicationManager {
        return stageManager
    }
}