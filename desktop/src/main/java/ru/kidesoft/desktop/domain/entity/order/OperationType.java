package ru.kidesoft.desktop.domain.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationType {
    UNDEFINED(0, "undefined", "Не определено"), ORDER(1, "order", "Заказ"), REFUND(2, "refund", "Возврат");

    private final int id;
    private final String name;
    private final String description;

    public static OperationType getById(int id) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.getId() == id) {
                return operationType;
            }
        }
        return UNDEFINED;
    }

    public static OperationType getByName(String name) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.getName().equals(name)) {
                return operationType;
            }
        }
        return UNDEFINED;
    }
}
