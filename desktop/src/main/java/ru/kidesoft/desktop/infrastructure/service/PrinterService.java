package ru.kidesoft.desktop.infrastructure.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ClickDto;
import ru.kidesoft.desktop.infrastructure.port.dao.database.HistoryRepository;
import ru.kidesoft.desktop.infrastructure.port.dao.database.LoginRepository;
import ru.kidesoft.desktop.infrastructure.port.dao.database.SessionRepository;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktOperator;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktRepository;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktSystem;
import ru.kidesoft.desktop.infrastructure.port.api.printer.PrinterRepository;
import ru.kidesoft.desktop.infrastructure.port.api.printer.dto.PrinterDto;
import ru.kidesoft.desktop.domain.entity.click.Click;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.history.StatusType;
import ru.kidesoft.desktop.domain.entity.order.*;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.exception.*;
import ru.kidesoft.desktop.infrastructure.service.entities.*;
import ru.kidesoft.desktop.repository.api.ApiRepositoryFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PrinterService {
    private final Logger logger = LogManager.getLogger(PrinterService.class);
    private final ApiRepositoryFactoryImpl apiRepositoryFactoryImpl;
    private final ProfileService profileService;
    private final SettingService settingService;
    private final SessionRepository sessionRepository;
    private final LoginService loginService;
    private final SessionService sessionService;
    private final KktRepository kktRepository;
    private final PrinterRepository printerRepository;
    private final HistoryService historyService;
    private final KktSystem kktSystem;
    private final OrderService orderService;
    private final ClickService clickService;
    ConfigurableApplicationContext applicationContext;
    private final LoginRepository loginRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public PrinterService(ConfigurableApplicationContext applicationContext, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl, ProfileService profileService, SettingService settingService,
                          LoginRepository loginRepository, SessionRepository sessionRepository, LoginService loginService, SessionService sessionService, KktRepository kktRepository, PrinterRepository printerRepository, HistoryService historyService,
                          HistoryRepository historyRepository, KktSystem kktSystem, OrderService orderService, ClickService clickService) {
        this.applicationContext = applicationContext;
        this.apiRepositoryFactoryImpl = apiRepositoryFactoryImpl;
        this.profileService = profileService;
        this.settingService = settingService;
        this.loginRepository = loginRepository;
        this.sessionRepository = sessionRepository;
        this.loginService = loginService;
        this.sessionService = sessionService;
        this.kktRepository = kktRepository;
        this.printerRepository = printerRepository;
        this.historyService = historyService;
        this.historyRepository = historyRepository;
        this.kktSystem = kktSystem;
        this.orderService = orderService;
        this.clickService = clickService;
    }

    public void print(int orderId, SourceType sourceType, OperationType operationType, PrintType ...printType) throws AppException {
        var order = orderService.getOrder(orderId, sourceType, operationType);

        ArrayList<PrintType> arrayPrintType = new ArrayList<>(List.of(Arrays.stream(printType).filter(Objects::nonNull).toArray(PrintType[]::new)));

        var setting = settingService.getByLogin(loginService.getCurrentLogin());

        if (arrayPrintType.isEmpty()) {
            if (setting.getPrintCheck()) {
                arrayPrintType.add(PrintType.CHECK);
            }
            if (setting.getPrintTicket()) {
                arrayPrintType.add(PrintType.TICKET);
            }
        }

        for (PrintType print : arrayPrintType) {
            switch (print) {
                case CHECK -> processKktPrint(order);
                case TICKET -> {
                    var printerDto = PrinterDto.builder().name(setting.getPrinterName()).pageSize(setting.getPageSize()).pageOrientation(setting.getPageOrientation()).build();
                    processTicketPrint(order, printerDto);
                }
            }
        }
    } // NOTE: По сути это - Service Manager (компиляция из разного набора сервисов в одну точку входа)



    public void kktPrint(Order order) throws AppException {
        var orderForCheck = order.toBuilder().buildFor(PrintType.CHECK);

        var profile = profileService.getCurrentProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        kktSystem.setOperator(operator).getPrinter().print(orderForCheck);
        logger.info("Печать чека завершена");
    }

    public void processKktPrint(Order order) throws AppException {

            var historyBuilder = History.builder().orderId(order.getId()).sourceType(order.getSourceType()).operationType(order.getOperationType());
            try {
                kktPrint(order);
                historyBuilder.statusType(StatusType.SUCCESS).error(null);
            } catch (BusinessRulesException e) {
                historyBuilder.statusType(StatusType.SUCCESS).error(e.getMessage());
                logger.warn("Чек не печатается в следствии бизнес-правил", e);
            } catch (AppException ex) {
                historyBuilder.statusType(StatusType.ERROR).error(ex.getMessage());
                logger.error("Не удалось выполнить печать чека", ex);
                throw ex;
            } finally {
                historyService.saveByCurrentLogin(historyBuilder.build());
            }

    }

    public void processTicketPrint(Order order, PrinterDto setting) throws AppException {
        try {
            var orderForTicket = order.toBuilder().buildFor(PrintType.TICKET);
            var profile = profileService.getCurrentProfile();
            var cashier = Cashier.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
            var orderDto = OrderDto.builder().order(orderForTicket).cashier(cashier).build();

            ticketPrint(orderDto, setting);
        } catch (BusinessRulesException e) {
            logger.trace("Билет не печатается в следствии бизнес-правил", e);
        }

    }

    public void ticketPrint(OrderDto order, PrinterDto setting) throws AppException {
        logger.trace("Печать билета: {}", order.getOrder().getId());
        printerRepository.print(order, setting);
    }

    public void printByClick() throws AppException {
        ClickDto click = null;
        var clickResultBuilder = Click.builder();
        try {
            click = clickService.getClickFromRemote();

            var currentClick = clickService.getCurrentClick();

            if (Objects.equals(currentClick.getClickId(), click.getId())) {
                logger.trace("Последний клик не изменился");
                return;
            }

            logger.info("Последний клик изменился: {}", click.getId());

            logger.info("Производим печать клика: {} с orderId: {}, тип: {}", click.getId(), click.getOrderId(), click.getSourceType());

            print(click.getOrderId(), click.getSourceType(), click.getOrderType());

            logger.info("Печать клика завершена: {}", click.getId());

            clickResultBuilder.clickId(click.getId());

        } catch (Throwable e) {
            logger.trace("Ошибка в процессе получения клика", e);
            throw e;
        } finally {
            var resultClick = clickResultBuilder.build();

            if (resultClick.getClickId() != null) {
                clickService.save(resultClick);
            }
        }
    }



}
