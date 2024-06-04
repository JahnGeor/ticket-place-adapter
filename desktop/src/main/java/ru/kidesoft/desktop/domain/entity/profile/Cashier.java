package ru.kidesoft.desktop.domain.entity.profile;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Cashier {
    @Column(name = "FULLNAME", nullable = false)
    private String fullName;
    @Column(name = "INN", nullable = false)
    private Long inn;
}
