package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.StageManager;
import ru.kidesoft.desktop.controller.javafx.dto.SettingUiDto;
import ru.kidesoft.desktop.domain.dao.database.SettingRepository;
import ru.kidesoft.desktop.domain.entity.setting.PageOrientation;
import ru.kidesoft.desktop.domain.entity.setting.PageSize;
import ru.kidesoft.desktop.domain.entity.setting.Setting;
import ru.kidesoft.desktop.domain.service.SettingService;


import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/setting.fxml")
public class SettingController extends Controller<SettingUiDto> {

    SettingService settingService;

    @Autowired
    public SettingController(ConfigurableApplicationContext context, SettingService settingService,
                             SettingRepository settingRepository) {
        super(context);
        this.settingService = settingService;
        this.settingRepository = settingRepository;
    }

    public void setUserData(SettingUiDto setting) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                            setText(item.getDescription());
                        }
                    }
                }
        );

        StringConverter<PageSize> pageSizeConverter = new StringConverter<PageSize>() {

            @Override
            public String toString(PageSize pageSize) {
                return pageSize.getDescription();
            }

            @Override
            public PageSize fromString(String s) {
                return PageSize.fromDescription(s);
            }
        };


        pageSizeBox.setConverter(pageSizeConverter);


        StringConverter<PageOrientation> pageOrientationConverter = new StringConverter<PageOrientation>() {

            @Override
            public String toString(PageOrientation pageOrientation) {
                return pageOrientation.getDescription();
            }


            @Override
            public PageOrientation fromString(String s) {
                return PageOrientation.fromDescription(s);
            }
        };


        pageOrientationBox.setConverter(pageOrientationConverter);

        autoUpdateLabelBox.setGraphic(new CheckBox());
        KktDriverPathButton.setGraphic(new FontIcon(MaterialDesignF.FOLDER_OPEN));
        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());
        autoReconnectLabel.setGraphic(new CheckBox());

        //TODO: получаем настройки из базы данных
        Setting setting = settingService.getSetting();

        var settingDto = SettingUiDto.builder()
                .kktDriverPath(setting.getKktDriverPath())
                .autoReconnect(setting.getKktAutoReconnect())
                .pageOrientation(setting.getPageOrientation())
                .pageSize(setting.getPageSize())
                .period(setting.getServerRequestInterval())
                .timeout(setting.getServerRequestTimeout())
                .printCheck(setting.getPrintCheck())
                .printTicket(setting.getPrintTicket())
                .printerName(setting.getPrinterName())
                .updateAuto(setting.getUpdateAutomatically())
                .updateRepo(setting.getUpdateRepositoryUrl())
                .build();

        // TODO: Используем метод setFxmlDto() для установки данных на форму



        updateView(settingDto);
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
    private final SettingRepository settingRepository;

    @FXML
    void KktDriverPathExplorer(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        var selectedDirectory = directoryChooser.showDialog(KktDriverPathButton.getScene().getWindow());

        if (selectedDirectory != null) {
            KktDriverPathField.setText(selectedDirectory.toString());
        }
    }

    @FXML
    void saveAction(ActionEvent event) {

        settingService.save(Setting.builder().kktDriverPath(KktDriverPathField.getText()).
                kktAutoReconnect(((CheckBox) autoReconnectLabel.getGraphic()).isSelected()).
                pageOrientation(pageOrientationBox.getSelectionModel().getSelectedItem()).
                pageSize(pageSizeBox.getSelectionModel().getSelectedItem()).
                serverRequestInterval(periodBox.getSelectionModel().getSelectedItem()).
                serverRequestTimeout(timeoutBox.getSelectionModel().getSelectedItem()).
                printCheck(((CheckBox) printCheckLabel.getGraphic()).isSelected()).
                printTicket(((CheckBox) printTicketLabel.getGraphic()).isSelected()).
                printerName(printerNameField.getSelectionModel().getSelectedItem()).
                updateAutomatically(((CheckBox) autoUpdateLabelBox.getGraphic()).isSelected()).
                updateRepositoryUrl(repoPathField.getText()).build());

        context.getBean(StageManager.class).show(MainController.class);
    }

    @Override
    public void updateView(SettingUiDto viewDto) {
        KktDriverPathField.setText(viewDto.getKktDriverPath());
        repoPathField.setText(viewDto.getUpdateRepo());

        printerNameField.getItems().addAll(
                Printer.getAllPrinters().stream().map(Printer::getName).toList()
        );

        printerNameField.setValue(viewDto.getPrinterName());

        pageOrientationBox.getItems().addAll(PageOrientation.values());

        pageSizeBox.getItems().addAll(PageSize.values());

        pageSizeBox.getSelectionModel().select(viewDto.getPageSize());

        pageOrientationBox.getSelectionModel().select(viewDto.getPageOrientation());

        ((CheckBox) printTicketLabel.getGraphic()).setSelected(viewDto.isPrintTicket());

        ((CheckBox) printCheckLabel.getGraphic()).setSelected(viewDto.isPrintCheck());

        ((CheckBox) autoReconnectLabel.getGraphic()).setSelected(viewDto.isAutoReconnect());

        timeoutBox.getItems().addAll(
                Duration.ofSeconds(1), Duration.ofSeconds(5),
                Duration.ofSeconds(10), Duration.ofSeconds(30),
                Duration.ofSeconds(60), Duration.ofSeconds(120)
        );

        timeoutBox.getSelectionModel().select(viewDto.getTimeout());

        periodBox.getItems().addAll(
                Duration.ofSeconds(1), Duration.ofSeconds(5),
                Duration.ofSeconds(10), Duration.ofSeconds(30),
                Duration.ofSeconds(60), Duration.ofSeconds(120)
        );

        periodBox.getSelectionModel().select(viewDto.getPeriod());


    }
}
