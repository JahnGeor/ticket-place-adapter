package ru.kidesoft.ticketplace.adapter.application.usecase

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.action.PrintAction
import ru.kidesoft.ticketplace.adapter.application.usecase.web.GetWebPort

class ClickProcess(commonPort: CommonPort) : Usecase<ClickProcess.Input, ClickProcess.Output>(commonPort) {
    class Input : Usecase.Input {

    }
    class Output : Usecase.Output {
        var orderId: Int? = null
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        val webPort = GetWebPort(commonPort).invoke(sceneManager = sceneManager).webPort

        val click = webPort.getClick().mapToEntity()

        logger.trace("От сервера получен клик №${click.clickId} - заказ №${click.orderId}, ${click.sourceType}")
        val storedClick = commonPort.databasePort.getClick().getByCurrent()

        storedClick?.let {
            if (it.clickId == click.clickId) {
                logger.trace("Заказ №${click.orderId} с номером клика №${click.clickId} уже хранится в базе данных")
            } else {
                logger.trace("Совершаем печать заказа №${click.orderId} с номером клика ${click.clickId}")
                PrintAction(commonPort).invoke(PrintAction.Input(click.orderId, click.sourceType))
                commonPort.databasePort.getClick().save(click)
                logger.trace("Сохраняем текущий клик")
                output.orderId = click.orderId
            }
        }?: run {
            commonPort.databasePort.getClick().save(click)
            logger.trace("Не найден сохраненный заказ, сохраняем данные по текущему заказу")
        }


        sceneManager?.let {
            present(output, it)
        }

        return output
    }

    override fun present(output: Output, sceneManager: SceneManager) {
        output.orderId?.let {
            sceneManager.showNotification(NotificationType.INFO, "Печать", "Чек №${it} успешно напечатан")
        }
    }
}