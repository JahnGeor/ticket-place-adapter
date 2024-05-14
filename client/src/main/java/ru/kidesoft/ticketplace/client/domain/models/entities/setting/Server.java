package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

import java.time.Duration;

public class Server {
    private Duration timeout;
    private Duration interval;

    public Server() {
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }
}
