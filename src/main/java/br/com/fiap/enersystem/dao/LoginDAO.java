package br.com.fiap.enersystem.dao;

import br.com.fiap.enersystem.model.Empresa;
import br.com.fiap.enersystem.model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

    private Connection connection;

    public LoginDAO(Connection connection) {
        this.connection = connection;
    }

    public Login create(Login login) throws SQLException {
        String sql = "INSERT INTO TB_ENERGSYSTEM_LOGIN (CNPJ, SENHA, ID_EMPRESA, STATUS) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID_LOGIN"})) {
            stmt.setString(1, login.getCnpj());
            stmt.setString(2, login.getSenha());
            stmt.setLong(3, login.getEmpresa().getId());
            stmt.setString(4, login.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    login.setId(generatedKeys.getInt(1));
                }
            }
        }

        System.out.println("Inserindo login com os seguintes dados:");
        System.out.println("ID: " + login.getId());
        System.out.println("CNPJ: " + login.getCnpj());
        System.out.println("Senha: " + login.getSenha());
        System.out.println("ID Empresa: " + login.getEmpresa().getId());
        System.out.println("Status: " + login.getStatus());

        return login;
    }

    public Login findByCnpj(String cnpj) throws SQLException {
        String sql = "SELECT l.ID_LOGIN, l.CNPJ, l.SENHA, l.STATUS, " +
                "e.ID_EMPRESA, e.NOME, e.CNPJ AS EMPRESA_CNPJ, e.EMAIL, e.ESTADO, e.KWH, e.TIPO_ENERGIA " +
                "FROM TB_ENERGSYSTEM_LOGIN l " +
                "JOIN TB_ENERGSYSTEM_EMPRESA e ON l.ID_EMPRESA = e.ID_EMPRESA " +
                "WHERE l.CNPJ = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(rs.getLong("ID_EMPRESA"));
                    empresa.setNome(rs.getString("NOME"));
                    empresa.setCnpj(rs.getString("EMPRESA_CNPJ"));
                    empresa.setEmail(rs.getString("EMAIL"));
                    empresa.setEstado(rs.getString("ESTADO"));
                    empresa.setKwh(rs.getInt("KWH"));
                    empresa.setTipoEnergia(rs.getString("TIPO_ENERGIA"));

                    Login login = new Login();
                    login.setId(rs.getLong("ID_LOGIN"));
                    login.setCnpj(rs.getString("CNPJ"));
                    login.setSenha(rs.getString("SENHA"));
                    login.setStatus(rs.getString("STATUS"));
                    login.setEmpresa(empresa);

                    return login;
                }
            }
        }
        return null;
    }


    public Login findById(long id) throws SQLException {
        String sql = "SELECT l.ID_LOGIN, l.CNPJ, l.SENHA, l.STATUS, " +
                "e.ID_EMPRESA, e.NOME, e.CNPJ AS EMPRESA_CNPJ, e.EMAIL, e.ESTADO, e.KWH, e.TIPO_ENERGIA " +
                "FROM TB_ENERGSYSTEM_LOGIN l " +
                "JOIN TB_ENERGSYSTEM_EMPRESA e ON l.ID_EMPRESA = e.ID_EMPRESA " +
                "WHERE l.ID_LOGIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setId(rs.getLong("ID_EMPRESA"));
                    empresa.setNome(rs.getString("NOME"));
                    empresa.setCnpj(rs.getString("EMPRESA_CNPJ"));
                    empresa.setEmail(rs.getString("EMAIL"));
                    empresa.setEstado(rs.getString("ESTADO"));
                    empresa.setKwh(rs.getInt("KWH"));
                    empresa.setTipoEnergia(rs.getString("TIPO_ENERGIA"));

                    Login login = new Login();
                    login.setId(rs.getLong("ID_LOGIN"));
                    login.setCnpj(rs.getString("CNPJ"));
                    login.setSenha(rs.getString("SENHA"));
                    login.setStatus(rs.getString("STATUS"));
                    login.setEmpresa(empresa);

                    return login;
                }
            }
        }
        return null;
    }

    public void update(Login login) throws SQLException {
        String sql = "UPDATE TB_ENERGSYSTEM_LOGIN SET CNPJ = ?, SENHA = ?, ID_EMPRESA = ?, STATUS = ? WHERE ID_LOGIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login.getCnpj());
            stmt.setString(2, login.getSenha());
            stmt.setLong(3, login.getEmpresa().getId());
            stmt.setString(4, login.getStatus());
            stmt.setLong(5, login.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM TB_ENERGSYSTEM_LOGIN WHERE ID_LOGIN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Login> findAll() throws SQLException {
        String sql = "SELECT l.ID_LOGIN, l.CNPJ, l.SENHA, l.STATUS, " +
                "e.ID_EMPRESA, e.NOME, e.CNPJ AS EMPRESA_CNPJ, e.EMAIL, e.ESTADO, e.KWH, e.TIPO_ENERGIA " +
                "FROM TB_ENERGSYSTEM_LOGIN l " +
                "JOIN TB_ENERGSYSTEM_EMPRESA e ON l.ID_EMPRESA = e.ID_EMPRESA";
        List<Login> logins = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getLong("ID_EMPRESA"));
                empresa.setNome(rs.getString("NOME"));
                empresa.setCnpj(rs.getString("EMPRESA_CNPJ"));
                empresa.setEmail(rs.getString("EMAIL"));
                empresa.setEstado(rs.getString("ESTADO"));
                empresa.setKwh(rs.getInt("KWH"));
                empresa.setTipoEnergia(rs.getString("TIPO_ENERGIA"));

                Login login = new Login();
                login.setId(rs.getLong("ID_LOGIN"));
                login.setCnpj(rs.getString("CNPJ"));
                login.setSenha(rs.getString("SENHA"));
                login.setStatus(rs.getString("STATUS"));
                login.setEmpresa(empresa);

                logins.add(login);
            }
        }
        return logins;
    }
}
