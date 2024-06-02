package ru.kidesoft.desktop.domain.entity.setting;


import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum PageOrientation {

    PORTRAIT(0, "portrait", "Портретная"),
    LANDSCAPE(1, "landscape", "Альбомная");

    private final int id;
    private final String name;
    private final String description;


    public static PageOrientation fromId(int id) {
        for (PageOrientation pageOrientation : values()) {
            if (pageOrientation.getId() == id) {
                return pageOrientation;
            }
        }
        return PORTRAIT;
    }


    public static PageOrientation fromName(String name) {
        for (PageOrientation pageOrientation : values()) {
            if (pageOrientation.getName().equals(name)) {
                return pageOrientation;
            }
        }
        return PORTRAIT;
    }
}
