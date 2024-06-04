package ru.kidesoft.desktop.domain.entity;

import lombok.Getter;

@Getter
public enum State {
    UNDEFINED(0, "Undefined", "Неизвестное состояние"),
    OPENED(1, "opened", "Открыта"),
    CLOSED(2, "closed", "Закрыта"),
    EXPIRED(3, "expired", "Истекла");

    private int id;
    private String name;
    private String description;


    State(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static State fromId(int id) {
        for (State state : State.values()) {
            if (state.getId() == id) {
                return state;
            }
        }
        return State.UNDEFINED;
    }

    public static State fromName(String name) {
        for (State state : State.values()) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return State.UNDEFINED;
    }

}
