package ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums;

public enum PageSize {
    UNDEFINED(0, "Неопределено"), A4(1, "A4"), A5(2, "A5");
    private Integer id;
    private String name;

    PageSize(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static PageSize getById(Integer id) {
        for (PageSize pt : PageSize.values()) {
            if (pt.getId().equals(id)) {
                return pt;
            }
        }
        return UNDEFINED;
    }

    @Override
    public String toString() {
        return name;
    }
}