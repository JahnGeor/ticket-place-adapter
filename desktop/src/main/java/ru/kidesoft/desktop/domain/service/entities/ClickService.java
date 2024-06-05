package ru.kidesoft.desktop.domain.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.ClickRepository;
import ru.kidesoft.desktop.domain.entity.click.Click;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;
import ru.kidesoft.desktop.domain.exception.DbException;

@Service
public class ClickService {

    private Logger logger = LogManager.getLogger(ClickService.class);

    private final LoginService loginService;
    private final ClickRepository clickRepository;

    public ClickService(LoginService loginService, ClickRepository clickRepository) {
        this.loginService = loginService;
        this.clickRepository = clickRepository;
    }

    public Click getByLogin(Login login) throws AppException {
        var click = clickRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить последний клик пользователя {}", login.getId());
                    return new DbException("Не удалось получить последний клик пользователя");
                }
        );

        logger.info("Получен последний клик пользователя {}", login.getId());
        return click;
    }

    public Click getCurrentClick() throws AppException {
        var login = loginService.getCurrentLogin();
        return getByLogin(login);
    }

    /*
    *    apiRepositoryFactoryImpl
    *            .setHost(login.getUrl())
    *            .setTimeout(setting.getServerRequestTimeout())
    *            .setToken(session.getAccessToken()).build()
    *            .Click(profile.getUserId());
    *  */
}
