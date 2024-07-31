package ru.kidesoft.ticketplace.adapter.application.usecase.core

import kotlinx.coroutines.withTimeoutOrNull
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.presenter.dialog.ClickDialog
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintAction
import ru.kidesoft.ticketplace.adapter.application.usecase.web.GetWebPort
import ru.kidesoft.ticketplace.adapter.domain.click.ClickInfo
import java.time.Duration

class ClickProcess(commonPort: CommonPort) : Usecase<ClickProcess.Input, ClickProcess.Output>(commonPort) {
    class Input
    class Output {
        var orderId: Int? = null
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        val webPort = GetWebPort(commonPort).invoke(sceneManager = sceneManager).webPort

        val clickInfo = webPort.getClick().mapToEntity()

        logger.trace("От сервера получен клик №{} - заказ №{}, {}", clickInfo.clickId, clickInfo.orderId, clickInfo.sourceType)

        val storedClick = commonPort.databasePort.getClick().getByCurrent()

        if (storedClick != null) {
            if (storedClick.clickId == clickInfo.clickId) {
                logger.trace("Заказ №${clickInfo.orderId} с номером клика №${clickInfo.clickId} уже хранится в базе данных")
            } else {
                if (storedClick.orderId == clickInfo.orderId) {
                    orderAlreadyExist(storedClick.toClickInfo(), clickInfo, sceneManager!!)
                } else {
                    print(clickInfo)
                }
            }
        } else {
            logger.trace("Не найден сохраненный заказ, сохраняем данные по текущему заказу ${clickInfo.orderId} с номером задачи ${clickInfo.clickId}")
        }

        save(clickInfo)



        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    private suspend fun orderAlreadyExist(storedClickInfo : ClickInfo, newClickInfo : ClickInfo, sceneManager: SceneManager) {
        logger.warn("Заказ №${storedClickInfo.orderId} с номером клика №${storedClickInfo.clickId} уже хранится в базе данных. Текущий номер клика №${newClickInfo.clickId}")

        val duration = Duration.between(storedClickInfo.createdAt, newClickInfo.createdAt)

        if (duration > Duration.ofSeconds(15)) {
            print(newClickInfo)
        } else {
            val dialog = sceneManager.getPresenter(ClickDialog::class) ?: throw IllegalArgumentException("Презентер диалогового окна ${ClickDialog::class} не найден")

            val result = withTimeoutOrNull(5000) {
                val resultOfDialog = dialog.showAndWait(newClickInfo.orderId, newClickInfo.clickId)
                if (resultOfDialog) logger.info("Пользователь подтвердил печать чека") else logger.info("Пользователь отклонил печать чека")
                resultOfDialog
            } ?: kotlin.run {
                logger.info("Время ожидания ответа от пользователя вышло, запрос на печать отклонен")
                dialog.close()
                false
            }

            if (result) {
                print(newClickInfo)
            }
        }

    }


    suspend fun print(clickInfo: ClickInfo) {
        logger.trace("Совершаем печать заказа №${clickInfo.orderId} с номером задачи №${clickInfo.clickId}")
        PrintAction(commonPort).invoke(PrintAction.Input(clickInfo.orderId, clickInfo.sourceType))
    }

    fun save(clickInfo: ClickInfo) {
        commonPort.databasePort.getClick().saveByCurrent(clickInfo)
        logger.trace("Текущая задача ${clickInfo.clickId} заказа №${clickInfo.orderId} сохранена в базе данных")
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        output.orderId?.let {
            sceneManager.showNotification(NotificationType.INFO, "Печать", "Чек №${it} успешно напечатан")
        }
    }
}