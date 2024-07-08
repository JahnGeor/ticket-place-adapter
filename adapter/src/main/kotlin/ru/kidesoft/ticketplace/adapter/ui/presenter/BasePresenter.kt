package ru.kidesoft.ticketplace.adapter.ui.presenter

import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene

class BasePresenter {
    fun runDiagnostic() {

    }

    fun openScene(scene: Scene, sceneManager: SceneManager) {
        try {
            sceneManager.openScene(scene)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun exit(sceneManager: SceneManager) {
        try {
            sceneManager.openScene(Scene.AUTH)
        } catch (e: Exception) {
            println(e)
        }
    }
}