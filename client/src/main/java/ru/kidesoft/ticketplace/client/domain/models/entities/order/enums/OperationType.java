package ru.kidesoft.ticketplace.client.domain.models.entities.order.enums;

import java.util.Objects;

public enum OperationType {
    UNDEFINED(0, "undefined"),
    ORDER(1, "order"),
    REFUND(2, "refund");

    private final Integer id;
    private final String name;

    OperationType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OperationType getById(Integer id) {
        for (OperationType operationType : OperationType.values()) {
            if (Objects.equals(operationType.getId(), id)) {
                return operationType;
            }
        }
        return UNDEFINED;
    }

    public static OperationType getByName(String name) {
        for (OperationType operationType : OperationType.values()) {
            if (Objects.equals(operationType.getName(), name)) {
                return operationType;
            }
        }
        return UNDEFINED;
    }
}