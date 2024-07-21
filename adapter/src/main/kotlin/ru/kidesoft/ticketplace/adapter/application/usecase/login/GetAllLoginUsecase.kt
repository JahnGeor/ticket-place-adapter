package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase

class GetAllLoginUsecase(commonPort: CommonPort) :
    Usecase<GetAllLoginUsecase.Input, GetAllLoginUsecase.Output>(commonPort) {

    class Input : Usecase.Input {}
    class Output : Usecase.Output {
        var emails = listOf<String>()
        var urls = listOf<String>()
    }

    override suspend fun invoke(inputValues: Input?, sceneManager: SceneManager?): Output {
        val list = commonPort.databasePort.getLogin().getAll()

        var emails = mutableListOf<String>()
        var urls = mutableListOf<String>()

        list.distinct().map {
            emails.add(it.email)
            urls.add(it.url)
        }

        if (urls.isEmpty()) {
            urls = commonPort.apiPortFactory.getApis().toMutableList()
        }


        sceneManager?.let { it ->
            it.getPresenter(AuthPresenter::class)?.let {
                it -> it.also { it.setEmails(emails) }.also { it.setUrls(urls) }
            }
        }

        val output = Output()
        output.urls = urls.distinct()
        output.emails = emails.distinct()

        return output
    }
}