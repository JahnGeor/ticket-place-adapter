package ru.kidesoft.ticketplace.client.domain.dao.database.dto;

public class LoginProtected {
    public LoginProtected(String email, String url) {
        this.email = email;
        this.url = url;
    }

    private String email;
    private String url;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
