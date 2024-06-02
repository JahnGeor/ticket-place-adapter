package ru.kidesoft.desktop.domain.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    UNDEFINED(0, "undefined", "Неопределено"),
    CASH(1, "cash", "Наличные"),
    CARD(2, "card", "Безналичный расчет (карта)"),
    ONLINE(3, "online", "Безналичный расчет (онлайн)"),
    INVITED(4, "invited", "Приглашенный"),
    CHECKING_ACCOUNT(5, "checking_account", "Расчетный счет (юр. лицо)"),
    ACCOUNT_INDIVIDUAL(6, "account_individual", "Расчетный счет (физ. лицо)");

    private final int id;
    private final String name;
    private final String description;


    public static PaymentType getById(int id) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.getId() == id) {
                return paymentType;
            }
        }
        return UNDEFINED;
    }


    public static PaymentType getByName(String name) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.getName().equals(name)) {
                return paymentType;
            }
        }
        return UNDEFINED;
    }

}
