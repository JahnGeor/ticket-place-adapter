package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.ProfileRepository;
import ru.kidesoft.desktop.domain.dao.database.SettingRepository;
import ru.kidesoft.desktop.domain.dao.kkt.*;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;
import ru.kidesoft.desktop.domain.exception.DbException;
import ru.kidesoft.desktop.domain.service.entities.ProfileService;

import java.time.ZonedDateTime;

@Service
public class KktService {

    private final Logger logger = LogManager.getLogger(KktService.class);
    private final ConfigurableApplicationContext applicationContext;
    private final KktRepository kktRepository;
    private final ProfileRepository profileRepository;
    private final SettingRepository settingRepository;
    private final LoginRepository loginRepository;
    private final ProfileService profileService;
    private final KktSystem kktSystem;
    private final KktPrinter kktPrinter;

    @Autowired
    public KktService(ConfigurableApplicationContext applicationContext, KktRepository kktRepository, ProfileRepository profileRepository, SettingRepository settingRepository, LoginRepository loginRepository, ProfileService profileService, KktSystem kktSystem, KktPrinter kktPrinter) {
        this.applicationContext = applicationContext;
        this.kktRepository = kktRepository;
        this.profileRepository = profileRepository;
        this.settingRepository = settingRepository;
        this.loginRepository = loginRepository;
        this.profileService = profileService;
        this.kktSystem = kktSystem;
        this.kktPrinter = kktPrinter;
    }

    public void initialize() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new DbException("Не удалось получить текущего пользователя")
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new DbException("Не удалось получить профиль текущего пользователя")
        );
        var setting = settingRepository.findByLogin(login).orElseThrow(
                () -> new DbException("Не удалось получить настройки текущего пользователя")
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        var kktSetting = KktSetting.builder().autoReconnect(setting.getKktAutoReconnect()).path(setting.getKktDriverPath()).build();

        kktRepository.setConnection(kktSetting).openConnection();

        var state = kktSystem.getCurrentShiftState();


        // TODO: > Проверка имени и инн последнего чека

        if (state.equals(State.EXPIRED)) {
            kktSystem.setOperator(operator).getPrinter().closeShift();
            kktSystem.setCurrentTime(ZonedDateTime.now());
            kktSystem.setOperator(operator).getPrinter().openShift();
        } else if (state.equals(State.CLOSED)) {
            kktSystem.setCurrentTime(ZonedDateTime.now());
            kktSystem.setOperator(operator).getPrinter().openShift();
        }
    }

    public void openShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new DbException("Не удалось получить текущего пользователя")
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new DbException("Не удалось получить профиль текущего пользователя")
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        kktSystem.setOperator(operator).getPrinter().openShift();
    }

    public void closeShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new DbException("Не удалось получить текущего пользователя")
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new DbException("Не удалось получить профиль текущего пользователя")
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        kktSystem.setOperator(operator).getPrinter().closeShift();
    }

    public State switchShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new DbException("Не удалось получить текущего пользователя")
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new DbException("Не удалось получить профиль текущего пользователя")
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        State state = kktSystem.getCurrentShiftState();

        switch (state) {
            case OPENED:
                kktSystem.setOperator(operator).getPrinter().closeShift();
                break;
            case CLOSED:
                kktSystem.setOperator(operator).getPrinter().openShift();
                break;
            case EXPIRED:
                kktSystem.setOperator(operator).getPrinter().closeShift();
                kktSystem.setOperator(operator).getPrinter().openShift();
                break;
            default:
                logger.error("Неизвестное состояние смены ККТ: {}", state);
                throw new DbException("Неизвестное состояние смены ККТ");
        }

        return kktSystem.getCurrentShiftState();
    }

    public State getShiftState() throws AppException {
        try {
            var state = kktSystem.getCurrentShiftState();
            logger.info("Запрос состояния ККТ: {}", state);
            return state;
        } catch (Exception e) {
            logger.error("Не удалось получить состояние ККТ", e);
            return State.UNDEFINED;
            //throw new AppException("Не удалось получить состояние ККТ", AppExceptionType.KktExceptionType);
        }
    }

    public boolean isConnectionOpened() throws AppException {
        var status = kktSystem.isConnectionOpened();

        logger.info("Статус подключения ККТ: {}", status);

        return status;
    }

    public void printLastCheck() throws AppException {
        var profile = profileService.getCurrentProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktSystem.setOperator(operator).getPrinter().printLastReceipt();
        logger.info("Печать последнего чека, оператор: {}", profile.getLogin().getId());
    }

    public void printXReport() throws AppException {
        var profile = profileService.getCurrentProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktSystem.setOperator(operator).getPrinter().printXReport();
        logger.info("Печать X-отчета, id профиля: {}", profile.getLogin().getId());
    }

    public void printXReportByOperator(Cashier cashier) throws AppException {
        var operator = KktOperator.builder().fullName(cashier.getFullName()).inn(cashier.getInn()).build();

        try { // TODO: Заменить
            kktRepository.getFptr(); // TODO: Заменить
        } catch (Exception e) { // TODO: Заменить
            kktRepository.setConnection(); // TODO: Заменить
        } // TODO: Заменить

        kktRepository.openConnection();
        kktSystem.setOperator(operator).getPrinter().printXReport();

        logger.info("Печать X-отчета под профилем: ИНН: {}, ФИО: {}", cashier.getInn(), cashier.getFullName());
    }

    public void closeShiftByOperator(Cashier cashier) throws AppException {
        var operator = KktOperator.builder().fullName(cashier.getFullName()).inn(cashier.getInn()).build();

        try { // TODO: Заменить
            kktRepository.getFptr(); // TODO: Заменить
        } catch (Exception e) { // TODO: Заменить
            kktRepository.setConnection(); // TODO: Заменить
        } // TODO: Заменить

        kktRepository.openConnection();
        kktSystem.setOperator(operator).getPrinter().closeShift();

        logger.info("Закрытие смены под профилем: ИНН: {}, ФИО: {}", cashier.getInn(), cashier.getFullName());
    }

    public void cashIncome(float income) throws AppException {
        var profile = profileService.getCurrentProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktSystem.setOperator(operator).getPrinter().income(income);
        logger.info("Внесение наличных средств в размере {} рублей, id профиля: {}", income, profile.getLogin().getId());
    }

    public void cancelReceipt() throws AppException {
        var profile = profileService.getCurrentProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktSystem.setOperator(operator).getPrinter().cancelReceipt();
        logger.info("Отмена чека, id пользователя: {}", profile.getLogin().getId());
    }

    public void kktTime() throws AppException {
        var time = ZonedDateTime.now();
        kktSystem.setCurrentTime(time);
        logger.info("Время ККТ {} установлено", time);
    }
}
