package ru.kidesoft.ticketplace.adapter.infrastructure.presenter

import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Alert
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Notification
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene

interface SceneManager : Notification, Alert {
    fun openScene(scene : Scene)
    fun closeApplication()
}

interface ViewManager {
    fun getSceneManager() : SceneManager
}