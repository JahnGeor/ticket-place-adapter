package ru.kidesoft.desktop.controller.javafx.dto.auth;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CashierUiDto {
    private String fullName;
    private Long inn;
}
