package ru.kidesoft.ticketplace.adapter.application.usecase.updater

import ru.kidesoft.ticketplace.adapter.application.port.ApiPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.AuthPresenter
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetKktPort
import ru.kidesoft.ticketplace.adapter.application.usecase.login.GetAllLoginUsecase
import ru.kidesoft.ticketplace.adapter.application.usecase.profile.GetCashierListUsecase
import ru.kidesoft.ticketplace.adapter.domain.profile.Cashier

class UpdateAuth(commonPort: CommonPort) :
    Usecase<UpdateAuth.Input, UpdateAuth.Output>(commonPort) {
    class Input

    class Output {

        var emails = listOf<String>()
        var urls = listOf<String>()
        var cashiers = listOf<Cashier>()

    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val loginOutput = GetAllLoginUsecase(commonPort).invoke()

        val cashierOutput = GetCashierListUsecase(commonPort).invoke()

        val output = Output().apply {
            this.cashiers = cashierOutput.cashierList
            this.urls = loginOutput.urls
            this.emails = loginOutput.emails
        }

        sceneManager?.let { _present(output, sceneManager) }

        return output
    }

    private fun _present(output: Output, sceneManager: SceneManager) {
        val authPresenter = sceneManager.getPresenter(AuthPresenter::class)?: throw NullPointerException("${AuthPresenter::class::simpleName} not found")
        authPresenter.setUrls(output.urls)
        authPresenter.setEmails(output.emails)
        authPresenter.setCashiers(output.cashiers)
    }
}