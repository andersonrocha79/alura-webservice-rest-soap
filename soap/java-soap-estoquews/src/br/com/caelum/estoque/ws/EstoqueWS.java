package br.com.caelum.estoque.ws;

import br.com.caelum.estoque.modelo.item.*;
import br.com.caelum.estoque.modelo.usuario.AuthorizationException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

// DOCUMENT:    envia detalhes do tipo em outro arquivo
// RPC:         projetado para executar um método remoto, tem detalhes do parâmetro de entrada e saída

// DOCUMENT/LITEARL/WRAPPED é uma alternativa ao modelo RPC porque empacota o registro com o nome do método a ser chamado no servidor

// utilizar sempre LITERA, ENCODED não funciona mais

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,
             use = SOAPBinding.Use.LITERAL,
             parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class EstoqueWS
{

    private ItemDao dao = new ItemDao();

    @WebMethod(operationName = "TodosOsItens")
    @ResponseWrapper(localName="itens")
    @WebResult(name = "item")
    @RequestWrapper(localName="listaItens")
    public List<Item> getItens(@WebParam(name="filtros") Filtros filtros)
    {
        System.out.println("Chamando getItens()");
        List<Filtro> lista = filtros.getLista();
        List<Item> itensResultado = dao.todosItens(lista);
        return itensResultado;
    }

    @WebMethod(operationName = "CadastrarItem", action = "CadastrarItem")
    @WebResult(name = "item")
    public Item cadastrarItem(@WebParam(name="token", header = true) TokenUsuario token,
                              @WebParam(name="item")  Item item) throws AuthorizationException
    {

        System.out.println("Chamando cadastrarItem() " + item + ", token: " + token );

        boolean valido = new TokenDao().ehValido(token);

        if (!valido)
        {
            throw new AuthorizationException("Autorização falhou.");
        }

        new ItemValidador(item).validate();

        this.dao.cadastrar(item);

        return item;

    }

}
