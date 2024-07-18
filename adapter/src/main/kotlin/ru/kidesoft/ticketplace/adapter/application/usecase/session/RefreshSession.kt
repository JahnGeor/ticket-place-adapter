package ru.kidesoft.ticketplace.adapter.application.usecase.session

import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase

class RefreshSession : _Usecase<RefreshSession.Input, RefreshSession.Output>() {
    class Input : _Usecase.Input
    class Output : _Usecase.Output

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        // TODO: val login = getCurrentLogin()

        // TODO: webport.login(login.email, login.password, login.url)

        // TODO: Profile.update()

        // TODO: Session.update()

        // + TODO: Если не выходит выполнить авторизацию -> делаем выход (обязательно выключить Listener)

        return Output()
    }
}