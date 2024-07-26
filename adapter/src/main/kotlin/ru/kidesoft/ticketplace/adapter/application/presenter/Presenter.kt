package ru.kidesoft.ticketplace.adapter.application.presenter

import kotlin.reflect.KClass

interface Presenter {
    fun getSceneManager() : SceneManager
    fun getApplicationManager() : ApplicationManager
}

interface SceneManager : Notification, Alert {
    fun openScene(scene: Scene)
    fun <P : Presenter> getPresenter(presenterClass : KClass<P>) : P?
}

interface ApplicationManager {
    fun closeApplication(code: Int)
}

interface Notification {
    fun showNotification(notificationType: NotificationType, title: String, message: String)
}


interface Alert {
    fun showAlert(alertType: AlertType, title: String, message: String, exception: Throwable? = null)
}

