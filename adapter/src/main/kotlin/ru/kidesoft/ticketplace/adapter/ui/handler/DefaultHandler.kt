package ru.kidesoft.ticketplace.adapter.ui.handler

import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.serialization.SerializationException
import ru.kidesoft.ticketplace.adapter.application.presenter.AlertType
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.domain.exception.AuthorizationException
import ru.kidesoft.ticketplace.adapter.domain.exception.PoolerException
import ru.kidesoft.ticketplace.adapter.domain.exception.ValidationException
import ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol.KktException
import java.io.IOException
import java.sql.SQLException
import java.util.concurrent.TimeoutException

class DefaultHandler(val sceneManager: SceneManager) : Handler {


    override fun handle(exception: Throwable) {
        // TODO: сделать разветвление, пока просто - ошибки

    when (exception) {
        is KktException -> kktHandle(exception)
        is PoolerException -> poolerHandler(exception)
        is AuthorizationException -> authorizationHandler(exception)
        is ValidationException -> validationHandler(exception)
        else -> defaultHandler(exception)
    }

    }

    fun validationHandler(e : ValidationException) {
        sceneManager.showNotification(NotificationType.ERROR, "Во время проверки данных произошла ошибка", e.message?: "Неизвестная ошибка валидации данных")
    }

    fun kktHandle(e: KktException) {
        sceneManager.showAlert(AlertType.ERROR, "Во время работы с кассой произошла ошибка", e.message?.let { it }?:e.cause?.message?.let { it }?:"Неизвестная ошибка")
    }

    fun poolerHandler(e : PoolerException) {
        sceneManager.showAlert(AlertType.ERROR, "Во время работы со службой опроса сервера произошла ошибка", "${e.message}")
    }

    fun defaultHandler(e: Throwable) {
        sceneManager.showAlert(AlertType.ERROR, "Во время выполнения операции произошла ошибка", "Ошибка неизвестного типа", e)
    }

    fun authorizationHandler(e : AuthorizationException) {
        if (e.cause is ResponseException) {
             when (e.cause) {
                 is ClientRequestException -> clientAuthorizationHandler(e.cause as ClientRequestException)
                 is ServerResponseException -> serverAuthorizationHandler(e.cause as ServerResponseException)
                 is SerializationException -> serializerAuthorizationHandler(e.cause as SerializationException)
                 is IOException -> newtworkAuthorizationHandler(e.cause as IOException)
             }
        }

        if (e.cause is HttpRequestTimeoutException) timeoutAuthorizationHandler(e.cause as HttpRequestTimeoutException)

        if (e.cause is SQLException) {
            sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Ошибка работы с базой данных")
        }
    }

    fun clientAuthorizationHandler(e : ClientRequestException) {
        if (e.response.status == HttpStatusCode.Unauthorized || e.response.status == HttpStatusCode.Forbidden) {
            sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Неправильный логин или пароль")
        }
    }

    fun serverAuthorizationHandler(e : ServerResponseException) {
        sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Сервер не смог обработать запрос на авторизацию. Пожалуйста, повторите попытку")
    }

    fun serializerAuthorizationHandler(e : SerializationException) {
        sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Невозможно обработать ответ от сервера, неизвестная структура ответа")
    }

    fun newtworkAuthorizationHandler(e : IOException) {
        sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Потеряно соединение с интернетом или сервер недоступен. Пожалуйста, проверьте состояние соединения и повторите попытку снова.")
    }

    fun timeoutAuthorizationHandler(e : HttpRequestTimeoutException) {
        sceneManager.showAlert(AlertType.ERROR, "Ошибка авторизации на удаленном сервере", "Не удалось получить ответ от сервера за установленное время. Пожалуйста, повторите попытку или обратитесь к системному администратору.")
    }
}