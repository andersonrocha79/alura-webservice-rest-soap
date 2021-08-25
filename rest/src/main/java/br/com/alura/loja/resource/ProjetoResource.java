package br.com.alura.loja.resource;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("projetos")
public class ProjetoResource
{

    // curl -v http://localhost:8080/projetos/5
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Projeto busca(@PathParam("id") Long id)
    {
        ProjetoDAO dao = new ProjetoDAO();
        Projeto projeto = dao.busca(id);
        return projeto;
    }

    /*
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") Long id)
    {
        ProjetoDAO dao = new ProjetoDAO();
        Projeto projeto = dao.busca(id);
        return projeto.toJson();
    }
     */

    // curl -v -d "<br.com.alura.loja.modelo.Projeto><nome>teste2</nome><id>5</id><anoDeInicio>2021</anoDeInicio></br.com.alura.loja.modelo.Projeto>" http://localhost:8080/projetos
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(Projeto newProject)
    {
        //Projeto newProject = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDAO().adiciona(newProject);
        URI uri = URI.create("/projetos/" + newProject.getId());
        return Response.created(uri).build();
        //return "<status>sucesso</status>";
    }

    @DELETE
    @Path("{id}")
    public Response removeProjeto(@PathParam("id") Long id)
    {
        new ProjetoDAO().remove(id);
        return Response.ok().build();

    }

}
