package ru.kidesoft.desktop.controller.javafx.dto.auth;

import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;

import java.util.List;

@Data
@Builder

public class AuthUiDto {
    List<Cashier> cashier;
    List<Login> login;
}
