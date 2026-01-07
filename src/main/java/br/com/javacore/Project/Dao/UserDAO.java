package br.com.javacore.Project.Dao;

import br.com.javacore.Project.Connection.ConnectionFactory;
import br.com.javacore.Project.Exception.BusinessException;
import br.com.javacore.Project.Exception.DataAccessException;
import br.com.javacore.Project.Model.User;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Log4j2
public class UserDAO {
    public static void insertUser(Connection conn, User user){
        String sql = "INSERT INTO users (id, name, age, email) VALUES (?, ?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, user.id());
            pstmt.setString(2, user.name());
            pstmt.setInt(3, user.age());
            pstmt.setString(4, user.email());

            pstmt.executeUpdate();
        } catch (SQLException e){

            if ("23000".equals(e.getSQLState())){
                throw new BusinessException("Email já cadastrado.");
            }

            log.error("Erro ao inserir usuário.", e);
            throw new DataAccessException("Erro ao inserir usuário.");
        }
    }

    public static List<User> findAllUsers(){
        String sql = "SELECT * FROM users WHERE active = 1;";
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){

            while (rs.next()){
                users.add(new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getBoolean("active"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }

            if (users.isEmpty()){
                throw new BusinessException("Nenhum usuário foi encontrado.");
            }
        } catch (SQLException e){
            log.error("Erro ao buscar usuários.", e);
            throw new DataAccessException("Erro ao buscar usuários.");
        }
        return users;
    }

    public static void upateActiverStatusUser(Connection conn, String id, boolean active){
        String sql = "UPDATE users SET active = ? WHERE id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setBoolean(1, active);
            pstmt.setString(2, id);

            int rows = pstmt.executeUpdate();

            if (rows == 0){
                throw new BusinessException("Nenhum usuário encontrado com o ID fornecido.");
            }
        } catch (SQLException e){
            log.error("Erro ao atualizar status do usuário.", e);
            throw new DataAccessException("Erro ao atualizar status do usuário.");
        }
    }

    public static void updateUser(Connection conn, String id, User user){
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<Object> params = new ArrayList<>();

        if (user.name() != null){
            sql.append("name = ?, ");
            params.add(user.name());
        }

        if (user.age() > 0){
            sql.append("age = ?, ");
            params.add(user.age());
        }

        if (user.email() != null){
            sql.append("email = ?, ");
            params.add(user.email());
        }

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE id = ? AND active = 1;");
        params.add(id);

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())){

            for (int i = 0; i < params.size(); i++){
                pstmt.setObject(i + 1, params.get(i));
            }

            int rows = pstmt.executeUpdate();

            if (rows == 0){
                throw new BusinessException("Nenhum usuário encontrado com o ID fornecido.");
            }
        } catch (SQLException e){
            log.error("Erro ao atualizar informações do usuário.", e);
            throw new DataAccessException("Erro ao atualizar informações do usuário.");
        }
    }

    public static User findById(Connection conn, String id){
        String sql = "SELECT * FROM users WHERE id = ?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()){
                return null;
            }

            return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    rs.getBoolean("active"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        } catch (SQLException e){
            log.error("Erro ao buscar usuário.", e);
            throw new DataAccessException("Erro ao buscar usuário");
        }
    }
}
