package ru.kidesoft.ticketplace.adapter.domain

enum class ShiftState(val id : Int, val value: String, val description : String) {
    UNDEFINED(0, "undefined", ""), OPENED(1, "", ""), CLOSED(2, "closed", "Закрыта"), EXPIRED(3, "expired", "Истекла");

    companion object {
        infix fun fromValue(value: String): ShiftState? = values().firstOrNull() { it.value == value }?:UNDEFINED
        infix fun fromDescription(description: String): ShiftState? = values().firstOrNull() { it.description == description }?:UNDEFINED
        infix fun fromId(id: Int): ShiftState? = values().firstOrNull() { it.id == id }?:UNDEFINED
    }
}

