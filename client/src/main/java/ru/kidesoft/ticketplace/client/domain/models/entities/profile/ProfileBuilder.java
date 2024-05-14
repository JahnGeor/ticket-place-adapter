package ru.kidesoft.ticketplace.client.domain.models.entities.profile;

import ru.kidesoft.ticketplace.client.domain.models.entities.profile.enums.RoleType;

public final class ProfileBuilder {
    private String fullName;
    private String userName;
    private Long inn;
    private RoleType role;
    private String avatar;
    private Integer userId;

    private ProfileBuilder() {
    }

    public static ProfileBuilder aProfile() {
        return new ProfileBuilder();
    }

    public ProfileBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProfileBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public ProfileBuilder withInn(Long inn) {
        this.inn = inn;
        return this;
    }

    public ProfileBuilder withRole(RoleType role) {
        this.role = role;
        return this;
    }

    public ProfileBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public ProfileBuilder withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Profile build() {
        Profile profile = new Profile();
        profile.setFullName(fullName);
        profile.setUserName(userName);
        profile.setInn(inn);
        profile.setRole(role);
        profile.setAvatar(avatar);
        profile.setUserId(userId);
        return profile;
    }
}
