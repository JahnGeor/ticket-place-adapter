package ru.kidesoft.ticketplace.client.domain.presenter.dto;

import ru.kidesoft.ticketplace.client.domain.dao.database.dto.CashierDto;

import java.util.ArrayList;
import java.util.List;

public class AuthProfile {
    List<String> emails;

    List<String> urls;
    List<CashierDto> cashiers;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<CashierDto> getCashiers() {
        return cashiers;
    }

    public void setCashiers(List<CashierDto> cashiers) {
        this.cashiers = cashiers;
    }

    public AuthProfile() {
        this.emails = new ArrayList<>();
        this.urls = new ArrayList<>();
        this.cashiers = new ArrayList<>();
    }
}
