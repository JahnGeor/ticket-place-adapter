package ru.kidesoft.desktop.controller.javafx.fxml;

import atlantafx.base.controls.RingProgressIndicator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.HistoryUiDto;
import ru.kidesoft.desktop.controller.javafx.dto.HistoryUiDtoList;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.history.StatusType;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.PrinterService;
import ru.kidesoft.desktop.domain.service.entities.HistoryService;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

@FxmlView("/fxml/history.fxml")
@org.springframework.stereotype.Controller
public class HistoryController extends Controller<HistoryUiDtoList> {
    private final HistoryService historyService;
    private final PrinterService printerService;
    @FXML
    private TableColumn<HistoryUiDto, Boolean> checkCol;

    @FXML
    private TableColumn<HistoryUiDto, String> dateCol;

    @FXML
    private TableColumn<HistoryUiDto, String> errorCol;

    @FXML
    private TableView<HistoryUiDto> historyTable;

    @FXML
    private TableColumn<HistoryUiDto, String> numberCol;

    @FXML
    private TableColumn<HistoryUiDto, String> operationTypeCol;

    @FXML
    private Button printButton;

    @FXML
    private Label printCheckLabel;

    @FXML
    private Label printTicketLabel;

    @FXML
    private TableColumn<HistoryUiDto, String> sourceCol;

    @FXML
    private TableColumn<HistoryUiDto, String> statusTypeCol;


    @Autowired
    public HistoryController(ConfigurableApplicationContext context, HistoryService historyService, PrinterService printerService) {
        super(context);
        this.historyService = historyService;
        this.printerService = printerService;
    }

    @FXML
    private void printAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Label("", new CheckBox());

        var checkBox = new CheckBox();
        checkBox.setOnAction(
                e -> {
                    historyTable.getItems().forEach(
                            historyUiDto -> historyUiDto.getCheck().setValue(checkBox.isSelected())
                    );
                }
        );
        checkCol.setGraphic(checkBox);

        checkCol.setCellFactory(
                CheckBoxTableCell.forTableColumn(checkCol)
        );
        checkCol.setSortable(false);
        checkCol.setEditable(true);

        checkCol.setCellValueFactory(
                cellData -> cellData.getValue().getCheck()
        );

        dateCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getHistory().getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                )
        );


        numberCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getHistory().getOrderId().toString())
        );


        sourceCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getHistory().getSourceType().getDescription())
        );


        operationTypeCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getHistory().getOperationType().getDescription())
        );


        statusTypeCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getHistory().getStatusType().getDescription())
        );


        errorCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getHistory().getError())
        );

        printCheckLabel.setGraphic(new CheckBox());
        printTicketLabel.setGraphic(new CheckBox());

        printButton.setOnAction(this::printSelected);
        try {
            var listDto = new HistoryUiDtoList();

            var list = historyService.getListByLogin();

            list.forEach(history -> listDto.list.add(HistoryUiDto.builder().history(history).check(new SimpleBooleanProperty(false)).build()));

            updateView(listDto);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateView(HistoryUiDtoList viewDto) {


        historyTable.setItems(viewDto.list);
    }

    public void printSelected(ActionEvent event) {

        var printServiceTask = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<>() {


                    @Override
                    protected Integer call() throws Exception {

                        AtomicInteger progress = new AtomicInteger();

                        var list = historyTable.getItems().stream().filter(historyUiDto -> historyUiDto.getCheck().getValue()).toList();

                        list.forEach(
                                historyUiDto -> {
                                    try {
                                        printerService.print(historyUiDto.getHistory().getOrderId(), historyUiDto.getHistory().getSourceType(), historyUiDto.getHistory().getOperationType());
                                    } catch (AppException ignored) {

                                    } finally {
                                        updateValue(getValue() + 1);
                                        updateProgress(progress.addAndGet(1), list.size());
                                    }
                                }
                        );

                        return getValue();
                    }
                };
            }
        };

        Stage stage = new Stage();
        var progress = new ProgressIndicator(0);

        progress.progressProperty().bind(printServiceTask.progressProperty());

        var box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setFillWidth(true);
        box.getChildren().add(progress);

        stage.setScene(new Scene(box));

        printServiceTask.stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        stage.close();
                    }

                    if (newValue == Worker.State.FAILED) {
                        stage.close();
                    }

                    if (newValue == Worker.State.CANCELLED) {
                        stage.close();
                    }

                    if (newValue == Worker.State.RUNNING) {
                        stage.show();
                    }
                }
        );


        printServiceTask.start();



        // TODO: Выполнить печать по выбранным историям
    }
}
