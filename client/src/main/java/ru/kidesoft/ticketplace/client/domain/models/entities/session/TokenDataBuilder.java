package ru.kidesoft.ticketplace.client.domain.models.entities.session;

public final class TokenDataBuilder {
    private String tokenType;
    private String accessToken;

    private TokenDataBuilder() {
    }

    public static TokenDataBuilder aTokenData() {
        return new TokenDataBuilder();
    }

    public TokenDataBuilder withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public TokenDataBuilder withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public TokenData build() {
        return new TokenData(tokenType, accessToken);
    }
}
