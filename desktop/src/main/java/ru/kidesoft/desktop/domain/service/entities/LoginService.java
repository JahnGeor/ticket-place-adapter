package ru.kidesoft.desktop.domain.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.LoginRepository;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;
import ru.kidesoft.desktop.domain.exception.DbException;


@Service
public class LoginService {
    private final Logger logger = LogManager.getLogger(LoginService.class);
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login getCurrentLogin() throws AppException {
        var login = loginRepository.findCurrentLogin().orElseThrow(
                () -> {
                    logger.error("Не удалось получить текущего пользователя");
                    return new DbException("Не удалось получить текущего пользователя");
                }
        );

        logger.info("Получен текущий пользователь {}", login.getId());
        return login;
    }
}
