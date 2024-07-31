package ru.kidesoft.ticketplace.adapter.ui.view.dialog

import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import kotlinx.coroutines.*
import ru.kidesoft.ticketplace.adapter.application.presenter.dialog.ClickDialog
import ru.kidesoft.ticketplace.adapter.ui.StageManager
import ru.kidesoft.ticketplace.adapter.ui.view.DialogController


class ClickDialogController : DialogController(), ClickDialog {

    private lateinit var alert: Alert

    override suspend fun showAndWait(orderId: Int, clickId: Int): Boolean {
    val result = CompletableDeferred<Boolean>()

        Platform.runLater {
            alert = Alert(Alert.AlertType.CONFIRMATION)

            StageManager.setOwnerProperties(alert.dialogPane.scene.window  as  Stage)

            alert.headerText = "Запрос на повторную печать"
            alert.contentText =
                "Вы дважды отправили на печать чек по одному и тому же заказу. Вы хотите распечатать чек?"

            val yesBtn =
                ButtonType("Да", ButtonData.YES)
            val noBtn =
                ButtonType("Нет", ButtonData.NO)

            alert.buttonTypes.setAll(yesBtn, noBtn)

            alert.initOwner(getSceneManager().stage)

            alert.showAndWait().ifPresent { button ->
                result.complete(button == yesBtn)
            }
        }

        return result.await()
    }

    override fun close() {
        Platform.runLater {
            alert.close()
        }
    }
}
