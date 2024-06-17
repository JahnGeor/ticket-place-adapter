package ru.kidesoft.desktop.domain.entity.profile;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Cashier {
    @Column(name = "fullname", nullable = false)
    private String fullName;
    @Column(name = "inn", nullable = false)
    private Long inn;
}
