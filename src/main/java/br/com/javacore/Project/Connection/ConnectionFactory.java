package br.com.javacore.Project.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final HikariDataSource DS;

    static {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASSWORD"));

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        DS = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return DS.getConnection();
    }
}