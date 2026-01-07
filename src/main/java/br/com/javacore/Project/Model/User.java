package br.com.javacore.Project.Model;

import java.time.LocalDateTime;

public record User(
        String id,
        String name,
        int age,
        String email,
        Boolean active,
        LocalDateTime created_at
) {
}
