package ru.kidesoft.ticketplace.client.domain.models.entities.profile.enums;

public enum RoleType {
    UNDEFINED(0, "undefined", "Неопределно"),
    ADMIN(1, "admin", "Администратор");

    private Integer id;
    private String name;
    private String description;

    RoleType(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static RoleType getById(Integer id) {
        for (RoleType pt : RoleType.values()) {
            if (pt.getId().equals(id)) {
                return pt;
            }
        }

        return UNDEFINED;
    }

    public static RoleType getByName(String name) {
        for (RoleType pt : RoleType.values()) {
            if (pt.getName().equals(name)) {
                return pt;
            }
        }

        return UNDEFINED;
    }
}
