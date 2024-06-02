package ru.kidesoft.desktop.domain.entity.constant;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Constant {
    @Id
    @Lob
    @Column(name = "NAME", nullable = false, unique = true)
    @Enumerated (EnumType.STRING)
    private ConstantEnums name;

    @Lob
    @Column(name = "VAL")
    private String val;
}
