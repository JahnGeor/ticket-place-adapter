package ru.kidesoft.desktop.infrastructure.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.dao.database.LoginRepository;
import ru.kidesoft.desktop.infrastructure.port.dao.database.ProfileRepository;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final LoginRepository loginRepository;
    private final Logger logger = LogManager.getLogger(ProfileService.class);
    private final LoginService loginService;
    ConfigurableApplicationContext applicationContext;
    @Autowired
    public ProfileService(ConfigurableApplicationContext applicationContext, ProfileRepository profileRepository, LoginRepository loginRepository, LoginService loginService) {
        this.applicationContext = applicationContext;
        this.profileRepository = profileRepository;
        this.loginRepository = loginRepository;
        this.loginService = loginService;
    }

    public Profile getByLogin(Login login) throws AppException {
        var profile = profileRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить профиль текущего пользователя {}", login.getId());
                    return new DbException("Не удалось получить профиль текущего пользователя");
                }
        );

        logger.info("Получен профиль пользователя {}", profile.getId());

        return profile;
    }

    public Profile getCurrentProfile() throws AppException {
        var login = loginService.getCurrentLogin();
        return getByLogin(login);
    }

    public List<Cashier> getCashier() throws AppException {
        var cashiers = profileRepository.findCashierList();
        logger.info("Получен список кассиров, размер списка: {}", cashiers.size());
        return cashiers;
    }

    public void getCurrentSession() {

    }
}
