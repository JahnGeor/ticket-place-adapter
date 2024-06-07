package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.HistoryRepository;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.SessionRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktSystem;
import ru.kidesoft.desktop.domain.dao.printer.PrinterRepository;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.history.StatusType;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.PrintType;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.*;
import ru.kidesoft.desktop.domain.service.entities.*;
import ru.kidesoft.desktop.repository.api.ApiRepositoryFactoryImpl;

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
    ConfigurableApplicationContext applicationContext;
    private final LoginRepository loginRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public PrinterService(ConfigurableApplicationContext applicationContext, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl, ProfileService profileService, SettingService settingService,
                          LoginRepository loginRepository, SessionRepository sessionRepository, LoginService loginService, SessionService sessionService, KktRepository kktRepository, PrinterRepository printerRepository, HistoryService historyService,
                          HistoryRepository historyRepository, KktSystem kktSystem, OrderService orderService) {
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
    }

    public void print(int orderId, SourceType sourceType, OperationType operationType) throws AppException {
        var order = orderService.getOrder(orderId, sourceType, operationType);

        var setting = settingService.getByLogin(loginService.getCurrentLogin());

        if (setting.getPrintCheck()) {
            processKktPrint(order);
        }

        if (setting.getPrintTicket()) {
            processTicketPrint(order);
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

    public void processTicketPrint(Order order) throws AppException {
        try {
            var orderForTicket = order.toBuilder().buildFor(PrintType.TICKET);
            ticketPrint(orderForTicket);
        } catch (BusinessRulesException e) {
            logger.trace("Билет не печатается в следствии бизнес-правил", e);
        }

    }

    public void ticketPrint(Order order) throws AppException {
        logger.trace("Печать билета: {}", order.getId());
        printerRepository.print(order);
    }

}
