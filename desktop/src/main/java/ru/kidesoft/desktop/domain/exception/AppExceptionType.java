package ru.kidesoft.desktop.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum AppExceptionType {
    UNDEFINED(0, "undefined", "Не определено"),
    ApiExceptionType(1, "apiExceptionType", "Ошибка связи с удаленным сервером"),
    DbExceptionType(2, "dbExceptionType", "Ошибка работы с базой данных"),
    KktExceptionType(3, "kktExceptionType", "Ошибка работы ККТ"),;

    private int id;
    private String name;
    private String description;


    public static AppExceptionType getById(int id) {
        for (AppExceptionType type : AppExceptionType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }


    public static AppExceptionType getByName(String name) {
        for (AppExceptionType type : AppExceptionType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

}
