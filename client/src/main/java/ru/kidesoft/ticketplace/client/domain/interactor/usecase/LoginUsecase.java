package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.api.ApiDao;
import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization.AuthorizationDto;
import ru.kidesoft.ticketplace.client.domain.models.entities.login.Login;
import ru.kidesoft.ticketplace.client.domain.models.exception.AppException;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.models.exception.WebException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.AuthProfile;

import java.util.Optional;
import java.util.UUID;

public class LoginUsecase {
    DatabaseDao databaseDao;
    ApiDao apiDao;

    public LoginUsecase(DatabaseDao databaseDao, ApiDao apiDao) {
        this.databaseDao = databaseDao;
        this.apiDao = apiDao;
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

    public AuthProfile getLogin() throws AppException {

        try {
            AuthProfile authProfileDto = databaseDao.getLoginDao().getAuthProfile();

            if (authProfileDto == null) {
                logger.error("Отсутствуют данные для окна авторизации");
                throw new NullPointerException("loginDto is null");
            }

            if (authProfileDto.getUrls().isEmpty()) {
                logger.warn("Отсутствуют данные по адресам серверов");
                var defUrl = databaseDao.getConstantDao().getDefaultUrl();
                authProfileDto.getUrls().add(defUrl);
                logger.info("Добавлен адрес по умолчанию");
            } else {
                logger.trace("Обнаружены данные для окна авторизации: {}", authProfileDto);
            }

            return authProfileDto;
        } catch (DbException e) {
            logger.error("Возникла ошибка при получении данных для окна авторизации", e);
            throw new AppException(e);
        }
    }

    public void logout() throws AppException {
        try {
            databaseDao.getConstantDao().setActiveUserUUID(null);
        } catch (DbException e) {
            logger.error("Возникла ошибка при выходе из учетной записи", e);
            throw e;
        }
    }

    public void login(Login login) throws AppException {
        try {
            AuthorizationDto authorizationDto = apiDao.authorization(login);
            databaseDao.startTransaction();

            UUID loginId = databaseDao.getLoginDao().save(login);
            logger.trace("Cохранена учетная запись с UUID: {}", loginId);
            databaseDao.getConstantDao().setActiveUserUUID(loginId);
            logger.trace("Установлена активная учетная запись с UUID: {}", loginId);
            UUID profileId = databaseDao.getProfileDao().save(authorizationDto.profile);
            logger.trace("Cохранен профиль с UUID: {}", profileId);
            UUID sessionId = databaseDao.getSessionDao().save(authorizationDto.session);
            logger.trace("Cохранена сессия с UUID: {}", sessionId);
            UUID settingId = databaseDao.getSettingDao().setDefault();
            logger.trace("Cохранен настройка с UUID: {}", settingId);
            databaseDao.commit();
        } catch (DbException e) {
            databaseDao.rollback();
            logger.trace("Отмена транзакции авторизации");
            logger.error("Не удалось сохранить данные в системе после авторизации на удаленном сервере", e);
            throw e;
        } catch (WebException e) {
            logger.error("Не удалось авторизоваться на удаленном сервере", e);
            throw e;
        }

    }

}
