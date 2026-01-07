package br.com.javacore.Project.Config;

import org.flywaydb.core.Flyway;

public class DatabaseMigration {
    public static void migrate(){
        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getenv("DB_URL"),
                        System.getenv("DB_USER"),
                        System.getenv("DB_PASSWORD")
                ).load();

        flyway.migrate();
    }
}
