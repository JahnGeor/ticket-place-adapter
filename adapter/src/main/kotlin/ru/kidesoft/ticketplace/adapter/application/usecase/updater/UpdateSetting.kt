package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.presenter.SettingPresenter
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.setting.Setting

class UpdateSetting(val databasePort: DatabasePort) : _Usecase<UpdateSetting.Input, UpdateSetting.Output>() {
    class Input : _Usecase.Input
    class Output(val setting : Setting) : _Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val setting = databasePort.getSetting().getByCurrentUser()?: throw NullPointerException("Setting of current user not found")

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