package ru.kidesoft.ticketplace.client.domain.interactor.usecase;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.Setting;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;

public class SettingUsecase {
    private static final Logger logger = LogManager.getLogger();
    DatabaseDao databaseDao;

    public SettingUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public Setting getSetting() throws DbException {
        try {
            var setting = databaseDao.getSettingDao().getSetting();
            logger.trace("Полученные настройки: {}", setting);
            return setting;
        } catch (DbException e) {
            logger.error("Не удалось получить настройки по пользователю: ", e);
            throw new DbException(e);
        }
    }

    public void saveSetting(Setting setting) throws DbException {

        try {
            databaseDao.getSettingDao().save(setting);
        } catch (DbException e) {
            logger.error("Не удалось обновить настройки по пользователю: ", e);
            throw new DbException(e);
        }
    }
}
