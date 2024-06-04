package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.javafx.FontIcon;
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
        printRefundFromCheckButton.setGraphic(new FontIcon(FluentUiRegularAL.ARROW_ENTER_24));
        printSellFromCheckButton.setGraphic(new FontIcon(FluentUiRegularAL.ARROW_ENTER_24));
    }

    @Override
    public void updateView(AdminUiDto viewDto) {

    }

    @FXML private TextField printRefundFromCheckLabel;

    @FXML private TextField printSellFromCheckLabel;

    @FXML private Button printSellFromCheckButton;

    @FXML private Button printRefundFromCheckButton;

    @FXML void printRefundFromCheckAction(ActionEvent event) {

    }

    @FXML void printSellFromCheckAction(ActionEvent event) {

    }
}
