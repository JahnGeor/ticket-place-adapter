package ru.kidesoft.desktop.domain.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PrintType {
    UNDEFINED(0, "undefined", "Не определено"),
    CHECK(1, "check", "Чек"),
    TICKET(2, "ticket", "Билет");

    private final int id;
    private final String name;
    private final String description;


    public static PrintType getById(int id) {
        for (PrintType printType : PrintType.values()) {
            if (printType.getId() == id) {
                return printType;
            }
        }
        return UNDEFINED;
    }


    public static PrintType getByName(String name) {
        for (PrintType printType : PrintType.values()) {
            if (printType.getName().equals(name)) {
                return printType;
            }
        }
        return UNDEFINED;
    }

}
