package ru.kidesoft.ticketplace.adapter

import java.util.Properties

class ApplicationProperties() {
    lateinit var version: String
    var database: DatabaseProperties = DatabaseProperties()

    init {
        val properties = Properties()
        properties.load(javaClass.getResourceAsStream("/application.properties"))

        version = properties.getProperty("application.version")
        database.url = properties.getProperty("application.database.url")
        database.username = properties.getProperty("application.database.username")
        database.password = properties.getProperty("application.database.password")
        database.driverClassName = properties.getProperty("application.database.driverClassName")
        database.locations = properties.getProperty("application.database.locations")
    }
}

class DatabaseProperties {
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
    lateinit var driverClassName: String
    lateinit var locations : String
}