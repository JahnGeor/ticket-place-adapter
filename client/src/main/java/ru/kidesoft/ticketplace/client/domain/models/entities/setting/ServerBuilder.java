package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

import java.time.Duration;

public final class ServerBuilder {
    private Duration timeout;
    private Duration interval;

    private ServerBuilder() {
    }

    public static ServerBuilder aServer() {
        return new ServerBuilder();
    }

    public ServerBuilder withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public ServerBuilder withInterval(Duration interval) {
        this.interval = interval;
        return this;
    }

    public Server build() {
        Server server = new Server();
        server.setTimeout(timeout);
        server.setInterval(interval);
        return server;
    }
}
