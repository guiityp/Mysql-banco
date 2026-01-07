package br.com.javacore.Project.Service;

import br.com.javacore.Project.Connection.ConnectionFactory;
import br.com.javacore.Project.Dao.UserDAO;
import br.com.javacore.Project.Domain.Validator.*;
import br.com.javacore.Project.Exception.BusinessException;
import br.com.javacore.Project.Model.User;
import br.com.javacore.Project.Utils.TransactionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserService {
    public static void insertUser(User user){
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            UserDAO.insertUser(conn, validateAndNormalizeForInsert(user));

            conn.commit();
        } catch (Exception e){
            throw TransactionUtils.rollbackAndThrow(
                    conn,
                    e,
                    new BusinessException("Erro ao salvar usuário.")
            );
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {}
        }
    }

    public static List<User> findAllUsers(){
        return UserDAO.findAllUsers();
    }

    public static void activeUser(String id){
        validateId(id);

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            User user = UserDAO.findById(conn, id);

            if (user == null){
                throw new BusinessException("Usuário não encontrado.");
            }

            if (Boolean.TRUE.equals(user.active())){
                throw new BusinessException("Usuário já esta ativo.");
            }

            UserDAO.upateActiverStatusUser(conn, id, true);

            conn.commit();
        } catch (Exception e){
            throw TransactionUtils.rollbackAndThrow(
                    conn,
                    e,
                    new BusinessException("Erro ao ativar usuário.")
            );
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void disableUser(String id){
        validateId(id);

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            User user = UserDAO.findById(conn, id);

            if (user == null){
                throw new BusinessException("Usuário não encontrado.");
            }

            if (Boolean.FALSE.equals(user.active())){
                throw new BusinessException("Usuário já esta desativado.");
            }


            UserDAO.upateActiverStatusUser(conn, id, false);

            conn.commit();
        } catch (Exception e){
            throw TransactionUtils.rollbackAndThrow(
                    conn,
                    e,
                    new BusinessException("Erro ao desativar usuário.")
            );
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {}
        }
    }

    public static void updateUser(String id, User user){
        validateId(id);

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            UserDAO.updateUser(conn, id, validateAndNormalizeForUpdate(user));

            conn.commit();
        } catch (Exception e){
            throw TransactionUtils.rollbackAndThrow(
                    conn,
                    e,
                    new BusinessException("Erro ao salvar alterações do usuário.")
            );
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {}
        }
    }

    private static User validateAndNormalizeForInsert(User user){
        if (user == null){
            throw new BusinessException("Usuário não pode ser nulo.");
        }

        NameValidator.validate(user.name());
        AgeValidator.validate(user.age());
        EmailValidator.validate(user.email());

        return new User(
                UUID.randomUUID().toString(),
                NameNormalize.normalize(user.name()),
                user.age(),
                EmailNormalize.normalize(user.email()),
                null,
                null
        );
    }

    private static User validateAndNormalizeForUpdate(User user){
        if (user == null){
            throw new BusinessException("Usuário não pode ser nulo.");
        }

        if (user.name() == null && user.age() <= 0 && user.email() == null){
            throw new BusinessException("Preencha ao menos um dos campo para atualização.");
        }

        String name = user.name();
        int age = user.age();
        String email = user.email();

        if (name != null){
            NameValidator.validate(name);
            NameNormalize.normalize(name);
        }

        if (age > 0){
            AgeValidator.validate(age);
        }

        if (email != null){
            EmailValidator.validate(email);
            EmailNormalize.normalize(email);
        }

        return new User(
                null,
                name,
                age,
                email,
                null,
                null
        );
    }

    private static void validateId(String id){
        if (id == null || id.isBlank()){
            throw new BusinessException("ID é obrigatório.");
        }

        try {
            UUID.fromString(id.trim());
        } catch (IllegalArgumentException e){
            throw new BusinessException("Id inválido.");
        }
    }
}