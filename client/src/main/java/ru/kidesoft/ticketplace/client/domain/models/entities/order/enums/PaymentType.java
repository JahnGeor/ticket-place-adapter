package ru.kidesoft.ticketplace.client.domain.models.entities.order.enums;

import java.util.Objects;

public enum PaymentType {
    UNDEFINED(0, "undefined", "Неопределено"),
    CASH(1, "cash", "Наличные"),
    CARD(2, "card", "Безналичный расчет (карта)"),
    ONLINE(3, "online", "Безналичный расчет (онлайн)"),
    INVITED(4, "invited", "Приглашенный"),
    CHECKING_ACCOUNT(5, "checking_account", "Расчетный счет (юр. лицо)"),
    ACCOUNT_INDIVIDUAL(6, "account_individual", "Расчетный счет (физ. лицо)");

    private final Integer id;
    private final String name;
    private final String description;

    PaymentType(Integer id, String name, String description) {
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

    public static PaymentType getById(Integer id) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (Objects.equals(paymentType.getId(), id)) {
                return paymentType;
            }
        }

        return UNDEFINED;
    }

    public static PaymentType getByName(String name) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (Objects.equals(paymentType.getName(), name)) {
                return paymentType;
            }
        }

        return UNDEFINED;
    }
}