package br.com.fiap.enersystem;

import br.com.fiap.enersystem.model.CORSFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/api/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .packages("br.com.fiap.enersystem.api")
                .register(CORSFilter.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("API disponível em: " + BASE_URI);
        System.out.println("Pressione Ctrl+C para parar o servidor...");
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        System.in.read();
    }
}
