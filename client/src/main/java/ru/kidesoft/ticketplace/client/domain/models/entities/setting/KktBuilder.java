package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public final class KktBuilder {
    private Boolean kktAutoRecconnect;
    private String kktDriverPath;

    private KktBuilder() {
    }

    public static KktBuilder aKkt() {
        return new KktBuilder();
    }

    public KktBuilder withKktAutoRecconnect(Boolean kktAutoRecconnect) {
        this.kktAutoRecconnect = kktAutoRecconnect;
        return this;
    }

    public KktBuilder withKktDriverPath(String kktDriverPath) {
        this.kktDriverPath = kktDriverPath;
        return this;
    }

    public Kkt build() {
        Kkt kkt = new Kkt();
        kkt.setKktAutoRecconnect(kktAutoRecconnect);
        kkt.setKktDriverPath(kktDriverPath);
        return kkt;
    }
}
