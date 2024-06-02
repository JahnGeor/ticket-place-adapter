package ru.kidesoft.desktop.domain.entity.constant;

public enum ConstantEnums {
    UNDEFINED("UNDEFINED"),
    ACTIVE_USER_ID("ACTIVE_USER_ID"),
    DEFAULT_URL("DEFAULT_URL");

    private final String name;

    ConstantEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ConstantEnums getByName(String name) {
        for (ConstantEnums constant : values()) {
            if (constant.getName().equals(name)) {
                return constant;
            }
        }

        return UNDEFINED;
    }
}
