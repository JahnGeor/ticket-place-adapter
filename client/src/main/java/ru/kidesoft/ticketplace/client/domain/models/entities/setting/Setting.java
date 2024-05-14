package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public class Setting {
    private Kkt kkt;
    private Printer printer;
    private Update update;
    private Server server;

    public Setting() {
        this.kkt = new Kkt();
        this.printer = new Printer();
        this.update = new Update();
        this.server = new Server();
    }

    public Kkt getKkt() {
        return kkt;
    }

    public void setKkt(Kkt kkt) {
        this.kkt = kkt;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
