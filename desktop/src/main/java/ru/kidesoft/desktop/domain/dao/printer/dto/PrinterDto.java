package ru.kidesoft.desktop.domain.dao.printer.dto;

import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.setting.PageOrientation;
import ru.kidesoft.desktop.domain.entity.setting.PageSize;

@Builder
@Data
public class PrinterDto {
    private String name;
    private PageSize pageSize;
    private PageOrientation pageOrientation;
}
