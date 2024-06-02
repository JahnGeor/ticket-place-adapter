package ru.kidesoft.desktop.controller.javafx.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AboutUiDto {
    private String version;
    private String description;
}
