package br.com.caelum.estoque.ws;

import javax.xml.ws.Endpoint;

public class PublicaWebService
{

    // http://localhost:8080/estoquews?wsdl

    // testar com o programa 'SoapUI'

    public static void main(String[] args)
    {

        EstoqueWS service = new EstoqueWS();
        String url = "http://localhost:8080/estoquews";

        System.out.println("Servidor rodando: " + url + "?wsdl");

        Endpoint.publish(url, service);

    }

}
