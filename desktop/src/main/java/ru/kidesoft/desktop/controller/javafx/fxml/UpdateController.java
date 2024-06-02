package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.UpdateUiDto;

import java.net.URL;
import java.util.ResourceBundle;


@org.springframework.stereotype.Controller
@FxmlView("/fxml/update.fxml")
public class UpdateController extends Controller<UpdateUiDto> {

    @Autowired
    public UpdateController(ConfigurableApplicationContext context) {
        super(context);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void updateView(UpdateUiDto viewDto) {

    }
}
