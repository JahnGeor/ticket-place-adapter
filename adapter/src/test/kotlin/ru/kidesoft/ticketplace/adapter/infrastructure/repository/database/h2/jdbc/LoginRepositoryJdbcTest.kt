package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginTestData

class LoginRepositoryJdbcTest {
    val database = DatabaseRepositoryJdbc(DatabaseProperties().apply {
        url = "jdbc:h2:./test;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
        locations = "classpath:ru/kidesoft/ticketplace/adapter/database"
    })


    @Test
    fun getLoginId() {

    }

    @Test
    @DisplayName("Сохранение > 10")
    fun saveMultiple() {
        generateLoginTestData(database, 10, 10000).also {
            println("Размер тестируемого объема сообщений: ${it.size}")
        }
    }

    @Test
    @DisplayName("Сохранение 1 сообщения")
    fun save() {
        generateLoginTestData(database, 1, 1)
    }

    @Test
    fun getAll() {
        deleteAll()

        val loginListInserted = generateLoginTestData(database, 1, 10000).also { println("Размер тестируемого объема сообщений: ${it.size}")}

        val listGet = database.getLogin().getAll()

        assertEquals(loginListInserted.size, listGet.size)
    }

    @Test
    fun getByCurrent() {

    }

    @Test
    fun deleteAll() {
        val deleted = database.getLogin().deleteAll()

        println("Удалено строк: $deleted")

        val list = database.getLogin().getAll()

        assertEquals(0, list.size)
    }

    @Test
    fun deleteById() {

    }


}