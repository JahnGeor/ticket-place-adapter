package ru.kidesoft.desktop.infrastructure.port.api.kkt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KktOperator {
    private String fullName;
    private Long inn;
}
