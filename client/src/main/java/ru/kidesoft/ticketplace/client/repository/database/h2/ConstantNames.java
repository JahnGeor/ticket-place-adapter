package ru.kidesoft.ticketplace.client.repository.database.h2;

public enum ConstantNames {
    ACTIVE_LOGIN_UUID("ACTIVE_LOGIN_UUID"),
    DEFAULT_URL("DEFAULT_URL");

    private String name;

    ConstantNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
