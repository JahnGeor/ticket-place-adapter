package ru.kidesoft.ticketplace.adapter.ui.presenter

import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene



abstract class Presenter {
    fun openScene(scene: Scene, sceneManager: SceneManager) {
        try {
            sceneManager.openScene(scene)
        } catch (e: Exception) {
            sceneManager.onError(e)
        }
    }
}