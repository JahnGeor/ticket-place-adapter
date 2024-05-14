package ru.kidesoft.ticketplace.client.domain.dao;

import ru.kidesoft.ticketplace.client.domain.models.entities.history.History;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.Profile;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Login;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Setting;

import java.util.UUID;

public interface DatabaseDao {
    interface ClickDao {
    }

    interface ConstantDao {
        UUID getActiveUserUUID() throws DbException;

        void setActiveUserUUID(UUID loginId) throws DbException;

        String getDefaultUrl() throws DbException;
    }

    interface ProfileDao {
        UUID save(Profile profile) throws DbException;
    }

    interface LoginDao {
        UUID save(Login login) throws DbException;
        Login getLogin() throws DbException;
    }

    interface SettingDao {
        UUID save(Setting setting) throws DbException;
        UUID setDefault() throws DbException;
    }

    interface SessionDao {
        UUID save(Session session) throws DbException;
    }

    interface HistoryDao {
        UUID save(History history) throws DbException;
    }

    ClickDao getClickDao();

    ProfileDao getProfileDao();

    LoginDao getLoginDao();

    SettingDao getSettingDao();

    SessionDao getSessionDao();

    HistoryDao getHistoryDao();

    ConstantDao getConstantDao();

    void commit() throws DbException;

    void rollback() throws DbException;

    void startTransaction() throws DbException;

    void endTransaction() throws DbException;
}


