package ru.kidesoft.ticketplace.adapter.application.port

class CommonPort(val databasePort: DatabasePort, val kktPortFactory: KktPortFactory, val apiPortFactory: ApiPortFactory, val propertiesPort: PropertiesPort, val jobPort: JobPort, val printerPort: PrinterPort) {

}