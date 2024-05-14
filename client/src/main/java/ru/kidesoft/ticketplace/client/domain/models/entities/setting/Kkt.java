package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public class Kkt {
    private Boolean kktAutoRecconnect;
    private String kktDriverPath;

    public Boolean getKktAutoRecconnect() {
        return kktAutoRecconnect;
    }

    public void setKktAutoRecconnect(Boolean kktAutoRecconnect) {
        this.kktAutoRecconnect = kktAutoRecconnect;
    }

    public String getKktDriverPath() {
        return kktDriverPath;
    }

    public void setKktDriverPath(String kktDriverPath) {
        this.kktDriverPath = kktDriverPath;
    }

    public Kkt() {
    }
}
