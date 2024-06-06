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
    @Value("${app.application.default-url}")
    private String DefaultUrl;
    @Value("${app.application.version}")
    private String Version;
    @Value("${spring.application.title}")
    private String Title;

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
