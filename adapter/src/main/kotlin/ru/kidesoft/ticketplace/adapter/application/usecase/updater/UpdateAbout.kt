package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.PropertiesPort
import ru.kidesoft.ticketplace.adapter.application.presenter.AboutPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class UpdateAbout(commonPort: CommonPort): Usecase<UpdateAbout.Input, UpdateAbout.Output>(commonPort) {
    class Input: Usecase.Input
    class Output(val version: String) : Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val version = commonPort.propertiesPort.getVersion()

        val output = Output(version)

        sceneManager?.let { present(output, it) }

        return Output(version)
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(AboutPresenter::class)?:throw NullPointerException("${AboutPresenter::class} is not found")
        presenter.setVersion(output.version)
    }
}