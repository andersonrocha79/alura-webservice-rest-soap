package br.com.alura.loja;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

// JAX-RS

public class Servidor
{

    public static void main(String[] args) throws IOException
    {

        HttpServer server = iniciaServidor();
        System.out.println("Servidor rodando na porta 8080");
        System.in.read();
        server.stop();

    }

    static HttpServer iniciaServidor()
    {
        URI uri = URI.create("http://localhost:8080/");
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        return server;
    }

}
