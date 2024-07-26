package ru.kidesoft.ticketplace.adapter.domain.profile

enum class RoleType(val id: Int, val value: String, val description: String) {
    UNDEFINED(0, "undefined", "Неизвестно"),
    ADMIN(1, "admin", "Администратор"),
    USER(2, "user", "Пользователь"),
    SELLER(3, "seller", "Кассир"),
    AGENT(4, "agent", "Агент"),
    SENIOR_SELLER(5, "senior_seller", "Старший кассир"),
    ACCOUNTANT(6, "accountant", "Бухгалтер"),
    MANAGER_HALL(7, "manager_hall", "Менеджер зала"),
    CLIENT(8, "client", "Покупатель"),
    GATEWAY(9, "gateway", "Шлюз");

    companion object {
        infix fun fromId(id: Int): RoleType = RoleType.values()
            .firstOrNull() { it.id == id }?: UNDEFINED
        infix fun fromName(name: String): RoleType = RoleType.values()
            .firstOrNull() { it.name == name }?: UNDEFINED
        infix fun fromDescription(description: String): RoleType = RoleType.values().firstOrNull() { it.description == description }?: UNDEFINED
    }
}