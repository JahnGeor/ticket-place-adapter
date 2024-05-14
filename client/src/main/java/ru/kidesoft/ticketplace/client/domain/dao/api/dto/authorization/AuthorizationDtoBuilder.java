package ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization;

import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;

public final class AuthorizationDtoBuilder {
    private Profile profile;
    private Session session;

    private AuthorizationDtoBuilder() {
    }

    public static AuthorizationDtoBuilder anAuthorizationDto() {
        return new AuthorizationDtoBuilder();
    }

    public AuthorizationDtoBuilder withProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public AuthorizationDtoBuilder withSession(Session session) {
        this.session = session;
        return this;
    }

    public AuthorizationDto build() {
        AuthorizationDto authorizationDto = new AuthorizationDto();
        authorizationDto.setProfile(profile);
        authorizationDto.setSession(session);
        return authorizationDto;
    }
}
