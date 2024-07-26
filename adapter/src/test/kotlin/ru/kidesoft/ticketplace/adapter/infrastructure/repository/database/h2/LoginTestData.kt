package ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2

import net.datafaker.Faker
import ru.kidesoft.ticketplace.adapter.domain.login.LoginInfo
import ru.kidesoft.ticketplace.adapter.domain.session.SessionInfo
import ru.kidesoft.ticketplace.adapter.domain.session.Token
import ru.kidesoft.ticketplace.adapter.domain.setting.*
import java.time.Duration
import java.time.ZoneId

object LoginTestData {
    fun dinamicList(minSize : Int = 1, maxSize: Int = 10) : MutableList<LoginInfo> {
        val mutableList = mutableListOf<LoginInfo>()

        val maxNumber = Faker().number().numberBetween(minSize, maxSize)

        for (i in 1..maxNumber) {
            mutableList.add(
                LoginInfo(
                    url = Faker().internet().url(),
                    email = Faker().internet().emailAddress(),
                    password = Faker().internet().username()
                )
            )
        }

        return mutableList
    }
}

object SessionTestData {
    fun dinamicList(size: Int) : MutableList<SessionInfo> {
        val faker = Faker()

        val mutableList = mutableListOf<SessionInfo>()

        for (login in 1..size) {
            val createdAt = faker.timeAndDate().past().atZone(ZoneId.of("Europe/Moscow"))
            mutableList.add(
                SessionInfo(Token(
                    faker.bothify("??????"), faker.internet().uuid(), createdAt, createdAt.plusDays(1)
                ))
            )
        }

        return mutableList
    }
}

object SettingTestData {
    fun dinamicList(minSize: Int = 1, maxSize: Int = 10) : MutableList<SettingInfo> {
        val faker = Faker()
        val mutableList = mutableListOf<SettingInfo>()
        val maxNumber = Faker().number().numberBetween(minSize, maxSize)
        for (i in 1..maxNumber) {
            mutableList.add(
                SettingInfo(
                    KktSetting(
                        faker.internet().url(),
                        faker.bool().bool()
                    ),
                    ServerSetting(Duration.ofSeconds((faker.number().numberBetween(1, 13) * 5).toLong()), Duration.ofSeconds((faker.number().numberBetween(1, 13) * 5).toLong())),
                    UpdateSetting(faker.bool().bool(), faker.internet().url()),
                    PrintSetting(
                        faker.bool().bool(), faker.bool().bool(), PageSize.entries.find { it.ordinal ==  faker.number().numberBetween(0, 2)} ?: PageSize.UNDEFINED, PageOrientation.entries.find { it.ordinal ==  faker.number().numberBetween(0, 2)} ?: PageOrientation.UNDEFINED, faker.device().modelName()
                    ),
                )
            )
        }
        return mutableList
    }
}