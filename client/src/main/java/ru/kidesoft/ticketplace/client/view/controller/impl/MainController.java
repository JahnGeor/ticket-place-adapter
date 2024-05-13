package ru.kidesoft.ticketplace.client.view.controller.impl;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import ru.kidesoft.ticketplace.client.domain.executor.Executor;
import ru.kidesoft.ticketplace.client.domain.executor.handler.impl.DefaultHandler;
import ru.kidesoft.ticketplace.client.domain.interactor.impl.PrinterUsecase;
import ru.kidesoft.ticketplace.client.main.StageSetting;
import ru.kidesoft.ticketplace.client.view.controller.Controller;
import ru.kidesoft.ticketplace.client.view.controller.ControllerType;
import ru.kidesoft.ticketplace.client.view.controller.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller {
    public MainController(Stage stage) {
        super(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var tile = new Tile();
        var tile2 = new Tile();

        VBox vBox = new VBox();


        var textFullName = new Text("Иванов Иван Иванович");

        textFullName.getStyleClass().addAll(Styles.TITLE_4, Styles.TEXT_LIGHTER);

        var textRole = new Text("Администратор");

        textRole.getStyleClass().addAll(Styles.TEXT_SUBTLE);
        var textInn = new Text("ИНН: 2134567890");
        textInn.getStyleClass().addAll(Styles.TEXT_SUBTLE);

        vBox.getChildren().addAll(textFullName, textRole, textInn);

        card.setHeader(vBox);


        logoImage.setImage(StageSetting.logoImage);
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

    @Override
    public <T> void update(T t) {

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
        Executor.builder().withPresenter(this).load().execute(PrinterUsecase::printXReport);
    }

    @FXML
    void testAction(ActionEvent event) {

    }
}
