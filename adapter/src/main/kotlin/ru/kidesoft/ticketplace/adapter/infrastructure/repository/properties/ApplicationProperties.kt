package ru.kidesoft.ticketplace.adapter.infrastructure.repository.properties

import ru.kidesoft.ticketplace.adapter.application.port.PropertiesPort
import java.util.*

class ApplicationProperties : PropertiesPort {
    override fun getVersion() : String {
        val properties = javaClass.classLoader.getResourceAsStream("application.properties").use {
            Properties().apply { load(it) }
        }

        return properties.getProperty("application.version")
    }
}