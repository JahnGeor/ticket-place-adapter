package ru.kidesoft.desktop.controller.javafx.fxml.main;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignH;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.handler.HandlerManager;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.MainUiDto;
import ru.kidesoft.desktop.controller.javafx.events.StartSessionEvent;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.KktException;
import ru.kidesoft.desktop.domain.service.KktService;
import ru.kidesoft.desktop.domain.service.entities.ProfileService;


import java.net.URL;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
@FxmlView("/fxml/main.fxml")
public class MainView extends Controller<MainUiDto> {

    private final HandlerManager handlerManager;
    private final KktService kktService;
    private final ProfileService profileService;
    private TimeService timeService;

    @FXML
    public Button incomeButton;
    @FXML
    public Button diagnosticButton;
    @FXML
    public TextField incomeField;
    @FXML
    public ImageView logoImage;
    @FXML
    public Button shiftButton;
    @FXML
    public Button printLastCheck;
    @FXML
    public Button printXReport;
    @FXML
    public Label timeLabel;
    @FXML
    public Card card;

    public final Button refreshButton = new Button();

    public final Label fullNameLabel = new Label();
    public final Label innLabel = new Label();
    public final Label roleLabel = new Label();

    public final Label kktStateLabel = new Label();
    public final Label kktShiftStateLabel = new Label();
    public final Label listenLabel = new Label();
    public final Label lastListenedNumber = new Label();

    @Autowired
    public MainView(ConfigurableApplicationContext context, HandlerManager handlerManager, KktService kktService, ProfileService profileService) {
        super(context);
        timeService = new TimeService();
        this.handlerManager = handlerManager;
        this.kktService = kktService;
        this.profileService = profileService;
    }

    @SneakyThrows
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


        refreshButton.setGraphic(new FontIcon(MaterialDesignR.REFRESH_CIRCLE));
        refreshButton.getStylesheets().add(Styles.BUTTON_ICON);


        var title
                = new HBox();

        title.setAlignment(Pos.CENTER);

        var titleBox = new VBox();
        titleBox.getChildren().add(fullNameLabel);
        titleBox.getChildren().add(innLabel);

        title.getChildren().add(titleBox);
        title.getChildren().add(new Spacer());
        title.getChildren().add(refreshButton);

        card.setHeader(
                title
        );

        card.setBody(new VBox(
                new Separator(),
                kktStateLabel,
                kktShiftStateLabel,
                new Separator(),
                listenLabel,
                lastListenedNumber

        ));

        timeLabel.textProperty().bind(timeService.messageProperty());

        if (!timeService.isRunning()) {
            timeService.start();
        }

        incomeButton.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onIncomeCashClick));
        shiftButton.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onSwitchShiftClick));
        printLastCheck.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onPrintLastClick));
        printXReport.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onPrintXClick));
        diagnosticButton.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onDiagnosticClick));
        refreshButton.setOnAction(eventAction -> handlerManager.handle(eventAction, this::onRefreshClick));

        var initData = initData();

        if (initData != null) {
            updateView(initData);
        }
    }

    @Override
    public void updateView(MainUiDto viewDto) {
        if (viewDto == null) {
            throw new NullPointerException("viewDto is null");
        }


        if (viewDto.getIsListening() != null) {
            kktStateLabel.setText("Состояние ККТ: " + (viewDto.getIsListening() ? "Готова к работе" : "Не готова к работе"));
        }

        if (viewDto.getLastCheckNumber() != null) {
            lastListenedNumber.setText("Последний обработанный чек: " + viewDto.getLastCheckNumber());
        }

        if (viewDto.getFullName() != null) {
            fullNameLabel.setText(viewDto.getFullName());
        }


        if (viewDto.getInn() != null) {
            innLabel.setText("ИНН: " + viewDto.getInn());
        }


        if (viewDto.getRoleType() != null) {
            roleLabel.setText("Роль: " + viewDto.getRoleType().getDescription());
        }


        if (viewDto.getKktReady() != null) {
            kktStateLabel.setText("Состояние ККТ: " + (viewDto.getKktReady() ? "Готова к работе" : "Не готова к работе"));
        }


        if (viewDto.getIsListening() != null) {
            listenLabel.setText("Прослушивание сервера: " + (viewDto.getIsListening() ? "Включено" : "Выключено"));
        }


        if (viewDto.getLastCheckNumber() != null) {
            lastListenedNumber.setText("Последний обработанный чек: " + viewDto.getLastCheckNumber());
        }

        if (viewDto.getShiftState() != null) {
            kktShiftStateLabel.setText("Смена: " + viewDto.getShiftState().getDescription());
        }
    }

    private MainUiDto getMainData() {
        var mainUiDtoBuilder = MainUiDto.builder();

        try {
            var profile = profileService.getCurrentProfile();
            mainUiDtoBuilder.inn(profile.getInn().toString()).roleType(profile.getRole()).fullName(profile.getFullname());

            var kktReady = kktService.isConnectionOpened();

            var shiftState = kktService.getShiftState();
            mainUiDtoBuilder.shiftState(shiftState);

            mainUiDtoBuilder.kktReady(kktReady);
        } catch (Exception ignored) {
        }
        return mainUiDtoBuilder.build();
    }

    private MainUiDto initData() throws AppException {
        var mainUiDtoBuilder = MainUiDto.builder();

        var profile = profileService.getCurrentProfile();
        mainUiDtoBuilder.inn(profile.getInn().toString()).roleType(profile.getRole()).fullName(profile.getFullname());

        try {
            var kktReady = kktService.isConnectionOpened();
            mainUiDtoBuilder.kktReady(kktReady);
        } catch (Exception e) {
            mainUiDtoBuilder.kktReady(false);
        }

        try {
            var shiftState = kktService.getShiftState();
            mainUiDtoBuilder.shiftState(shiftState);
        } catch (Exception e) {
            mainUiDtoBuilder.shiftState(State.UNDEFINED);
        }

        return mainUiDtoBuilder.build();
    }

    //  Контроллеры
    public void onRefreshClick(ActionEvent event) throws AppException {
        updateView(getMainData());
    }

    public void onDiagnosticClick(ActionEvent event) throws AppException {
        context.publishEvent(new StartSessionEvent(StartSessionEvent.StartSession.REFRESH));
    }

    public void onIncomeCashClick(ActionEvent event) throws AppException {
        var income = Float.parseFloat(context.getBean(MainView.class).incomeField.getText());
        kktService.cashIncome(income);
    }

    public void onSwitchShiftClick(ActionEvent event) throws AppException {
        State state = kktService.switchShift();
        var viewUpdate = MainUiDto.builder().shiftState(state).build();
        context.getBean(MainView.class).updateView(viewUpdate);
    }

    public void onPrintLastClick(ActionEvent event) throws AppException {
        kktService.printLastCheck();
    }

    public void onPrintXClick(ActionEvent event) throws AppException {
        kktService.printXReport();
    }
}
