package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientTest
{

    private HttpServer  server;

    private Client      client;
    private WebTarget   target;

    @Before
    public void inicia()
    {

        server      = Servidor.iniciaServidor();

        ClientConfig config = new ClientConfig();
        config.register(new LoggingFilter());

        this.client = ClientBuilder.newClient(config);

        this.target = client.target("http://localhost:8080");

    }

    @After
    public void finaliza()
    {
        server.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado()
    {


       /*
        String conteudo = target.path("/carrinhos/1").request().get(String.class);
        System.out.println(conteudo);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
        Assert.assertEquals(1l, carrinho.getId());
        */
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class); // utiliza JAXB
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
        Assert.assertEquals(1l, carrinho.getId());

    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado()
    {

        /*
        String conteudo = target.path("/projetos/1").request().get(String.class);
        System.out.println(conteudo);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals("Minha loja", projeto.getNome());
        Assert.assertEquals(2014, projeto.getAnoDeInicio());
         */

        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals("Minha loja", projeto.getNome());
        Assert.assertEquals(2014, projeto.getAnoDeInicio());

    }

    @Test
    public void testaSucessoInclusaoNovoCarrinho()
    {

        // simula um novo carrinho
        Carrinho carrinho = new Carrinho();
        String novoProduto = "Tablet Nova Geracao 7 polegadas LG";
        carrinho.adiciona(new Produto(10L, novoProduto, 20, 3));
        carrinho.setRua("Rua Joao Barbosa");
        carrinho.setCidade("Pedro Leopoldo");

        // convedrte o carrinho para xml
        // String xml = carrinho.toXML();
        // Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);

        // faz a chamada do método 'post' do servidor
        Response response = target.path("/carrinhos").request().post(entity);

        // verifica se conseguiu incluir
        Assert.assertEquals(response.getStatus(), 201);

        // verifica se o Location do novo recurso retorna o novo carrinho registrado
        String location = response.getHeaderString("Location");
        //String conteudo = client.target(location).request().get(String.class);
        Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class);
        Assert.assertTrue(carrinhoCarregado.getProdutos().get(0).getNome().contains(novoProduto));

        // Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));

    }

    @Test
    public void testaSucessoInclusaoNovoProjeto()
    {

        Projeto projeto = new Projeto(20l, "novo projeto", 2021);
        // String xml = projeto.toXML();
        // Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Entity<Projeto> entity = Entity.entity(projeto, MediaType.APPLICATION_XML);

        Response response = target.path("/projetos").request().post(entity);

        // verifica se conseguiu incluir
        Assert.assertEquals(response.getStatus(), 201);

        // verifica se o Location do novo recurso retorna o novo carrinho registrado
        String location = response.getHeaderString("Location");
        Projeto projetoIncluido = client.target(location).request().get(Projeto.class);
        Assert.assertTrue(projetoIncluido.getNome().equals("novo projeto"));

        // Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));

    }

}
