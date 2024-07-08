package ru.kidesoft.desktop.infrastructure.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.dao.database.ConstantRepository;
import ru.kidesoft.desktop.infrastructure.port.dao.database.LoginRepository;
import ru.kidesoft.desktop.infrastructure.port.dao.database.SettingRepository;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.setting.Setting;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;

import java.util.UUID;

@Service
public class SettingService {
    private final Logger logger = LogManager.getLogger(SettingService.class);

    private final ConstantRepository constantRepository;
    private final LoginRepository loginRepository;
    private final LoginService loginService;
    SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository, ConstantRepository constantRepository, LoginRepository loginRepository, LoginService loginService) {
        this.settingRepository = settingRepository;
        this.constantRepository = constantRepository;
        this.loginRepository = loginRepository;
        this.loginService = loginService;
    }

    public Setting getByLogin(Login login) throws AppException {
        var setting = settingRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить настройки пользователя {}", login.getId());
                    return new DbException("Не удалось получить настройки пользователя");
                }
        );

        logger.info("Получены настройки пользователя {}", login.getId());

        return setting;
    }

    public Setting getCurrentSetting() throws AppException {
        var login = loginService.getCurrentLogin();
        return getByLogin(login);
    }

    public void save(Setting setting) {
        var loginId = constantRepository.findByName(ConstantEnums.ACTIVE_USER_ID).orElseThrow(() -> new RuntimeException("Active user id not found")).getVal();

        var login = loginRepository.findById(UUID.fromString(loginId)).orElseThrow(() -> new RuntimeException("Didn't find login with active user id"));

        setting.setLogin(login);

        settingRepository.findByLogin(login).ifPresent(
                oldSetting -> {
                    setting.setId(oldSetting.getId());
                }
        );

        settingRepository.save(setting);
    }

}
