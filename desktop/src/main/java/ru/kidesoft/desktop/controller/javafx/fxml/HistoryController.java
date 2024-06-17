package ru.kidesoft.desktop.controller.javafx.fxml;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kidesoft.desktop.controller.handler.Handler;
import ru.kidesoft.desktop.controller.handler.HandlerManager;
import ru.kidesoft.desktop.controller.javafx.Controller;
import ru.kidesoft.desktop.controller.javafx.dto.HistoryUiDto;
import ru.kidesoft.desktop.controller.javafx.dto.HistoryUiDtoList;
import ru.kidesoft.desktop.controller.javafx.events.manager.StageManager;
import ru.kidesoft.desktop.controller.javafx.fxml.progress.Progress;
import ru.kidesoft.desktop.domain.entity.history.StatusType;
import ru.kidesoft.desktop.domain.entity.order.PrintType;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.service.PrinterService;
import ru.kidesoft.desktop.domain.service.entities.HistoryService;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

@FxmlView("/fxml/history.fxml")
@org.springframework.stereotype.Controller
public class HistoryController extends Controller<HistoryUiDtoList> {
    private final HistoryService historyService;
    private final PrinterService printerService;
    private final HandlerManager handlerManager;
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

    @FXML private MenuButton filterMenuButton;


    @Autowired
    public HistoryController(ConfigurableApplicationContext context, HistoryService historyService, PrinterService printerService, HandlerManager handlerManager) {
        super(context);
        this.historyService = historyService;
        this.printerService = printerService;
        this.handlerManager = handlerManager;
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

        printButton.setOnAction(eventAction -> handlerManager.handle(eventAction, this::printSelected));

        Menu filterByStatus = new Menu("Фильтр по статусу");

        MenuItem filterByStatusAll = new MenuItem("Все");
        MenuItem filterByStatusSuccess = new MenuItem(StatusType.SUCCESS.getDescription());
        MenuItem filterByStatusError = new MenuItem(StatusType.ERROR.getDescription());

        filterByStatus.getItems().addAll(filterByStatusAll, filterByStatusSuccess, filterByStatusError);

        filterMenuButton.getItems().add(filterByStatus);

        init();


    }

    public Void init() {
        try {
            var listDto = new HistoryUiDtoList();
            var list = historyService.getListByLogin();
            list.forEach(history -> listDto.list.add(HistoryUiDto.builder().history(history).check(new SimpleBooleanProperty(false)).build()));
            updateView(listDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void updateView(HistoryUiDtoList viewDto) {
        ((CheckBox) this.checkCol.getGraphic()).setSelected(false);
        historyTable.setItems(viewDto.list);
    }

    public void printSelected(ActionEvent event) throws AppException {

        if (historyTable.getItems().stream().noneMatch(
                historyUiDto -> historyUiDto.getCheck().getValue()
        )) {
            context.getBean(StageManager.class).showWarning("Отсутствуют объекты для печати", "Пожалуйста, выберите объекты для печати и повторите попытку");
            return;
        }

        var printType = new ArrayList<PrintType>();

        if ( ((CheckBox) printCheckLabel.getGraphic()).isSelected()) {
            printType.add(PrintType.CHECK);
        }
        if (((CheckBox) printTicketLabel.getGraphic()).isSelected()) {
            printType.add(PrintType.TICKET);
        }

        if (printType.isEmpty()) {
            context.getBean(StageManager.class).showWarning("Не выбран тип печати", "Пожалуйста, выберите тип печати и повторите попытку");
            return;
        }


        var printServiceTask = new Service<String>() {
            @Override
            protected Task<String> createTask() {

                var list = historyTable.getItems().stream().filter(
                        historyUiDto -> historyUiDto.getCheck().getValue()
                ).map(HistoryUiDto::getHistory).distinct().toList();

                Integer size = list.size();



                return new Task<>() {


                    @Override
                    protected String call() throws Exception {
                        updateProgress(0, size);

                        updateTitle("Печать...");

                        AtomicInteger progress = new AtomicInteger(0);

                        for (var i = 0; i < list.size(); i++) {
                            try {
                                var history = list.get(i);
                                printerService.print(history.getOrderId(), history.getSourceType(), history.getOperationType(), printType.toArray(PrintType[]::new));
                                progress.set(progress.get() + 1);

                            } catch (Throwable ignored) {

                            } finally {
                                updateMessage("Завершено: " + (i+1) + " из " + size);
                                updateProgress(i+1, size);
                                updateValue("Успешно: " + progress.get());
                            }
                        }

                        if (progress.get() == size) {
                            updateTitle("Успешно завершено");
                        } else {
                            updateTitle("Завершено с ошибками");
                        }


                        return getValue();
                    }
                };
            }
        };



        var progressStage = context.getBean(Progress.class)
                .bindProgressProperty(printServiceTask.progressProperty())
                .bindValueProperty(printServiceTask.valueProperty())
                .bindTitleProperty(printServiceTask.titleProperty())
                .bindMessageProperty(printServiceTask.messageProperty());



        printServiceTask.stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        progressStage.showButton();
                    }

                    if (newValue == Worker.State.FAILED) {
                        progressStage.showButton();
                    }

                    if (newValue == Worker.State.CANCELLED) {
                        progressStage.showButton();
                    }

                    if (newValue == Worker.State.RUNNING) {
                        progressStage.start();
                    }
                }
        );

        printServiceTask.start();
        progressStage.getStage().showingProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                handlerManager.handle(this::init);
            }
        });



        // TODO: Выполнить печать по выбранным историям
    }
}
