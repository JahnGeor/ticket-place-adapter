package ru.kidesoft.ticketplace.adapter.application.usecase.login

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.Presenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.WebServerApiFactory

class GetAllLoginUsecase(private val databasePort: DatabasePort, private val apiFactory: WebServerApiFactory) :
    _Usecase<GetAllLoginUsecase.GetAllLoginUsecaseInput, GetAllLoginUsecase.GetAllLoginUsecaseOutput>() {

    class GetAllLoginUsecaseInput : _Usecase.Input {}
    class GetAllLoginUsecaseOutput : _Usecase.Output {
        var emails = listOf<String>()
        var urls = listOf<String>()
    }

    override suspend fun execute(inputValues: GetAllLoginUsecaseInput?): GetAllLoginUsecaseOutput {
        val list = databasePort.getLogin().GetAll()
        var output = GetAllLoginUsecaseOutput()

        var emails = mutableListOf<String>()
        var urls = mutableListOf<String>()

        list.distinct().map {
            emails.add(it.email)
            urls.add(it.url)
        }

        if (output.urls.isEmpty()) {
            output.urls = apiFactory.getApis()
        } else {
            output.urls = urls.distinct()
        }

        output.emails = emails.distinct()

        return output
    }

    override fun present(output: GetAllLoginUsecaseOutput, sceneManager: SceneManager) {
        sceneManager.getPresenter(AuthPresenter::class)?.setEmails(output.emails)
        sceneManager.getPresenter(AuthPresenter::class)?.setUrls(output.urls)
    }

}