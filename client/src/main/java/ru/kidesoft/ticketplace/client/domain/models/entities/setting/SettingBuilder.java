package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public final class SettingBuilder {
    private Kkt kkt;
    private Printer printer;
    private Update update;
    private Server server;

    private SettingBuilder() {
    }

    public static SettingBuilder aSetting() {
        return new SettingBuilder();
    }

    public SettingBuilder withKkt(Kkt kkt) {
        this.kkt = kkt;
        return this;
    }

    public SettingBuilder withPrinter(Printer printer) {
        this.printer = printer;
        return this;
    }

    public SettingBuilder withUpdate(Update update) {
        this.update = update;
        return this;
    }

    public SettingBuilder withServer(Server server) {
        this.server = server;
        return this;
    }

    public Setting build() {
        Setting setting = new Setting();
        setting.setKkt(kkt);
        setting.setPrinter(printer);
        setting.setUpdate(update);
        setting.setServer(server);
        return setting;
    }
}
