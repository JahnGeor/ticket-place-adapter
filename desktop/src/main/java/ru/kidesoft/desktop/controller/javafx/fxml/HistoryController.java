package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.HistoryUiDto;

import java.net.URL;
import java.util.ResourceBundle;
@FxmlView("/fxml/history.fxml")
@org.springframework.stereotype.Controller
public class HistoryController extends Controller<HistoryUiDto> {
    @FXML
    private Label printCheckLabel;
    @FXML
    private Label printTicketLabel;

    @Autowired
    public HistoryController(ConfigurableApplicationContext context) {
        super(context);
    }

    @FXML
    private void printAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Label("", new CheckBox());

        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
    }

    @Override
    public void updateView(HistoryUiDto viewDto) {

    }
}
