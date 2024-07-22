package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.presenter.SettingPresenter
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

class UpdateSetting(commonPort: CommonPort) : Usecase<UpdateSetting.Input, UpdateSetting.Output>(commonPort) {
    class Input
    class Output(val setting : Setting)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val setting = commonPort.databasePort.getSetting().getByCurrent()?: throw NullPointerException("Setting of current user not found")

        val output = Output(setting)

        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(SettingPresenter::class)?:throw NullPointerException("Setting presenter not found")
        presenter.setSetting(output.setting)
    }
}