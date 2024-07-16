package ru.kidesoft.ticketplace.adapter.application.usecase.profile

import ru.kidesoft.ticketplace.adapter.Main
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile

class GetProfileByCurrentUser(val database: DatabasePort) : _Usecase<GetProfileByCurrentUser.Input, GetProfileByCurrentUser.Output>() {
    class Input : _Usecase.Input {}
    class Output(val profile: Profile) : _Usecase.Output {

    }

    override suspend fun execute(inputValues: Input?, sceneManager: SceneManager? = null): Output {
        val profile = database.getProfile().getCurrentProfile()?: throw IllegalArgumentException("Профиль текущего пользователя не найден")

        sceneManager?.let {
            it.getPresenter(MainPresenter::class)?.setProfile(profile)
        }

        return Output(profile)
    }
}