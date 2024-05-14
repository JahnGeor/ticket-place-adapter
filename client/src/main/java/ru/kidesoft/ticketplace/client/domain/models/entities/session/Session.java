package ru.kidesoft.ticketplace.client.domain.models.entities.session;

import java.time.ZonedDateTime;

public class Session {
    private ZonedDateTime createdAt;
    private ZonedDateTime expiredAt;
    private TokenData tokenData;

    public Session() {
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public TokenData getTokenData() {
        return tokenData;
    }

    public void setTokenData(TokenData tokenData) {
        this.tokenData = tokenData;
    }
}
