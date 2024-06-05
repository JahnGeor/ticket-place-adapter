package ru.kidesoft.desktop.domain.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.database.SessionRepository;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.session.Session;
import ru.kidesoft.desktop.domain.entity.setting.Setting;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.AppExceptionType;
import ru.kidesoft.desktop.domain.exception.DbException;

@Service
public class SessionService {

    private final Logger logger = LogManager.getLogger(SessionService.class);

    private final LoginService loginService;
    private final SessionRepository sessionRepository;

    public SessionService(LoginService loginService, SessionRepository sessionRepository) {
        this.loginService = loginService;
        this.sessionRepository = sessionRepository;
    }

    public Session getByLogin(Login login) throws AppException {
        var session = sessionRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить текущую сессию пользователя {}", login.getId());
                    return new DbException("Не удалось получить текущую сессию пользователя");
                }
        );

        logger.info("Получена текущая сессия пользователя {}", login.getId());
        return session;
    }

    public Session getCurrentSession() throws AppException {
        var login = loginService.getCurrentLogin();
        return getByLogin(login);
    }
}
