package ru.kidesoft.ticketplace.adapter.application.usecase.action

import io.ktor.client.request.forms.*
import io.ktor.utils.io.core.*
import org.apache.logging.log4j.LogManager
import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.application.presenter.Scene
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase._Usecase
import ru.kidesoft.ticketplace.adapter.domain.setting.SettingExposed
import java.util.*

class SaveSettingAction(val databasePort: DatabasePort) : _Usecase<SaveSettingAction.Input, SaveSettingAction.Output>() {

    private val logger = LogManager.getLogger()

    class Input(val setting : SettingExposed) : _Usecase.Input {}

    class Output(val savedId : UUID) : _Usecase.Output {

    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
      try {
          if (input == null) {
              throw IllegalArgumentException("${SaveSettingAction::class.simpleName} требует наличие данных во входном наборе ${Input::class.qualifiedName}")
          }

          val currentLogin = databasePort.getLogin().getCurrent()?: throw NullPointerException("Не обнаружен текущий пользователь")

          input!!.setting.loginId = currentLogin.id

          val setting = databasePort.getSetting().getByCurrentUser()?.let {
              databasePort.getSetting().update(id = it.id, setting = input!!.setting)
          } ?: databasePort.getSetting().create(setting = input!!.setting)

          logger.trace("Настройки сохранены под id: ${setting.id}")

          val output = Output(setting.id)

          sceneManager?.let {
              present(output, it)
          }

          return output
      } catch (e : Throwable) {
          logger.error("Во время сохранения настроек произошла ошибка: $e").also{ throw e }
      }

    }

    override fun present(output: Output, sceneManager: SceneManager) {
        sceneManager.openScene(Scene.MAIN)
    }

}