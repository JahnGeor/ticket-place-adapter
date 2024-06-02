package ru.kidesoft.desktop.domain.entity.profile;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleType {
    ADMIN(0, "admin", "Администратор"),
    USER(1, "user", "Пользователь"),
    SELLER(2, "seller", "Кассир"),
    AGENT(3, "agent", "Агент"),
    SENIOR_SELLER(4, "senior_seller", "Старший кассир"),
    ACCOUNTANT(5, "accountant", "Бухгалтер"),
    MANAGER_HALL(6, "manager_hall", "Менеджер зала"),
    CLIENT(7, "client", "Покупатель"),
    GATEWAY(8, "gateway", "Шлюз");

    private final int id;
    private final String name;
    private final String description;

}
