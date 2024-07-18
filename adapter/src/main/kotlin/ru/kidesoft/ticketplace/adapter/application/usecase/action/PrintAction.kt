package ru.kidesoft.ticketplace.adapter.application.usecase.action

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.ApiFactory
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.port.KktPortFactory
import ru.kidesoft.ticketplace.adapter.application.port.KktType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.action.SaveSettingAction.Input
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.PrintType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType

class PrintAction(val databasePort: DatabasePort, val apiFactory: ApiFactory, val kktPortFactory: KktPortFactory) :
    _Usecase<PrintAction.Input, PrintAction.Output>() {
    private val logger = LogManager.getLogger()

    class Input : _Usecase.Input {
        var orderId: Int? = null
        var sourceType: SourceType? = null
        var operationType: OperationType? = null
    }

    class Output : _Usecase.Output {}

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        try {
            if (input == null) throw IllegalArgumentException("${this::class.simpleName} требует наличие данных во входном наборе ${Input::class.qualifiedName}")

            val sourceType = input.sourceType?.let { it }
                ?: throw IllegalArgumentException("${this::class.simpleName} требует наличие данных во входном наборе ${Input::sourceType::class.qualifiedName}")

            val orderId = input.orderId?.let { it }
                ?: throw IllegalArgumentException("${this::class.simpleName} требует наличие данных во входном наборе ${Input::orderId::class.qualifiedName}")

            val operationType = input.operationType?.let { it }
                ?: throw IllegalArgumentException("${this::class.simpleName} требует наличие данных во входном наборе ${Input::operationType::class.qualifiedName}")


            val activeSession =
                databasePort.getSession().getActive() ?: throw IllegalArgumentException("Активная сессия не найдена")
            val activeLogin = databasePort.getLogin().getCurrent()
                ?: throw IllegalArgumentException("Не найдены данные авторизации по текущему пользователю")
            val currentSetting = databasePort.getSetting().getByCurrentUser()
                ?: throw IllegalArgumentException("Не найдены данные настроек по текущему пользователю")
            val activeProfile = databasePort.getProfile().getCurrentProfile()
                ?: throw IllegalArgumentException("Не найдены данные профиля по текущему пользователю")

            var orderExposed = apiFactory.getInstance(
                activeLogin.url, timeout = currentSetting.server.requestTimeout,
                tokenType = activeSession.token.type,
                token = activeSession.token.value
            ).getOrder(orderId, sourceType).mapToEntity()

            var kktPort = kktPortFactory.getInstance(KktType.ATOL, activeLogin.id) ?: kktPortFactory.createInstance(
                KktType.ATOL,
                currentSetting.kkt,
                activeLogin.id
            )

            if (currentSetting.print.isPrintingCheck) {
                val transform = runCatching { orderExposed.buildFor(PrintType.CHECK) }

                if (transform.isSuccess) {
                    kktPort.print(activeProfile.cashier, transform.getOrNull()!!, operationType)
                } else {
                    // TODO: Запись в таблицу истории
                    logger.info("Не печатается по причине: ${transform.exceptionOrNull()!!.message}")
                }
            }

            val output = Output()

            return output
        } catch (e: Throwable) {
            logger.error("Во время печати чека и/или билета произошла ошибка: $e").also { throw e }
        }
    }

    override fun present(output: Output, sceneManager: SceneManager) {

    }

}