package br.com.javacore.Project.Domain.Validator;

public class EmailNormalize {
    public static String normalize(String email){
        return email.trim().toLowerCase();
    }
}
