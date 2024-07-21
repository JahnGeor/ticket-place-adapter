package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.junit.jupiter.api.Test
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.domain.history.HistoryPayload
import ru.kidesoft.ticketplace.adapter.domain.history.
StatusType
import ru.kidesoft.ticketplace.adapter.domain.login.LoginExposed
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.ZonedDateTime

class HistoryRepositoryTest {

    @Test
    fun addHistory() {

        val dProp = DatabaseProperties().apply {
            driverClassName = "org.h2.Driver"
            url = "jdbc:h2:file:./build/test/workingDir/test"
            username = "sa"
            password = "sa"
            locations = "classpath:ru/kidesoft/ticketplace/adapter/database"
        }

        val h2DatabaseRepository = Database(dProp)

        val loginExposed = LoginExposed().apply {
            url = "ticketplace.ru"
            password = "asfdfs"
            email = "fasdjafslkj@.ru"
        }

        val loginId = h2DatabaseRepository.getLogin().getLoginId(loginExposed.email, loginExposed.url) ?: let {
            h2DatabaseRepository.getLogin().create(loginExposed).id
        }

        h2DatabaseRepository.getHistory().save(1, HistoryPayload(
            createdAt = ZonedDateTime.now(),
            error = "Ошибка №2",
            sourceType = SourceType.REFUND,
            operationType = OperationType.ORDER,
            statusType = StatusType.ERROR
        ))
    }
}

