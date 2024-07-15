package ru.kidesoft.ticketplace.adapter.application.presenter

interface Presenter {
    fun getSceneManager() : SceneManager
    fun getApplicationManager() : ApplicationManager
}

interface SceneManager : Notification, Alert {
    fun openScene(scene: Scene)
}

interface ApplicationManager {
    fun closeApplication(code: Int)
}

interface Notification {
    fun showNotification(notificationType: NotificationType, message: String, title: String)
}


interface Alert {
    fun showAlert(alertType: AlertType, message: String, title: String, exception: Exception? = null)
}

