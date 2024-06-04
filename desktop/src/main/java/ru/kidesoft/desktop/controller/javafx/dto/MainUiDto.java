package ru.kidesoft.desktop.controller.javafx.dto;

import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.entity.profile.RoleType;

@Builder
@Data
public class MainUiDto {
    private State shiftState;
    private boolean kktReady;
    private boolean isListening;
    private Integer lastCheckNumber;
    private String fullName;
    private String inn;
    private RoleType roleType;

}
