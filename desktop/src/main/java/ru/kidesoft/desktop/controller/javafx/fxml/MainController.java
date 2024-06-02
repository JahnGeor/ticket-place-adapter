package ru.kidesoft.desktop.controller.javafx.fxml;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.MainUiDto;


import java.net.URL;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/main.fxml")
public class MainController extends Controller<MainUiDto> {

    @Autowired
    public MainController(ConfigurableApplicationContext context) {
        super(context);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //logoImage.setImage(StageSetting.logoImage);
        incomeButton.setGraphic(new FontIcon(FluentUiRegularMZ.PAYMENT_20));

        shiftButton.setTooltip(new Tooltip("Состояние смены"));

        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_CHECK));
//        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_OFF));
//        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_ALERT));
//        shiftButton.setGraphic(new FontIcon(MaterialDesignP.PRINTER_EYE));

        printLastCheck.setGraphic(new FontIcon(FluentUiRegularAL.DOCUMENT_ARROW_LEFT_16));
        printLastCheck.setTooltip(new Tooltip("Печать последнего чека"));

        printXReport.setTooltip(new Tooltip("Печать Х-отчёта"));
        printXReport.setGraphic(new FontIcon(MaterialDesignA.ALPHA_X_CIRCLE_OUTLINE

        ));
    }

    @FXML
    private Card card;

    private Label fullNameLabel;

    private Label innLabel;

    private Label roleLabel;

    @FXML
    private Button incomeButton;

    @FXML
    private TextField incomeField;

    @FXML
    private ImageView logoImage;

    @FXML
    private Button shiftButton;

    @FXML
    private Button printLastCheck;

    @FXML
    private Button printXReport;

    @FXML
    private Label timeLabel;

    @FXML
    void incomeAction(ActionEvent event) {

    }

    @FXML
    void shiftAction(ActionEvent event) {

    }

    @FXML
    void printLastCheckAction(ActionEvent event) {

    }

    @FXML
    void printXReportAction(ActionEvent event) {
    }

    @FXML
    void testAction(ActionEvent event) {

    }

    @Override
    public void updateView(MainUiDto viewDto) {

    }
}
