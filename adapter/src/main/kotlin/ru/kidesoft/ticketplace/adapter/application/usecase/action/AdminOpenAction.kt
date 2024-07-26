package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
//
//class AdminOpenAction : Usecase<AdminOpenAction.Input, AdminOpenAction.Output>() {
//    class Input
//    class Output(val validation: Boolean)
//
//    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
//        val output = Output(false)
//
//        return output
//    }
//
//    override fun present(output: Output, sceneManager: SceneManager) {
//
//        if (output.validation) {
//            sceneManager.openScene(Scene.ADMIN)
//        } else {
//            // sceneManager.showNotification()
//        }
//
//
//
//
//
//    }
//}