package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import net.datafaker.providers.base.Bool
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.statements.GlobalStatementInterceptor
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace.ClickData

class Database(databaseProperties: DatabaseProperties) : DatabasePort {
    private val database: Database

    init {
        val flyway : Flyway = Flyway.configure().schemas("PUBLIC").dataSource(databaseProperties.url, databaseProperties.username, databaseProperties.password).locations(databaseProperties.locations).load()

        val migrationInfo = flyway.migrate()

        println("База данных: ${migrationInfo.database}")
        println("Выполнено миграций: ${migrationInfo.migrationsExecuted}")
        println("Успешных миграций: ${migrationInfo.successfulMigrations.count()}")
        println("Неуспешных миграций: ${migrationInfo.failedMigrations.count()}")
        println("Прошлая версия: ${migrationInfo.initialSchemaVersion?: "нет"}")
        println("Новая версия: ${migrationInfo.targetSchemaVersion}")

        flyway.info()

        database = Database.connect(databaseProperties.url, driver = databaseProperties.driverClassName, user = databaseProperties.username, password = databaseProperties.password)
        // TODO: добавить проверку и обработку ошибок миграции
    }

    override fun getLogin(): LoginPort {
        return LoginRepository(database)
    }

    override fun getSession(): SessionPort {
        return SessionRepository(database)
    }

    override fun getClick(): ClickPort {
        return ClickRepository(database)
    }

    override fun getProfile(): ProfilePort {
        return ProfileRepository(database)
    }

    override fun getSetting(): SettingPort {
        return SettingRepository(database)
    }

    override fun getHistory(): HistoryPort {
        return HistoryRepository(database)
    }

    override fun execTransaction(block: () -> Boolean) {
        val t = database.transactionManager.newTransaction()

        try {
            val isSuccess = block.invoke()
            if (isSuccess) t.commit() else t.rollback()
        } catch (e : Exception) {
            t.rollback()
            throw e
        } finally {
            t.close()
        }

    }


}