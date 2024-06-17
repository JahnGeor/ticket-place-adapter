package ru.kidesoft.desktop;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Getter
@Setter
public class ApplicationConfiguration {
    @Value("${spring.application.default-url}")
    private String DefaultUrl;
    @Value("${spring.application.version}")
    private String Version;
    @Value("${spring.application.name}")
    private String Title;
    @Value("${spring.application.password}")
    private String Password;

    private Image logo;
    private Image icon;

    ApplicationConfiguration(@Value("${spring.application.icon}") Resource icon,
                             @Value("${spring.application.logo}") Resource logo) {
        try {
            this.logo = new Image(logo.getURL().toExternalForm());
            this.icon = new Image(icon.getURL().toExternalForm());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
