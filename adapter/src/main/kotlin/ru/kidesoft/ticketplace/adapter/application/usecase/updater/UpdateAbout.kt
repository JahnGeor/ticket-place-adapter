package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.PropertiesPort
import ru.kidesoft.ticketplace.adapter.application.presenter.AboutPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase

class UpdateAbout(val properties : PropertiesPort): _Usecase<UpdateAbout.Input, UpdateAbout.Output>() {
    class Input: _Usecase.Input
    class Output(val version: String) : _Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val version = properties.getVersion()

        val output = Output(version)

        sceneManager?.let { present(output, it) }

        return Output(version)
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(AboutPresenter::class)?:throw NullPointerException("${AboutPresenter::class} is not found")
        presenter.setVersion(output.version)
    }
}