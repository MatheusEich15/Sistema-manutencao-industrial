package br.com.manutencaoIndustrial.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3356/manutencao_industrial?useSSL=false&serverTimezone=America/Sao_Paulo";

    private static final String USER = "root";

    private static final String PASSWORD = "mysqlPW";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
