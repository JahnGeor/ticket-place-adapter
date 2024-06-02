package ru.kidesoft.desktop.domain.dao;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class ApiSetting {

    private String url;
    private String token;
    private String tokenType;
    private Duration serverRequestTimeout;

}
