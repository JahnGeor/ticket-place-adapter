package ru.kidesoft.ticketplace.client.repository.database.h2;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.output.MigrateResult;
import ru.kidesoft.ticketplace.client.domain.dao.database.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.models.exception.DbException;
import ru.kidesoft.ticketplace.client.repository.database.h2.impl.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2DBRepository implements DatabaseDao {

    private Connection connection;
    private ClickRepository clickRepository;
    private ProfileRepository profileRepository;
    private LoginRepository loginRepository;
    private SettingRepository settingRepository;
    private SessionRepository sessionRepository;
    private HistoryRepository historyRepository;
    private ConstantRepository constantRepository;
    private final MigrateResult migrateResult;

    public MigrateResult getMigrateResult() {
        return migrateResult;
    }

    public static H2DBRepositoryBuilder builder() {
        return new H2DBRepositoryBuilder();
    }

    @Override
    public ClickDao getClickDao() {
        return clickRepository;
    }

    @Override
    public ProfileDao getProfileDao() {
        return profileRepository;
    }

    @Override
    public LoginDao getLoginDao() {
        return loginRepository;
    }

    @Override
    public SettingDao getSettingDao() {
        return settingRepository;
    }

    @Override
    public SessionDao getSessionDao() {
        return sessionRepository;
    }

    @Override
    public HistoryDao getHistoryDao() {
        return historyRepository;
    }

    @Override
    public ConstantDao getConstantDao() {
        return constantRepository;
    }

    @Override
    public void commit() throws DbException {
        try {
            connection.commit();

            endTransaction();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void rollback() throws DbException {
        try {
            connection.rollback();

            endTransaction();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void startTransaction() throws DbException{

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public void endTransaction() throws DbException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    public static class H2DBRepositoryBuilder {
        String url;
        String user;
        String password;
        String locations;
        Connection connection;

        MigrateResult migrateResult;

        public H2DBRepositoryBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public H2DBRepositoryBuilder setUser(String user) {
            this.user = user;
            return this;
        }

        public H2DBRepositoryBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public H2DBRepositoryBuilder setProperties(Properties properties) {
            url = properties.getProperty("app.database.url");
            user = properties.getProperty("app.database.user");
            password = properties.getProperty("app.database.password");
            locations = properties.getProperty("app.database.migration.path");
            return this;
        }


        public H2DBRepositoryBuilder create() throws SQLException, ClassNotFoundException {
            connection = DriverManager.getConnection(url, user, password);
            //connection.close();
            return this;
        }

        public H2DBRepositoryBuilder migrate() throws IOException, SQLException {
            FluentConfiguration configuration = Flyway.configure();

            configuration.
                    dataSource(url, user, password)
                    .locations(locations);

            migrateResult = configuration.load().migrate();
            return this;
        }

        public H2DBRepository build() throws SQLException {
            return new H2DBRepository(connection, migrateResult);
        }
    }

    public H2DBRepository(Connection connection, MigrateResult migrateResult) {
        this.migrateResult = migrateResult;
        this.connection = connection;
        this.clickRepository = new ClickRepository(connection);
        this.profileRepository = new ProfileRepository(connection);
        this.loginRepository = new LoginRepository(connection);
        this.settingRepository = new SettingRepository(connection);
        this.sessionRepository = new SessionRepository(connection);
        this.historyRepository = new HistoryRepository(connection);
        this.constantRepository = new ConstantRepository(connection);
    }


}
