package ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization;

import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;

public class AuthorizationDto {
    public Profile profile;
    public Session session;



    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
