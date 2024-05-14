package ru.kidesoft.ticketplace.client.domain.dao.database.dto;

public class CashierDto {

    public CashierDto(String fullName, Long inn) {
        this.fullName = fullName;
        this.inn = inn;
    }

    private String fullName;
    private Long inn;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getInn() {
        return inn;
    }

    public void setInn(Long inn) {
        this.inn = inn;
    }
}
