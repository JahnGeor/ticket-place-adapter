package ru.kidesoft.desktop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Setter
@Getter
@Configuration("AppConfiguration")
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
    private String version;
    private String defaultUrl;
}
