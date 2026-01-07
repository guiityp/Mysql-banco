package org.example;

import br.com.javacore.Project.Config.DatabaseMigration;
import br.com.javacore.Project.Exception.BusinessException;
import br.com.javacore.Project.Input.UserInputHandler;
import br.com.javacore.Project.Model.User;
import br.com.javacore.Project.Service.UserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Log4j2
public class Main {
    public static void main(String[] args) {

        DatabaseMigration.migrate();

        Scanner scanner = new Scanner(System.in);

        UserInputHandler inputHandler = new UserInputHandler(scanner);

        System.out.println("===CADASTRO DE USUÁRIO===");

        String name = inputHandler.readName();
        int age = inputHandler.readAge();
        String email = inputHandler.readEmail();

        User user = new User(
                null,
                name,
                age,
                email,
                null
        );

        try {
            UserService.insertUser(user);
        } catch (BusinessException e){
            System.out.println(e.getMessage());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<User> users = UserService.findAllUsers();

        System.out.println("===LISTA DE USUÁRIOS===");

        for (User u : users){
            System.out.printf("Id: %s | Nome: %s | Idade: %d | Email: %s | Data de criação: %s%n",
                    u.id(),
                    u.name(),
                    u.age(),
                    u.email(),
                    u.created_at().format(formatter)
                    );
        }

        scanner.close();
    }
}