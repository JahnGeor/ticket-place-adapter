package ru.kidesoft.ticketplace.adapter.application.usecase.profile

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile

class GetProfileByCurrentUser(commonPort: CommonPort) : Usecase<GetProfileByCurrentUser.Input, GetProfileByCurrentUser.Output>(commonPort) {
    class Input : Usecase.Input {}
    class Output(val profile: Profile) : Usecase.Output {

    }

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        val profile = commonPort.databasePort.getProfile().getByCurrent()?: throw IllegalArgumentException("Профиль текущего пользователя не найден")

        val output = Output(profile)

        sceneManager?.let {
            _present(output, it)
        }

        return output
    }

    private fun _present(output: Output, sceneManager: SceneManager) {
        val presenter = sceneManager.getPresenter(MainPresenter::class)?: throw NullPointerException("${MainPresenter::class::simpleName} is not found")
        presenter.setProfile(output.profile)
    }
}