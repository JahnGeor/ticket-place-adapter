package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.AdminUiDto;

import java.net.URL;
import java.util.ResourceBundle;
@org.springframework.stereotype.Controller
@FxmlView("/fxml/admin.fxml")
public class AdminController extends Controller<AdminUiDto> {
    @Autowired
    public AdminController(ConfigurableApplicationContext context) {
        super(context);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void updateView(AdminUiDto viewDto) {

    }
}
