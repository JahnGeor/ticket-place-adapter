package ru.kidesoft.ticketplace.client.domain.models.entities.history.enums;

import java.util.Objects;

public enum StatusType {
    UNDEFINED(0, "Неопределено"), SUCCESS(1, "Выполнено"), WARN(2, "Предупреждение"),  ERROR(3, "Ошибка");
    private final Integer id;
    private final String name;

    StatusType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static StatusType getById(Integer id) {
        for (StatusType statusType : StatusType.values()) {
            if (Objects.equals(statusType.getId(), id)) {
                return statusType;
            }

        }
        return UNDEFINED;
    }
}
