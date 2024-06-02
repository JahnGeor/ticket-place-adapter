package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.AboutUiDto;

import java.net.URL;
import java.util.ResourceBundle;
@FxmlView("/fxml/about.fxml")
@org.springframework.stereotype.Controller
public class AboutController extends Controller<AboutUiDto> {

    @Autowired
    public AboutController(ConfigurableApplicationContext context) {
        super(context);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //logoImage.setImage();
        versionLabel.setText("Версия приложения: " + "2.0.0");
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
