package br.com.javacore.Project.Input;

import br.com.javacore.Project.Domain.Validator.AgeValidator;
import br.com.javacore.Project.Domain.Validator.EmailValidator;
import br.com.javacore.Project.Domain.Validator.NameValidator;
import br.com.javacore.Project.Exception.BusinessException;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler(Scanner scanner){
        this.scanner = scanner;
    }

    public String readName(){
        while (true){
            try {
                System.out.print("Digite o nome: ");
                String input = scanner.nextLine();

                NameValidator.validate(input);

                return input;
            } catch (BusinessException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public int readAge(){
        while (true){
            try {
                System.out.print("Digite a idade: ");
                String input = scanner.nextLine();

                int age = Integer.parseInt(input);

                AgeValidator.validate(age);
                return age;
            } catch (NumberFormatException e){
                System.out.println("Idade não pode conter letras ou caracteres especiais");
            } catch (BusinessException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String readEmail(){
        while (true){
            try {
                System.out.print("Digite o email: ");
                String input = scanner.nextLine();

                EmailValidator.validate(input);
                return input;
            } catch (BusinessException e){
                System.out.println(e.getMessage());
            }
        }
    }
}