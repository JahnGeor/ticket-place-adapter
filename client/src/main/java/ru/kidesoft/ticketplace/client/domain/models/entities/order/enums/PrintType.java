package ru.kidesoft.ticketplace.client.domain.models.entities.order.enums;

import java.util.Objects;

public enum PrintType {
    UNDEFINED(0, "undefined"),
    CHECK(1, "check"),
    TICKET(2, "ticket");

    private final Integer id;
    private final String name;

    PrintType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static PrintType getById(Integer id) {
        for (PrintType printType : PrintType.values()) {
            if (Objects.equals(printType.getId(), id)) {
                return printType;
            }
        }
        return UNDEFINED;
    }

    public static PrintType getByName(String name) {
        for (PrintType printType : PrintType.values()) {
            if (Objects.equals(printType.getName(), name)) {
                return printType;
            }
        }
        return UNDEFINED;
    }
}