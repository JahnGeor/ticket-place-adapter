package ru.kidesoft.ticketplace.adapter.application.usecase.profile

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.MainPresenter
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Profile

class GetProfileByCurrentUser(val database: DatabasePort) : _Usecase<GetProfileByCurrentUser.Input, GetProfileByCurrentUser.Output, MainPresenter>() {
    class Input : _Usecase.Input {}
    class Output(val profile: Profile) : _Usecase.Output {

    }

    override suspend fun execute(inputValues: Input): Output {
        val profile = database.getProfile().getCurrentProfile()

        profile?.let {
            return Output(profile)
        } ?: throw IllegalArgumentException("Profile not found")

    }

    override fun present(output: Output, presenter: MainPresenter) {
        presenter.setProfile(profile = output.profile)
    }
}