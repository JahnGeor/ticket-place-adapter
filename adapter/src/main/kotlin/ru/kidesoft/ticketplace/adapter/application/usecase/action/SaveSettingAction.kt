package ru.kidesoft.ticketplace.adapter.application.usecase.action

import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.CommonPort
import ru.kidesoft.ticketplace.adapter.application.presenter.NotificationType
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
import java.util.*

class SaveSettingAction(commonPort: CommonPort) : Usecase<SaveSettingAction.Input, SaveSettingAction.Output>(commonPort) {

    class Input(val setting : SettingExposed)

    class Output(val savedId : UUID)

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
      try {
          if (input == null) {
              throw IllegalArgumentException("${SaveSettingAction::class.simpleName} требует наличие данных во входном наборе ${Input::class.qualifiedName}")
          }

          val currentLogin = commonPort.databasePort.getLogin().getByCurrent()?: throw NullPointerException("Не обнаружен текущий пользователь")

          input.setting.loginId = currentLogin.id

          val setting = commonPort.databasePort.getSetting().getByCurrent()?.let {
              commonPort.databasePort.getSetting().update(id = it.id, setting = input.setting)
          } ?: commonPort.databasePort.getSetting().create(setting = input.setting)

          logger.trace("Настройки сохранены под id: ${setting.id}")

          val output = Output(savedId = setting.id)

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