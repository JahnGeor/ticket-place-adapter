package ru.kidesoft.desktop.domain.entity.profile;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Cashier {
    private String fullName;
    private Long inn;
}
