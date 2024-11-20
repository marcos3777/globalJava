package br.com.fiap.enersystem.api;

import br.com.fiap.enersystem.dao.LoginDAO;
import br.com.fiap.enersystem.model.Login;
import br.com.fiap.enersystem.util.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger logger = Logger.getLogger(LoginResource.class.getName());

    @POST
    public Response authenticate(Login credentials) {
        if (credentials == null || credentials.getCnpj() == null || credentials.getSenha() == null) {
            logger.warning("Credenciais inválidas recebidas no request");
            return Response.status(Response.Status.BAD_REQUEST).entity("CNPJ e senha são obrigatórios").build();
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            LoginDAO loginDAO = new LoginDAO(connection);
            Login login = loginDAO.findByCnpj(credentials.getCnpj());

            if (login == null) {
                logger.info("CNPJ não encontrado: " + credentials.getCnpj());
                return Response.status(Response.Status.UNAUTHORIZED).entity("CNPJ ou senha inválidos").build();
            }

            // Verifica se a senha fornecida corresponde à senha armazenada
            if (!login.getSenha().equals(credentials.getSenha())) {
                logger.info("Senha inválida para o CNPJ: " + credentials.getCnpj());
                return Response.status(Response.Status.UNAUTHORIZED).entity("CNPJ ou senha inválidos").build();
            }

            // Verifica o status do login
            if (!"OK".equalsIgnoreCase(login.getStatus())) {
                logger.info("Login inativo ou pendente para o CNPJ: " + credentials.getCnpj());
                return Response.status(Response.Status.FORBIDDEN).entity("Login não está ativo").build();
            }

            // Autenticação bem-sucedida
            logger.info("Autenticação bem-sucedida para o CNPJ: " + credentials.getCnpj());
            return Response.ok(login).build();

        } catch (SQLException e) {
            logger.severe("Erro ao autenticar: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao autenticar").build();
        }
    }
}
