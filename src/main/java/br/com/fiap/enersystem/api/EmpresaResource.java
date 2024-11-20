package br.com.fiap.enersystem.api;

import br.com.fiap.enersystem.dao.EmpresaDAO;
import br.com.fiap.enersystem.dao.LoginDAO;
import br.com.fiap.enersystem.model.Empresa;
import br.com.fiap.enersystem.model.EmpresaLoginWrapper;
import br.com.fiap.enersystem.model.Login;
import br.com.fiap.enersystem.util.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;



@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaResource {

    @POST
    public Response create(EmpresaLoginWrapper wrapper) {
        Logger logger = Logger.getLogger(EmpresaResource.class.getName());

        Empresa empresa = wrapper.getEmpresa();
        Login login = wrapper.getLogin();

        if (empresa == null || empresa.getNome() == null || empresa.getCnpj() == null || empresa.getEmail() == null || login == null || login.getSenha() == null) {
            logger.warning("Dados inválidos recebidos no request: " + wrapper);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dados de Empresa e Login são obrigatórios").build();
        }

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            logger.info("Criando empresa: " + empresa);
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            Empresa createdEmpresa = empresaDAO.create(empresa);
            logger.info("Empresa criada com sucesso: " + createdEmpresa);

            login.setCnpj(empresa.getCnpj());
            login.setStatus("PENDENTE");
            login.setEmpresa(createdEmpresa);

            logger.info("Criando login associado: " + login);
            LoginDAO loginDAO = new LoginDAO(connection);
            Login createdLogin = loginDAO.create(login);
            logger.info("Login criado com sucesso: " + createdLogin);

            connection.commit();
            logger.info("Transação concluída com sucesso");

            return Response.status(Response.Status.CREATED).entity(createdEmpresa).build();

        } catch (SQLException e) {
            logger.severe("Erro durante a transação: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                    logger.info("Transação revertida com sucesso");
                } catch (SQLException rollbackEx) {
                    logger.severe("Erro ao reverter transação: " + rollbackEx.getMessage());
                }
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar empresa e login").build();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.info("Conexão com o banco de dados encerrada");
                } catch (SQLException closeEx) {
                    logger.severe("Erro ao fechar conexão: " + closeEx.getMessage());
                }
            }
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            Empresa empresa = empresaDAO.findById(id);
            if (empresa == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada").build();
            }
            return Response.ok(empresa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar empresa").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, Empresa empresa) {
        if (empresa.getNome() == null || empresa.getEmail() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Nome e Email são obrigatórios").build();
        }
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            Empresa existing = empresaDAO.findById(id);
            if (existing == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada").build();
            }

            // Configurando o ID e garantindo que o CNPJ não será atualizado
            empresa.setId(id);
            empresa.setCnpj(existing.getCnpj()); // Certificando que o CNPJ permanece o mesmo

            empresaDAO.update(empresa);
            return Response.ok(empresa).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar empresa").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            Empresa existing = empresaDAO.findById(id);
            if (existing == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Empresa não encontrada").build();
            }
            empresaDAO.delete(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar empresa").build();
        }
    }

    @GET
    public Response findAll() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            List<Empresa> empresas = empresaDAO.findAll();
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar empresas").build();
        }
    }


    @GET
    @Path("/pendentes")
    public Response findPendentes() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            List<Empresa> empresasPendentes = empresaDAO.findPendentes();
            return Response.ok(empresasPendentes).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar empresas pendentes").build();
        }
    }

    @GET
    @Path("/estado/{estado}")
    public Response findByEstado(@PathParam("estado") String estado) {
        System.out.println("Debug: estado recebido no recurso: '" + estado + "'");
        try (Connection connection = DatabaseConnection.getConnection()) {
            EmpresaDAO empresaDAO = new EmpresaDAO(connection);
            List<Empresa> empresas = empresaDAO.findByEstado(estado);
            System.out.println("Debug: lista de empresas retornada2: " + empresas);
            return Response.ok(empresas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar empresas por estado").build();
        }
    }
}



