package ru.kidesoft.ticketplace.adapter.ui.presenter

import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Alert
import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Notification
import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene

interface SceneManager : Notification, Alert {
    fun openScene(scene : Scene)
}

interface ViewManager {
    fun getSceneManager() : SceneManager
}