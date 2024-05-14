package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.ApiDao;
import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.dao.KktDao;
import ru.kidesoft.ticketplace.client.domain.dao.dto.AuthorizationDto;
import ru.kidesoft.ticketplace.client.domain.dao.dto.LoginProtected;
import ru.kidesoft.ticketplace.client.domain.models.exception.AppException;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Login;

import java.util.Optional;
import java.util.UUID;

public class LoginUsecase {
    DatabaseDao databaseDao;
    ApiDao apiDao;

    public LoginUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    Logger logger = LogManager.getLogger(LoginUsecase.class);

    public UUID getActiveLoginUUID() throws AppException {
        UUID loginId = databaseDao.getConstantDao().getActiveUserUUID();

        if (loginId == null) {
            logger.warn("Отсутствует активная учетная запись");
        }

        logger.trace("Обнаружена активная учетная запись с UUID: {}", loginId);

        return loginId;
    }

    public Login getLogin() throws AppException {
        Login loginDto = databaseDao.getLoginDao().getLogin();

        if (loginDto == null) {
            logger.error("Отсутствуют данные для окна авторизации");
            throw new NullPointerException("loginDto is null");
        }

        if (loginDto.getUrls().isEmpty()) {
            logger.warn("Отсутствуют данные по адресам серверов");
            var defUrl = databaseDao.getConstantDao().getDefaultUrl();
            loginDto.getUrls().add(defUrl);
            logger.info("Добавлен адрес по умолчанию");
        } else {
            logger.trace("Обнаружены данные для окна авторизации: {}", loginDto);
        }


        return loginDto;
    }

    public void setActiveLoginUUID(UUID loginId) throws AppException {
        logger.warn("Установить активного пользователя - не реализовано");
    }

    public void login(Login login) throws AppException {
        //logger.warn("Авторизация - не реализовано");
        AuthorizationDto authorizationDto = apiDao.authorization(login);

        databaseDao.startTransaction();
        try {
            UUID loginId = databaseDao.getLoginDao().save(login);
            logger.trace("Cохранена учетная запись с UUID: {}", loginId);
            UUID profileId = databaseDao.getProfileDao().save(authorizationDto.profile);
            logger.trace("Cохранен профиль с UUID: {}", profileId);
            UUID sessionId = databaseDao.getSessionDao().save(authorizationDto.session);
            logger.trace("Cохранена сессия с UUID: {}", sessionId);
            UUID settingId = databaseDao.getSettingDao().setDefault();
            logger.trace("Cохранен настройка с UUID: {}", settingId);
            databaseDao.getConstantDao().setActiveUserUUID(loginId);
            logger.trace("Установлена активная учетная запись с UUID: {}", loginId);
        } catch (DbException e) {
            databaseDao.rollback();
            throw e;
        }
        databaseDao.commit();
    }

}
