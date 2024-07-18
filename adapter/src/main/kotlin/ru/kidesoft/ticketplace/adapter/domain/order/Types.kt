package ru.kidesoft.ticketplace.adapter.domain.order

enum class SourceType {
    ORDER, REFUND;
}

enum class PaymentType(val value: String) {
    UNDEFINED("undefined"),
    CASH("cash"),
    CARD("card"),
    ONLINE("online"),
    INVITED("invited"),
    CHECKING_ACCOUNT("checking_account"),
    ACCOUNT_INDIVIDUAL("account_individual");

    companion object {
        fun findByValue(value: String) : PaymentType = PaymentType.values().find { it.value == value } ?: UNDEFINED
    }
}

enum class OperationType {
    REFUND, ORDER
}

enum class StatusType(val value: String) {
    UNDEFINED("undefined"), CREATED("created"), PAYED("payed"), RETURNED("returned"), REVOKED("revoked");

    companion object {
        fun findByValue(value: String) : StatusType = values().find { it.value == value } ?: UNDEFINED
    }
}

enum class PrintType() {
    CHECK, TICKET
}