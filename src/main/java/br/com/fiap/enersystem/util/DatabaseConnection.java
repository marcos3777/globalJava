package br.com.fiap.enersystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        //Variaveis de ambiente configuradas no windows, irei deixar os valores no bloco de notas e adicionar no zip.
        String usuario = System.getenv("USER_ORACLE");
        String senha = System.getenv("PASSWORD_ORACLE");
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";


        System.out.println("USER_ORACLE: " + usuario);
        System.out.println("PASSWORD_ORACLE: " + senha);


        return DriverManager.getConnection(url, usuario, senha);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Conexão estabelecida com sucesso!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}