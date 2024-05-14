package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public class Update {
    private String repository;
    private Boolean auto;

    public Update() {
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }
}
