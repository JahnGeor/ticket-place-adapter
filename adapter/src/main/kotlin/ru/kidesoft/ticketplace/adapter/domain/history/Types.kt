package ru.kidesoft.ticketplace.adapter.domain.history

enum class ReasonType(val id : Int, val value: String, val description : String) {
    UNDEFINED(0, "undefined", "Не определено"),
    PRINT_TICKET(1, "print_ticket", "Печать билета"),
    PRINT_CHECK(2, "print_check", "Печать чека");

    companion object {
        infix fun fromId(id: Int): ReasonType = values().firstOrNull() { it.id == id }?: UNDEFINED
        infix fun fromName(name: String): ReasonType = values().firstOrNull() { it.name == name }?: UNDEFINED
        infix fun fromDescription(description: String): ReasonType = values().firstOrNull() { it.description == description }?: UNDEFINED
    }
}

enum class StatusType(val id : Int, val value: String, val description: String) {
    UNDEFINED(0, "undefined", "Cтатус неопределён"),
    SUCCESS(1, "success", "Успешно"),
    ERROR(2, "error", "Ошибка");

    companion object {
        infix fun fromId(id: Int): StatusType = StatusType.values()
            .firstOrNull() { it.id == id }?: UNDEFINED
        infix fun fromName(name: String): StatusType = StatusType.values()
            .firstOrNull() { it.name == name }?: UNDEFINED
        infix fun fromDescription(description: String): StatusType = StatusType.values().firstOrNull() { it.description == description }?: UNDEFINED
    }
}
