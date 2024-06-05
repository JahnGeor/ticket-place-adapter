package ru.kidesoft.desktop.domain.entity.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    UNDEFINED(0, "undefined", "Не определено"),
    PRINT_TICKET(1, "print_ticket", "Печать билета"),
    PRINT_CHECK(2, "print_check", "Печать чека");

    private int id;
    private String name;
    private String description;

    public static OperationType fromId(int id) {
        for (OperationType type : OperationType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return UNDEFINED;
    }

    public static OperationType fromName(String name) {
        for (OperationType type : OperationType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return UNDEFINED;
    }
}
