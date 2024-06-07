package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import ru.kidesoft.desktop.ApplicationConfiguration;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.AboutUiDto;


import java.net.URL;
import java.util.ResourceBundle;
@FxmlView("/fxml/about.fxml")
@org.springframework.stereotype.Controller
public class AboutController extends Controller<AboutUiDto> {

    ApplicationConfiguration applicationConfiguration;


    @Autowired
    public AboutController(ConfigurableApplicationContext context, ApplicationConfiguration applicationConfiguration) {
        super(context);
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            logoImage.setImage(applicationConfiguration.getLogo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        versionLabel.setText("Версия приложения: " + applicationConfiguration.getVersion());
        versionLabel.setStyle("-fx-text-fill: #6E62E5");
    }

    @FXML
    private ImageView logoImage;

    @FXML
    private Label versionLabel;

    @Override
    public void updateView(AboutUiDto viewDto) {

    }
}
