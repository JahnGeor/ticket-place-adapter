package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.application.port.*

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
        TODO("Not yet implemented")
    }

    override fun getProfile(): ProfilePort {
        return ProfileRepository(database)
    }

    override fun getSetting(): SettingPort {
        return SettingRepository(database)
    }

    override fun getHistory(): HistoryPort {
        TODO("Not yet implemented")
    }
}