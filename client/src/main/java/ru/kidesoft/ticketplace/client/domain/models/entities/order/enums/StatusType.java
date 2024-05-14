package ru.kidesoft.ticketplace.client.domain.models.entities.order.enums;

import java.util.Objects;

public enum StatusType {
    UNDEFINED(0, "undefined", "Не определен"),
    CREATED(1, "created", "Создан"),
    PAYED(2, "payed", "Оплачен"),
    RETURNED(3, "returned", "Возвращен"),
    REVOKED(4, "revoked", "Аннулирован");

    private final Integer id;
    private final String name;
    private final String description;

    StatusType(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static StatusType getById(Integer id) {
        for (StatusType statusType : StatusType.values()) {
            if (Objects.equals(statusType.getId(), id)) {
                return statusType;
            }
        }

        return UNDEFINED;
    }

    public static StatusType getByName(String name) {
        for (StatusType statusType : StatusType.values()) {
            if (Objects.equals(statusType.getName(), name)) {
                return statusType;
            }
        }

        return UNDEFINED;
    }
}
