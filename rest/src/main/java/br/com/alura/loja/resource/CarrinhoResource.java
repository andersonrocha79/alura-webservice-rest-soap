package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("carrinhos")
public class CarrinhoResource
{

    //  curl --help

    // retornos http
    // https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html

    // curl -v http://localhost:8080/carrinhos/1
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Carrinho busca(@PathParam("id") Long id)
    {
        CarrinhoDAO dao = new CarrinhoDAO();
        Carrinho carrinho = dao.busca(id);
        //return carrinho.toXML();
        return carrinho;
    }

    /*
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") Long id)
    {
        CarrinhoDAO dao = new CarrinhoDAO();
        Carrinho carrinho = dao.busca(id);
        return carrinho.toJson();
    }
     */

    /*
    curl -v -H "Content-type: application/xml" -d "<br.com.alura.loja.modelo.Carrinho><produtos><br.com.alura.loja.modelo.Produto><preco>4000.0</preco><id>6237</id><nome>Videogame 4<
    /nome><quantidade>1</quantidade></br.com.alura.loja.modelo.Produto></produtos><rua>Rua Vergueiro 3185, 8 andar</rua><cidade>São Paulo</cidade><id>1</id></br.com.alura.loja.modelo.Carrinho>" http://localhost:8080/carrinhos
    */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(Carrinho carrinho)
    {
        // Carrinho newCar = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDAO().adiciona(carrinho);
        URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
        //return "<status>sucesso</status>";
    }

    // curl -v -X "DELETE" http://localhost:8080/carrinhos/1/produtos/6237
    @Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") Long id, @PathParam("produtoId") Long produtoId)
    {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.remove(produtoId);
        return Response.ok().build();
    }

    /*
    curl -v -X "PUT" -d "<br.com.alura.loja.modelo.Produto><preco>4000.0</preco><id>6237</id><nome>Videogame 4</nome><quantidade>4</quantidade></br.com.alura.loja.modelo.Produto>" http://localhost:8080/carrinhos/1/produtos/3467
    curl -v -X PUT -H "Content-Type: application/xml" -d "<br.com.alura.loja.modelo.Produto>      <preco>60.0</preco>      <id>3467</id>      <nome>Jogo de esporte</nome>      <quantidade>1</quantidade> </br.com.alura.loja.modelo.Produto>" http://localhost:8080/carrinhos/1/produtos/3467
     */
    @Path("{id}/produtos/{produtoId}")
    @PUT
    public Response alteraProduto(@PathParam("id") Long id,
                                  @PathParam("produtoId") Long produtoId,
                                  Produto produto)
    {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        // Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.troca(produto);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    public Response alteraProdutoQuantidade(@PathParam("id") Long id,
                                            @PathParam("produtoId") Long produtoId,
                                            Produto produto)
    {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        // Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }

}
