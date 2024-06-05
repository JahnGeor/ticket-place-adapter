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
import ru.kidesoft.desktop.controller.handler.HandlerManager;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.AdminUiDto;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.PrinterService;

import java.net.URL;
import java.util.ResourceBundle;
@org.springframework.stereotype.Controller
@FxmlView("/fxml/admin.fxml")
public class AdminController extends Controller<AdminUiDto> {
    private final PrinterService printerService;
    private final HandlerManager handlerManager;

    @Autowired
    public AdminController(ConfigurableApplicationContext context, PrinterService printerService, HandlerManager handlerManager) {
        super(context);
        this.printerService = printerService;
        this.handlerManager = handlerManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printRefundFromCheckButton.setGraphic(new FontIcon(FluentUiRegularAL.ARROW_ENTER_24));
        printSellFromCheckButton.setGraphic(new FontIcon(FluentUiRegularAL.ARROW_ENTER_24));

        printRefundFromCheckButton.setOnAction(event -> handlerManager.handle(event, this::printRefundFromCheckAction));
        printSellFromCheckButton.setOnAction(event -> handlerManager.handle(event, this::printSellFromCheckAction));
    }

    @Override
    public void updateView(AdminUiDto viewDto) {

    }

    @FXML private TextField printRefundFromCheckLabel;

    @FXML private TextField printSellFromCheckLabel;

    @FXML private Button printSellFromCheckButton;

    @FXML private Button printRefundFromCheckButton;

    void printRefundFromCheckAction(ActionEvent event) throws AppException {
        var orderId = Integer.parseInt(printRefundFromCheckLabel.getText());
        printerService.print(orderId, SourceType.ORDER, OperationType.REFUND);
    }

    void printSellFromCheckAction(ActionEvent event) throws AppException {
        var orderId = Integer.parseInt(printSellFromCheckLabel.getText());
        printerService.print(orderId, SourceType.ORDER, OperationType.ORDER);
    }
}
