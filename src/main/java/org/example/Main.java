package org.example;

import br.com.javacore.Project.Config.DatabaseMigration;
import br.com.javacore.Project.Connection.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Log4j2
public class Main {
    public static void main(String[] args) {

        DatabaseMigration.migrate();

        try (Connection conn = ConnectionFactory.getConnection()) {

            log.info("Conexão com o banco de dados estabelecida com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}