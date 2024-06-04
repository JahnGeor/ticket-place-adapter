package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.dao.database.ProfileRepository;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final LoginRepository loginRepository;
    private final Logger logger = LogManager.getLogger(ProfileService.class);
    ConfigurableApplicationContext applicationContext;
    @Autowired
    public ProfileService(ConfigurableApplicationContext applicationContext, ProfileRepository profileRepository, LoginRepository loginRepository) {
        this.applicationContext = applicationContext;
        this.profileRepository = profileRepository;
        this.loginRepository = loginRepository;
    }

    public Profile getProfile() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> {
                    logger.error("Не удалось получить id текущего пользователя");
                    return new AppException("Не удалось получить текущего пользователя", AppExceptionType.DbExceptionType);
                }
        );

        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить профиль текущего пользователя {}", login.getId());
                    return new AppException("Не удалось получить профиль текущего пользователя", AppExceptionType.DbExceptionType);
                }
        );

        logger.info("Получен профиль пользователя {}", profile.getId());

        return profile;
    }

    public List<Cashier> getCashier() throws AppException {
        var cashiers = profileRepository.findCashierList();
        logger.info("Получен список кассиров, размер списка: {}", cashiers.size());
        return cashiers;
    }
}
