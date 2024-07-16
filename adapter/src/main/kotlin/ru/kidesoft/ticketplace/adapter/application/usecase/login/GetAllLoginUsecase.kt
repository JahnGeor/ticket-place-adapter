package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.WebServerApiFactory

class GetAllLoginUsecase(private val databasePort: DatabasePort, private val apiFactory: WebServerApiFactory) :
    _Usecase<GetAllLoginUsecase.Input, GetAllLoginUsecase.Output>() {

    class Input : _Usecase.Input {}
    class Output : _Usecase.Output {
        var emails = listOf<String>()
        var urls = listOf<String>()
    }

    override suspend fun execute(inputValues: Input?, sceneManager: SceneManager?): Output {
        val list = databasePort.getLogin().GetAll()

        var emails = mutableListOf<String>()
        var urls = mutableListOf<String>()

        list.distinct().map {
            emails.add(it.email)
            urls.add(it.url)
        }

        if (urls.isEmpty()) {
            urls = apiFactory.getApis().toMutableList()
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