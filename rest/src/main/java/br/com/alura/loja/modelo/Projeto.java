package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  // é um elemento válido do JAXB
@XmlAccessorType(XmlAccessType.FIELD) // todos os campos serão serializados
public class Projeto
{

    private String nome;
    private Long id;
    private int anoDeInicio;

    public Projeto(Long id, String nome, int anoDeInicio)
    {
        this.nome = nome;
        this.id = id;
        this.anoDeInicio = anoDeInicio;
    }

    public Projeto()
    {

    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public int getAnoDeInicio() {
        return anoDeInicio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toXML()
    {
        XStream xStream = new XStream();
        return xStream.toXML(this);
    }

    public String toJson()
    {
        return new Gson().toJson(this);
    }
}
