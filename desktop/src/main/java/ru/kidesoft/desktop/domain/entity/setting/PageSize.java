package ru.kidesoft.desktop.domain.entity.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum PageSize {
    UNDEFINED(0, "undefined", "Не определено"),
    A4(1, "a4", "A4"),
    A5(2, "a5", "A5");

    private final int id;
    private final String name;
    private final String description;

    public static PageSize fromId(int id) {
        for (PageSize pageSize : values()) {
            if (pageSize.getId() == id) {
                return pageSize;
            }
        }
        return UNDEFINED;
    }

    public static PageSize fromName(String name) {
        for (PageSize pageSize : values()) {
            if (pageSize.getName().equals(name)) {
                return pageSize;
            }
        }
        return UNDEFINED;
    }

    public static PageSize fromDescription(String description) {
        for (PageSize pageSize : values()) {
            if (pageSize.getDescription().equals(description)) {
                return pageSize;
            }
        }
        return UNDEFINED;
    }
}
