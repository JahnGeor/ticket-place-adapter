package ru.kidesoft.desktop.domain.dao.kkt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KktOperator {
    private String fullName;
    private Long inn;

}
