package br.com.javacore.Project.Domain.Validator;

import br.com.javacore.Project.Exception.BusinessException;

public class NameValidator {
    private static final String NAME_REGEX = "^[\\p{L} ]+$";

    public static void validate(String name){
        if (name == null || name.isBlank()){
            throw new BusinessException("Nome é obrigatório.");
        }

        if (!name.matches(NAME_REGEX)){
            throw new BusinessException("Nome não pode conter números ou caracteres especiais.");
        }
    }
}
