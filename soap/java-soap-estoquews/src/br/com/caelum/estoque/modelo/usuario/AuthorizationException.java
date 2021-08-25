package br.com.caelum.estoque.modelo.usuario;

import javax.xml.ws.WebFault;
import java.util.Date;

@WebFault(name = "AutorizacaoFault", messageName="AutorizacaoFault")
public class AuthorizationException extends Exception
{

    private static final long serialVersionUID = 1L;

    public AuthorizationException(String message)
    {
        super(message);
    }

    public InfoFault getFaultInfo()
    {
        return new InfoFault("Token invalido" , new Date());
    }

}
