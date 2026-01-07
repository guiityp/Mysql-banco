package br.com.javacore.Project.Domain.Validator;

public class NameNormalize {
    public static String normalize(String name){
        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder normalized = new StringBuilder();

        for (String part : parts){
            if (part.isBlank()) continue;

            normalized
                    .append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }

        return normalized.toString().trim();
    }
}
