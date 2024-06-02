package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.SettingUiDto;
import ru.kidesoft.desktop.domain.entity.setting.PageOrientation;
import ru.kidesoft.desktop.domain.entity.setting.PageSize;


import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/setting.fxml")
public class SettingController extends Controller<SettingUiDto> {

    @Autowired
    public SettingController(ConfigurableApplicationContext context) {
        super(context);
    }

    public void setUserData(SettingUiDto setting) {
        KktDriverPathField.setText(setting.getKktDriverPath());
        repoPathField.setText(setting.getUpdateRepo());

        printerNameField.getItems().addAll(
                Printer.getAllPrinters().stream().map(Printer::getName).toList()
        );

        printerNameField.setValue(setting.getPrinterName());

        pageOrientationBox.setCellFactory(
                param -> new ListCell<>() {
                    @Override
                    protected void updateItem(PageOrientation item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getDescription());
                        }
                    }
                }
        );

        pageSizeBox.setCellFactory(
                param -> new ListCell<>() {
                    @Override
                    protected void updateItem(PageSize item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                }
        );

        pageOrientationBox.getItems().addAll(PageOrientation.values());

        pageSizeBox.getItems().addAll(PageSize.values());

        pageSizeBox.getSelectionModel().select(setting.getPageSize());

        pageOrientationBox.getSelectionModel().select(setting.getPageOrientation());

        ((CheckBox) printTicketLabel.getGraphic()).setSelected(setting.isPrintTicket());

        ((CheckBox) printCheckLabel.getGraphic()).setSelected(setting.isPrintCheck());

        ((CheckBox) autoReconnectLabel.getGraphic()).setSelected(setting.isAutoReconnect());

        timeoutBox.getItems().addAll(
                Duration.ofSeconds(1), Duration.ofSeconds(5),
                Duration.ofSeconds(10), Duration.ofSeconds(30),
                Duration.ofSeconds(60), Duration.ofSeconds(120)
        );

        timeoutBox.getSelectionModel().select(setting.getTimeout());

        periodBox.getItems().addAll(
                Duration.ofSeconds(1), Duration.ofSeconds(5),
                Duration.ofSeconds(10), Duration.ofSeconds(30),
                Duration.ofSeconds(60), Duration.ofSeconds(120)
        );

        periodBox.getSelectionModel().select(setting.getPeriod());

        var durationConverter = new StringConverter<Duration>() {

            @Override
            public String toString(Duration duration) {
                return duration.getSeconds() + " сек";
            }

            @Override
            public Duration fromString(String s) {
                return null;
            }
        };
        var durationCallback = new Callback<ListView<Duration>, ListCell<Duration>>() {
            @Override
            public ListCell<Duration> call(ListView<Duration> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Duration item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getSeconds() + " сек");
                        }
                    }
                };
            }
        };

        timeoutBox.setCellFactory(durationCallback);
        periodBox.setCellFactory(durationCallback);
        timeoutBox.setConverter(durationConverter);
        periodBox.setConverter(durationConverter);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: получаем настройки из базы данных

        // TODO: Используем метод setFxmlDto() для установки данных на форму

        autoUpdateLabelBox.setGraphic(new CheckBox());
        KktDriverPathButton.setGraphic(new FontIcon(MaterialDesignF.FOLDER_OPEN));
        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
        autoReconnectLabel.setGraphic(new CheckBox());
    }

    @FXML
    private Label autoReconnectLabel;

    @FXML
    private Button KktDriverPathButton;

    @FXML
    private TextField KktDriverPathField;

    @FXML
    private Label KktPathLabel;

    @FXML
    private Label autoUpdateLabelBox;

    @FXML
    private ComboBox<PageOrientation> pageOrientationBox;

    @FXML
    private ComboBox<PageSize> pageSizeBox;

    @FXML
    private ComboBox<Duration> periodBox;

    @FXML
    private Label printCheckLabel;

    @FXML
    private Label printTicketLabel;

    @FXML
    private ComboBox<String> printerNameField;

    @FXML
    private TextField repoPathField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<Duration> timeoutBox;

    @FXML
    void KktDriverPathExplorer(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {
//
//        var kkt = KktBuilder.aKkt().
//                withKktDriverPath(KktDriverPathField.getText()).
//                withKktAutoRecconnect(((CheckBox) autoReconnectLabel.getGraphic()).isSelected()).build();
//
//        var server = ServerBuilder.aServer().withInterval(periodBox.getSelectionModel().getSelectedItem())
//                .withTimeout(timeoutBox.getSelectionModel().getSelectedItem()).build();
//
//        var printer = PrinterBuilder.aPrinter()
//                .withPageSize(pageSizeBox.getSelectionModel().getSelectedItem())
//                .withPageOrientation(pageOrientationBox.getSelectionModel().getSelectedItem())
//                .withPrintTicket(((CheckBox) printTicketLabel.getGraphic()).isSelected())
//                .withName(printerNameField.getSelectionModel().getSelectedItem())
//                .withPrintCheck(((CheckBox) printCheckLabel.getGraphic()).isSelected()).build();
//
//        var update = UpdateBuilder.anUpdate().withAuto(((CheckBox) autoUpdateLabelBox.getGraphic()).isSelected()
//                ).withRepository(repoPathField.getText()).build();
//
//        var setting = SettingBuilder.aSetting()
//                .withKkt(kkt)
//                .withPrinter(printer)
//                .withServer(server)
//                .withUpdate(update).build();
//
//        Executor.builder().load().execute(
//                Interactor.getSettingUsecase()::saveSetting, setting
//        ).ifPresent(
//                value ->  Executor.builder().load().execute(Interactor::openScene, ControllerType.MAIN)
//        );
    }

    @Override
    public void updateView(SettingUiDto viewDto) {

    }
}
