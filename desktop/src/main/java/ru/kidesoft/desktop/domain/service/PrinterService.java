package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.HistoryRepository;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.SessionRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
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
    ConfigurableApplicationContext applicationContext;
    private final LoginRepository loginRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public PrinterService(ConfigurableApplicationContext applicationContext, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl, ProfileService profileService, SettingService settingService,
                          LoginRepository loginRepository, SessionRepository sessionRepository, LoginService loginService, SessionService sessionService, KktRepository kktRepository, PrinterRepository printerRepository, HistoryService historyService,
                          HistoryRepository historyRepository) {
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
    }

    public void processPrint(int orderId, SourceType sourceType, OperationType operationType) throws AppException {
        var login = loginService.getCurrentLogin();

        logger.info("Печать чека с orderId: {} sourceType: {} operationType: {}", orderId, sourceType, operationType);


        var setting = settingService.getByLogin(login);
        var session = sessionService.getByLogin(login);
        var profile = profileService.getByLogin(login);

        var operator = KktOperator.builder()
                .fullName(profile.getFullname())
                .inn(profile.getInn())
                .build();


        var order = apiRepositoryFactoryImpl
                .setHost(login.getUrl())
                .setTimeout(setting.getServerRequestTimeout())
                .setToken(session.getAccessToken())
                .setTokenType(session.getTokenType())
                .build().Order(orderId, sourceType);

        logger.info("Получен заказ с id {} из ресурса с типом {}", orderId, sourceType);

        if (setting.getPrintCheck()) {
            var orderForCheck = order.toBuilder().buildFor(PrintType.CHECK);
            kktRepository.setOperator(operator).print(orderForCheck, operationType);
            logger.info("Печать чека завершена");
        }

        if (setting.getPrintTicket()) {
            var orderForTicket = order.toBuilder().buildFor(PrintType.TICKET);
            printerRepository.print(orderForTicket);
            logger.info("Печать билета завершена");
        }

    }

    public void print(int orderId, SourceType sourceType, OperationType operationType) throws AppException {
        var historyBuilder = History.builder().orderId(orderId).sourceType(sourceType).operationType(operationType);

        try {
            processPrint(orderId, sourceType, operationType);
            historyBuilder.statusType(StatusType.SUCCESS).error(null);
        } catch (BusinessRulesException e) {
            historyBuilder.statusType(StatusType.SUCCESS).error(e.getMessage());
            logger.warn("Чек не печатается в следствии бизнес-правил", e);
            throw e;
        } catch (AppException ex) {
            historyBuilder.statusType(StatusType.ERROR).error(ex.getMessage());
            logger.error("Не удалось выполнить печать чека", ex);
            throw ex;
        } finally {
            historyService.saveByCurrentLogin(historyBuilder.build());
        }

        // TODO: Запись в таблицу истории
    }
}
