package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.core.PoolingServiceControl
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingInfo
import java.util.*

class SaveSettingAction(commonPort: CommonPort) : Usecase<SaveSettingAction.Input, SaveSettingAction.Output>(commonPort) {

    class Input(val setting : SettingInfo, val restartListener: Boolean = true)

    class Output(val savedId : UUID)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
      try {
          if (input == null) {
              throw IllegalArgumentException("${SaveSettingAction::class.simpleName} требует наличие данных во входном наборе ${Input::class.qualifiedName}")
          }

          val settingId = commonPort.databasePort.getSetting().saveByCurrent(setting = input.setting)

          logger.trace("Настройки сохранены под id: {}", settingId)

          if (input.restartListener) {
              PoolingServiceControl(commonPort).invoke(PoolingServiceControl.PoolingCommand.RESTART, sceneManager)
          }

          val output = Output(savedId = settingId)

          sceneManager?.let {
              present(output, it)
          }

          return output
      } catch (e : Throwable) {
          logger.error("Во время сохранения настроек произошла ошибка: $e").also{ throw e }
      }

    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.showNotification(NotificationType.INFO, "Настройки успешно сохранены", "Сохранение настроек")
        sceneManager.openScene(Scene.MAIN)
    }

}