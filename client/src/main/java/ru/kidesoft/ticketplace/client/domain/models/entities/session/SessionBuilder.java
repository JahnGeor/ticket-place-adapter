package ru.kidesoft.ticketplace.client.domain.models.entities.session;

import java.time.ZonedDateTime;

public final class SessionBuilder {
    private ZonedDateTime createdAt;
    private ZonedDateTime expiredAt;
    private TokenData tokenData;

    private SessionBuilder() {
    }

    public static SessionBuilder aSession() {
        return new SessionBuilder();
    }

    public SessionBuilder withCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SessionBuilder withExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public SessionBuilder withTokenData(TokenData tokenData) {
        this.tokenData = tokenData;
        return this;
    }

    public Session build() {
        Session session = new Session();
        session.setCreatedAt(createdAt);
        session.setExpiredAt(expiredAt);
        session.setTokenData(tokenData);
        return session;
    }
}
