package ru.kidesoft.desktop.controller.javafx.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUiDto {
    CashierUiDto cashier;
}
