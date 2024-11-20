package br.com.fiap.enersystem.dao;

import br.com.fiap.enersystem.model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    private Connection connection;

    public EmpresaDAO(Connection connection) {
        this.connection = connection;
    }

    public Empresa create(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO TB_ENERGSYSTEM_EMPRESA (NOME, CNPJ, EMAIL, ESTADO, KWH, TIPO_ENERGIA) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID_EMPRESA"})) {
            System.out.println("Dados enviados para o banco:");
            System.out.println("Nome: " + empresa.getNome());
            System.out.println("CNPJ: " + empresa.getCnpj());
            System.out.println("Email: " + empresa.getEmail());
            System.out.println("Estado: " + empresa.getEstado());
            System.out.println("KWH: " + empresa.getKwh());
            System.out.println("Tipo de Energia: " + empresa.getTipoEnergia());

            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getEmail());
            stmt.setString(4, empresa.getEstado());
            stmt.setInt(5, empresa.getKwh());
            stmt.setString(6, empresa.getTipoEnergia());

            System.out.println("Executando a query...");
            stmt.executeUpdate();
            System.out.println("teste se passou");
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    empresa.setId(generatedKeys.getInt(1));
                    System.out.println("ID gerado para a empresa: " + empresa.getId());
                } else {
                    System.err.println("Nenhuma chave gerada para a empresa!");
                    throw new SQLException("Falha ao recuperar o ID gerado para a empresa.");
                }
            }
        }
        System.out.println("Empresa criada com sucesso: " + empresa);
        return empresa;
    }


    public Empresa findById(long id) throws SQLException {
        String sql = "SELECT * FROM TB_ENERGSYSTEM_EMPRESA WHERE ID_EMPRESA = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Empresa(
                            rs.getLong("ID_EMPRESA"),
                            rs.getString("NOME"),
                            rs.getString("CNPJ"),
                            rs.getString("EMAIL"),
                            rs.getString("ESTADO"),
                            rs.getInt("KWH"),
                            rs.getString("TIPO_ENERGIA")
                    );
                }
            }
        }
        return null;
    }

    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE TB_ENERGSYSTEM_EMPRESA SET NOME = ?, EMAIL = ?, ESTADO = ?, KWH = ?, TIPO_ENERGIA = ? WHERE ID_EMPRESA = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getEmail());
            stmt.setString(3, empresa.getEstado());
            stmt.setInt(4, empresa.getKwh());
            stmt.setString(5, empresa.getTipoEnergia());
            stmt.setLong(6, empresa.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM TB_ENERGSYSTEM_EMPRESA WHERE ID_EMPRESA = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Empresa> findAll() throws SQLException {
        String sql = "SELECT * FROM TB_ENERGSYSTEM_EMPRESA";
        List<Empresa> empresas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                empresas.add(new Empresa(
                        rs.getLong("ID_EMPRESA"),
                        rs.getString("NOME"),
                        rs.getString("CNPJ"),
                        rs.getString("EMAIL"),
                        rs.getString("ESTADO"),
                        rs.getInt("KWH"),
                        rs.getString("TIPO_ENERGIA")
                ));
            }
        }
        return empresas;
    }

    public List<Empresa> findPendentes() throws SQLException {
        String sql = "SELECT e.ID_EMPRESA, e.NOME, e.CNPJ, e.EMAIL, e.ESTADO, e.KWH, e.TIPO_ENERGIA " +
                "FROM TB_ENERGSYSTEM_EMPRESA e " +
                "JOIN TB_ENERGSYSTEM_LOGIN l ON e.ID_EMPRESA = l.ID_EMPRESA " +
                "WHERE l.STATUS = 'PENDENTE'";
        List<Empresa> empresasPendentes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empresa empresa = new Empresa(
                        rs.getLong("ID_EMPRESA"),
                        rs.getString("NOME"),
                        rs.getString("CNPJ"),
                        rs.getString("EMAIL"),
                        rs.getString("ESTADO"),
                        rs.getInt("KWH"),
                        rs.getString("TIPO_ENERGIA")
                );
                empresasPendentes.add(empresa);
            }
        }
        return empresasPendentes;
    }

    public List<Empresa> findByEstado(String estado) throws SQLException {
        System.out.println("Debug: estado recebido no DAO: '" + estado + "'");
        String sql = "SELECT e.ID_EMPRESA, e.NOME, e.CNPJ, e.EMAIL, e.ESTADO, e.KWH, e.TIPO_ENERGIA " +
                "FROM TB_ENERGSYSTEM_EMPRESA e " +
                "JOIN TB_ENERGSYSTEM_LOGIN l ON e.ID_EMPRESA = l.ID_EMPRESA " +
                "WHERE UPPER(e.ESTADO) = ? AND l.STATUS = 'OK'";
        List<Empresa> empresas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String estadoParam = estado.trim().toUpperCase();
            stmt.setString(1, estadoParam);
            System.out.println("Debug: valor do parâmetro estado atribuído: '" + estadoParam + "'");
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Debug: SQL sendo executado1: " + sql);
                while (rs.next()) {
                    Empresa empresa = new Empresa(
                            rs.getLong("ID_EMPRESA"),
                            rs.getString("NOME"),
                            rs.getString("CNPJ"),
                            rs.getString("EMAIL"),
                            rs.getString("ESTADO"),
                            rs.getInt("KWH"),
                            rs.getString("TIPO_ENERGIA")

                    );
                    System.out.println("Debug: SQL sendo executado2: " + sql);
                    empresas.add(empresa);
                }
            }
        }
        System.out.println("Debug: SQL sendo executado3: " + sql);
        System.out.println("Debug: lista de empresas encontrada: " + empresas);
        return empresas;
    }


}



