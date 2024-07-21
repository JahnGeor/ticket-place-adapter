package ru.kidesoft.ticketplace.adapter.domain.history

enum class Step {
    GET_ORDER, PRINT_TICKET, PRINT_CHECK, UNDEFINED;
}

enum class ErrorStatus {
    ERROR, SUCCESS
}