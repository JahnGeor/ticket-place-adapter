package ru.kidesoft.desktop.controller.javafx.dto;

import lombok.Builder;
import lombok.Getter;
import ru.kidesoft.desktop.domain.entity.setting.PageOrientation;
import ru.kidesoft.desktop.domain.entity.setting.PageSize;

import java.time.Duration;

@Getter
@Builder
public class SettingUiDto {
    private String kktDriverPath;
    private boolean autoReconnect;


    private String printerName;
    private PageOrientation pageOrientation;
    private PageSize pageSize;
    private boolean printTicket;
    private boolean printCheck;

    private Duration timeout;
    private Duration period;

    private String updateRepo;
    private boolean updateAuto;
}
