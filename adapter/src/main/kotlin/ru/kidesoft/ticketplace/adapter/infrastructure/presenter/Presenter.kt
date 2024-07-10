package ru.kidesoft.ticketplace.adapter.infrastructure.presenter

import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import ru.kidesoft.ticketplace.adapter.ui.view.ViewController
import java.lang.Exception


abstract class Presenter {
    fun openScene(scene: Scene, sceneManager: SceneManager) {
        try {
            sceneManager.openScene(scene)
        } catch (e: Throwable) {
            sceneManager.onErrorAlert(Exception(e))
        }
    }
}