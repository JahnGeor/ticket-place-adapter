package ru.kidesoft.ticketplace.client.domain.models.entities.login;

public final class LoginBuilder {
    private String email;
    private String password;
    private String url;

    private LoginBuilder() {
    }

    public static LoginBuilder aLogin() {
        return new LoginBuilder();
    }

    public LoginBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public Login build() {
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);
        login.setUrl(url);
        return login;
    }
}
