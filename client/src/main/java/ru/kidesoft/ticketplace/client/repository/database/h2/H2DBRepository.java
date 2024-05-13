package ru.kidesoft.ticketplace.client.repository.database.h2;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DBRepository {

    private Connection connection;

    public static H2DBRepositoryBuilder builder() {
        return new H2DBRepositoryBuilder();
    }
    public static class H2DBRepositoryBuilder {
        private String host;
        private String user;
        private String password;
        private String databaseUrl;

        private String connectionString;

        public H2DBRepositoryBuilder create(String connection) {
            this.connectionString = connection;
            return this;
        }

        public H2DBRepositoryBuilder migrate() {
            Flyway flyway = Flyway.configure().load();
            System.out.println(flyway.getConfiguration());
            return this;
        }

        public H2DBRepository build() throws SQLException {
            Connection connection = DriverManager.getConnection(connectionString);
            return new H2DBRepository(connection);
        }
    }

    public H2DBRepository(Connection connection) {
        this.connection = connection;
    }


}
