package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.ProfileRepository;
import ru.kidesoft.desktop.domain.dao.database.SettingRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktSetting;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;

@Service
public class KktService {

    private final Logger logger = LogManager.getLogger(KktService.class);
    private final ConfigurableApplicationContext applicationContext;
    private final KktRepository kktRepository;
    private final ProfileRepository profileRepository;
    private final SettingRepository settingRepository;
    private final LoginRepository loginRepository;
    private final ProfileService profileService;

    @Autowired
    public KktService(ConfigurableApplicationContext applicationContext, KktRepository kktRepository, ProfileRepository profileRepository, SettingRepository settingRepository, LoginRepository loginRepository, ProfileService profileService) {
        this.applicationContext = applicationContext;
        this.kktRepository = kktRepository;
        this.profileRepository = profileRepository;
        this.settingRepository = settingRepository;
        this.loginRepository = loginRepository;
        this.profileService = profileService;
    }

    public void initialize() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new AppException("Не удалось получить текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Не удалось получить профиль текущего пользователя", AppExceptionType.DbExceptionType)
        );
        var setting = settingRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Не удалось получить настройки текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        var kktSetting = KktSetting.builder().autoReconnect(setting.getKktAutoReconnect()).path(setting.getKktDriverPath()).build();

        kktRepository.setConnection(kktSetting).openConnection().setOperator(operator);

        var state = kktRepository.getCurrentShiftState();


        // TODO: > Проверка имени и инн последнего чека

        if (
                state.equals(State.EXPIRED)
        ) {
            kktRepository.closeShift();
        } else if (
                state.equals(State.CLOSED)
        ) {
            kktRepository.openShift();
        }
    }

    public void openShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new AppException("Не удалось получить текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Не удалось получить профиль текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        kktRepository.setOperator(operator).openShift();
    }

    public void closeShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new AppException("Не удалось получить текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Не удалось получить профиль текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        kktRepository.setOperator(operator).closeShift();
    }

    public State switchShift() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> new AppException("Не удалось получить текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> new AppException("Не удалось получить профиль текущего пользователя", AppExceptionType.DbExceptionType)
        );

        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();

        State state = kktRepository.getCurrentShiftState();

        switch (state) {
            case OPENED:
                kktRepository.setOperator(operator).closeShift();
                break;
            case CLOSED:
                kktRepository.setOperator(operator).openShift();
                break;
            case EXPIRED:
                kktRepository.setOperator(operator).closeShift();
                kktRepository.setOperator(operator).openShift();
                break;
            default:
                logger.error("Неизвестное состояние ККТ: {}", state);
                throw new AppException("Неизвестное состояние ККТ", AppExceptionType.KktExceptionType);
        }

        return kktRepository.getCurrentShiftState();
    }

    public State getShiftState() throws AppException {
        try {
            var state = kktRepository.getCurrentShiftState();
            logger.info("Запрос состояния ККТ: {}", state);
            return state;
        } catch (Exception e) {
            logger.error("Не удалось получить состояние ККТ", e);
            throw new AppException("Не удалось получить состояние ККТ", AppExceptionType.KktExceptionType);
        }
    }

    public boolean isConnectionOpened() {
        var status = kktRepository.isConnectionOpened();

        logger.info("Статус подключения ККТ: {}", status);

        return status;
    }

    public void printLastCheck() throws AppException {
        var profile = profileService.getProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktRepository.setOperator(operator).printLastReceipt();
        logger.info("Печать последнего чека, оператор: {}", profile.getLogin().getId());
    }

    public void printXReport() throws AppException {
        var profile = profileService.getProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktRepository.setOperator(operator).printXReport();
        logger.info("Печать X-отчета, id профиля: {}", profile.getLogin().getId());
    }

    public void cashIncome(float income) throws AppException {
        var profile = profileService.getProfile();
        var operator = KktOperator.builder().fullName(profile.getFullname()).inn(profile.getInn()).build();
        kktRepository.setOperator(operator).income(income);
        logger.info("Внесение наличных средств в размере {} рублей, id профиля: {}", income, profile.getLogin().getId());
    }
}
