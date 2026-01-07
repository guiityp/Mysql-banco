package br.com.javacore.Project.Domain.Validator;

import br.com.javacore.Project.Exception.BusinessException;

public class AgeValidator {
    public static void validate(int age){
        if (age <= 0){
            throw new BusinessException("Idade inválida.");
        }

        if (age > 130){
            throw new BusinessException("Idade ultrapassa os limites permitidos.");
        }
    }
}
