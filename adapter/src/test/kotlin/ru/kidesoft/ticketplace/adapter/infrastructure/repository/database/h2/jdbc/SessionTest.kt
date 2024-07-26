package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.kidesoft.ticketplace.adapter.DatabaseProperties
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginTestData
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.SessionTestData
import java.util.*

class SessionTest {
    val database = DatabaseRepositoryJdbc(DatabaseProperties().apply {
        url = "jdbc:h2:file:./test;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
        locations = "classpath:ru/kidesoft/ticketplace/adapter/database"
    })

    @Test
    fun saveByLoginId() {
        val loginList = LoginTestData.dinamicList(minSize = 120, maxSize = 12000)

        val sessionInfoList = SessionTestData.dinamicList(loginList.size)

        val loginUUIDList = mutableListOf<UUID>()

        loginList.forEachIndexed { _, login ->
            loginUUIDList.add(database.getLogin().save(login))
        }

        sessionInfoList.forEachIndexed { idx, _ ->
            val id = database.getSession().saveByLoginId(loginUUIDList[idx], sessionInfoList[idx], false)
            assertNotNull(id)
        }

        println(sessionInfoList.size)

    }

    @Test
    fun saveByCurrent() {
        val list = generateLoginTestData(database, 1, 20000)
        val sessionList = generateSessionTestData(database, list).also {
            println("Размер: ${it.size}")
        }
    }

    @Test
    fun deactivateExcept() {
        database.getSession().deactivateExcept(UUID.fromString("92ed26d7-895e-45d9-b720-a4a8abfe103b"))
    }

    @Test
    fun getActive() {
        val active = database.getSession().getActive()

        println(active)
    }

    @Test
    fun getByLoginId() {
    }

    @Test
    fun setActive() {
        database.getSession().setActive()
    }

    @Test
    fun setDeactive() {
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun deleteAll() {
    }

    @Test
    fun deleteActive() {
    }
}