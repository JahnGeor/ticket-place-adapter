package ru.kidesoft.ticketplace.client.repository.database.h2;

public enum TableNames {
    LOGIN("LOGIN", "Таблица авторизаций"),
    CONSTANT("CONSTANT", "Таблица констант"),
    CLICK("CLICK", "Таблица журнала опроса сервера"),
    PROFILE("PROFILE", "Таблица профиля пользователей"),
    SETTING("SETTING", "Таблица настроек"),
    SESSION("SESSION", "Таблица сессий"),
    HISTORY("HISTORY", "Таблица журнала событий");

    private String name;
    private String description;

    TableNames(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
