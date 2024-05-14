package ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums;

public enum PageOrientation {
    UNDEFINED(0, "undefined", "Неопределено"),
    PORTRAIT(1, "portrait", "Портретная"),
    LANDSCAPE(2, "landscape", "Альбомная");

    private Integer id;
    private String name;
    private String description;

    PageOrientation(Integer id, String name, String description) {
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
    public static PageOrientation getById(Integer id) {
        for (PageOrientation pt : PageOrientation.values()) {
            if (pt.getId().equals(id)) {
                return pt;
            }
        }

        return UNDEFINED;
    }

    public static PageOrientation getByName(String name) {
        for (PageOrientation pt : PageOrientation.values()) {
            if (pt.getName().equals(name)) {
                return pt;
            }
        }

        return UNDEFINED;
    }

    @Override
    public String toString() {
        return description;
    }
}
