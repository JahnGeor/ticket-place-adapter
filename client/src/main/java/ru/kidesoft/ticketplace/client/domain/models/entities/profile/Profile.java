package ru.kidesoft.ticketplace.client.domain.models.entities.profile;

import ru.kidesoft.ticketplace.client.domain.models.entities.profile.enums.RoleType;

public class Profile {
    private String fullName;
    private String userName;
    private Long inn;
    private RoleType role;
    private String avatar;
    private Integer userId;

    public Profile() {
    }

    public Profile(String fullName, String userName, Long inn, RoleType role, String avatar, Integer userId) {
        this.fullName = fullName;
        this.userName = userName;
        this.inn = inn;
        this.role = role;
        this.avatar = avatar;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getInn() {
        return inn;
    }

    public void setInn(Long inn) {
        this.inn = inn;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
