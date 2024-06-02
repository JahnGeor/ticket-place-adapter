package ru.kidesoft.desktop.domain.entity.history;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum StatusType {

    UNDEFINED(0, "undefined", "Cтатус не определён"),
    SUCCESS(1, "success", "Успешно"),
    ERROR(2, "error", "Ошибка");

    private final int id;
    private final  String name;
    private final String description;


}
