package ru.kidesoft.desktop.domain.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum SourceType {
    UNDEFINED(0, "undefined", "Не определено"),
    ORDER(1, "order", "Заказ"),
    REFUND(2, "refund", "Возврат");

    private int id;
    private String name;
    private String description;

    public static SourceType getById(int id) {
        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.getId() == id) {
                return sourceType;
            }
        }
        return UNDEFINED;
    }

    public static SourceType getByName(String name) {
        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.getName().equals(name)) {
                return sourceType;
            }
        }
        return UNDEFINED;
    }

    public static SourceType getByDescription(String description) {
        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.getDescription().equals(description)) {
                return sourceType;
            }
        }
        return UNDEFINED;
    }
}
