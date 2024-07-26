package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import org.flywaydb.core.Flyway
import org.h2.mvstore.tx.Transaction
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.application.port.*
import java.sql.Connection
import java.sql.DriverManager

const val LOGIN_TABLE = "LOGIN"
const val SESSION_TABLE = "SESSION"
const val CLICK_TABLE = "CLICK"
const val SETTING_TABLE = "SETTING"
const val PROFILE_TABLE = "PROFILE"
const val HISTORY_TABLE = "HISTORY"

class DatabaseRepositoryJdbc(databaseProperties: DatabaseProperties) : DatabasePort {
    val connection: Connection

    init {
        val flyway: Flyway = Flyway.configure().schemas("PUBLIC")
            .dataSource(databaseProperties.url, databaseProperties.username, databaseProperties.password)
            .locations(databaseProperties.locations).load()

        val migrationInfo = flyway.migrate()

        println("База данных: ${migrationInfo.database}")
        println("Выполнено миграций: ${migrationInfo.migrationsExecuted}")
        println("Успешных миграций: ${migrationInfo.successfulMigrations.count()}")
        println("Неуспешных миграций: ${migrationInfo.failedMigrations.count()}")
        println("Прошлая версия: ${migrationInfo.initialSchemaVersion ?: "нет"}")
        println("Новая версия: ${migrationInfo.targetSchemaVersion}")

        connection = DriverManager.getConnection(
            databaseProperties.url,
            databaseProperties.username,
            databaseProperties.password
        )
    }

    override fun getLogin(): LoginPort = LoginRepositoryJdbc(connection)

    override fun getSession(): SessionPort = SessionRepositoryJdbc(connection)

    override fun getClick(): ClickPort = ClickRepositoryJdbc(connection, getSession())// ClickRepositoryJdbc(connection)

    override fun getProfile(): ProfilePort = ProfileRepositoryJdbc(connection, getSession())

    override fun getSetting(): SettingPort = SettingRepositoryJdbc(connection, getSession())

    override fun getHistory(): HistoryPort = HistoryRepositoryJdbc(connection, getSession())

    override fun execTransaction(transaction: () -> Boolean) {
        connection.autoCommit = false

        connection.runCatching { transaction.invoke() }
            .onFailure {
                connection.rollback()
            }.onSuccess {
                if ( it ) connection.commit() else connection.rollback()
            }.also {
                connection.autoCommit = true
            }.getOrThrow()

    }
}