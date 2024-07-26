package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.jdbc

import ru.kidesoft.ticketplace.adapter.application.port.DatabasePort
import ru.kidesoft.ticketplace.adapter.domain.login.Login
import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import ru.kidesoft.ticketplace.adapter.domain.session.Session
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginTestData
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.SessionTestData


fun generateLoginTestData(database: DatabasePort, min: Int, max: Int): List<Login> {
    val loginInfoList = LoginTestData.dinamicList(min, max)

    val loginList = mutableListOf<Login>()

    for (i in loginInfoList) {
        val id = database.getLogin().save(i)
        loginList.add(Login(id, i))
    }

    return loginList
}

fun generateSessionTestData(database: DatabasePort, loginList: List<Login> ): List<Session> {
    val sessionTestData = SessionTestData.dinamicList(loginList.size)

    val sessionList = mutableListOf<Session>()

    loginList.forEachIndexed { index, login ->
        val id = database.getSession().saveByLoginId(login.id, sessionTestData[index], false)
        sessionList.add(Session(id, login.id, sessionTestData[index], false))
    }

    return sessionList
}
