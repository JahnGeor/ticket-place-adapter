package ru.kidesoft.desktop.controller.javafx.fxml;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.StartSessionEvent;
import ru.kidesoft.desktop.controller.javafx.dto.MainUiDto;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.KktService;
import ru.kidesoft.desktop.domain.service.ProfileService;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/main.fxml")
public class MainController extends Controller<MainUiDto> {

    private final KktService kktService;
    private final ProfileService profileService;

    Service<Void> service = new Service<Void>() {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                @Override
                protected Void call() throws Exception {
                    while (true) {
                        if (isCancelled()) {
                            succeeded();
                        } else {
                            updateMessage(sdf.format(new Date()));
                        }
                    }
                }
            };
        }
    };


    @Autowired
    public MainController(ConfigurableApplicationContext context, KktService kktService, ProfileService profileService, ProfileService profileService1) {
        super(context);
        this.kktService = kktService;
        this.profileService = profileService1;
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
        printXReport.setGraphic(new FontIcon(MaterialDesignA.ALPHA_X_CIRCLE_OUTLINE));

        diagnosticButton.setTooltip(new Tooltip("Диагностика"));
        diagnosticButton.getStylesheets().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED, Styles.ACCENT);
        diagnosticButton.setGraphic(new FontIcon(MaterialDesignH.HELP_CIRCLE));

        card.setHeader(
                fullNameLabel
        );

        card.setBody(new VBox(
                innLabel,
                roleLabel,
                new Separator(),
                kktStateLabel,
                kktShiftStateLabel,
                new Separator(),
                listenLabel,
                lastListenedNumber

        ));

        timeLabel.textProperty().bind(service.messageProperty());

        if (!service.isRunning()) {
            service.start();
        }

        Platform.runLater(
                () -> {
                    try {
                        var profile = profileService.getProfile();
                        var shiftState = kktService.getShiftState();
                        var kktState = kktService.isConnectionOpened();
                        var viewDto = MainUiDto.builder()
                                .inn(profile.getInn().toString())
                                .fullName(profile.getFullname())
                                .shiftState(shiftState)
                                .roleType(profile.getRole())
                                .kktReady(kktState)
                                .build();

                        updateView(viewDto);
                    } catch (AppException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @FXML
    private Card card;

    private final Label fullNameLabel = new Label();
    private final Label innLabel = new Label();
    private final Label roleLabel = new Label();

    private final Label kktStateLabel = new Label();
    private final Label kktShiftStateLabel = new Label();
    private final Label listenLabel = new Label();
    private final Label lastListenedNumber = new Label();


    @FXML
    private Button incomeButton;

    @FXML private Button diagnosticButton;


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

    @FXML void diagnosticAction(ActionEvent event) throws Exception {
        //TODO: Модуль диагностики
        context.publishEvent(new StartSessionEvent(StartSessionEvent.StartSession.REFRESH));
    }

    @FXML
    void shiftAction(ActionEvent event) throws AppException {
        kktService.switchShift();
    }

    @FXML
    void printLastCheckAction(ActionEvent event) throws AppException {
        kktService.printLastCheck();
    }

    @FXML
    void printXReportAction(ActionEvent event) throws AppException {
        kktService.printXReport();
    }



    @Override
    public void updateView(MainUiDto viewDto) {
        fullNameLabel.setText(viewDto.getFullName());
        innLabel.setText("ИНН: " + viewDto.getInn());
        roleLabel.setText("Роль: " + viewDto.getRoleType().getDescription());

        kktStateLabel.setText("Состояние ККТ: " +  (viewDto.isKktReady() ? "Готова к работе" : "Не готова к работе"));
        kktShiftStateLabel.setText("Смена: " + viewDto.getShiftState().getDescription());

        listenLabel.setText("Прослушивание сервера: " + (viewDto.isListening() ? "Включено" : "Выключено"));
        lastListenedNumber.setText("Последний обработанный чек: " + viewDto.getLastCheckNumber());
    }
}
