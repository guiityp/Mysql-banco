package br.com.javacore.Project.Domain.Validator;

import br.com.javacore.Project.Exception.BusinessException;

public class EmailValidator {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static void validate(String email){
        if (email == null || email.isBlank()){
            throw new BusinessException("Email é obrigatório.");
        }

        if (!email.matches(EMAIL_REGEX)){
            throw new BusinessException("Email inválido.");
        }
    }
}
