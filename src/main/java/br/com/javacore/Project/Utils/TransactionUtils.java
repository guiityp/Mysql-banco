package br.com.javacore.Project.Utils;

import java.sql.Connection;

public class TransactionUtils {
    public static RuntimeException rollbackAndThrow(
            Connection conn,
            Exception e,
            RuntimeException fallBack
    ){
        try {
            if (conn != null) conn.rollback();
        } catch (Exception ignored){}

        if (e instanceof RuntimeException){
            return (RuntimeException) e;
        }

        return fallBack;
    }
}
