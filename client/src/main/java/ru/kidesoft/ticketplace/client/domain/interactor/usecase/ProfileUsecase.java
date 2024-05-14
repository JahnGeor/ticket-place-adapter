package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

public class ProfileUsecase {
    private static final Logger logger = LogManager.getLogger(ProfileUsecase.class);
    DatabaseDao databaseDao;

    public ProfileUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public Profile getProfile() throws DbException {
        try {
            Profile profile = databaseDao.getProfileDao().getProfile();
            logger.trace("Обнаружен профиль: {}", profile);

            return profile;
        } catch (DbException e) {
            logger.error("Возникла ошибка при получении данных профиля", e);
            throw e;
        }
    }
}
