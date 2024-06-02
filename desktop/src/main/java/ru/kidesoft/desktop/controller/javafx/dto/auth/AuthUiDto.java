package ru.kidesoft.desktop.controller.javafx.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.List;

@Data
@Builder

public class AuthUiDto {
    List<CashierUiDto> cashier;
    List<Login> login;
}
