package br.com.caelum.estoque.modelo.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ItemDao {

	private static Map<String, Item> ITENS = new LinkedHashMap<>();

	public ItemDao() {
		popularItensNoMapa();
	}
	
	public void cadastrar(Item item) {
		ITENS.put(item.getCodigo(), item);
	}

	public ArrayList<Item> todosItens(List<Filtro> filtros) {
		
		ArrayList<Item> resultados = new ArrayList<Item>();
		Collection<Item> todosItens = ITENS.values();

		if (filtros == null || filtros.isEmpty()) {
			resultados.addAll(todosItens);
			return resultados;
		}
		
		for(Filtro filtro : filtros) {
			for (Item item : todosItens) {
				
				String tipo = filtro.getTipo().getNome();
				String nome = filtro.getNome();
				
				if(itemPossuiTipo(item, tipo) && itemPossuiNome(item, nome)){
					resultados.add(item);
				}
			}
		}
		
		return resultados;
	}

	//este método existe apenas para facilitar o primeiro exercicio que não usa o filtro
	public ArrayList<Item> todosItens() {
		return new ArrayList<>(ITENS.values());
	}
	
	private boolean itemPossuiNome(Item item, String nome) {
		return item.getNome().contains(nome);
	}

	private boolean itemPossuiTipo(Item item, String tipo) {
		return item.getTipo().equals(tipo);
	}
	
	public Item quantidadeDo(String codigo) {
		return ITENS.get(codigo);
	}
	

	
	private void popularItensNoMapa() {
		ITENS.put("MEA", new Item("MEA", "MEAN", "Livro", 5));
		ITENS.put("MEA", new Item("SEO","SEO na Prática", "Livro", 4));
		ITENS.put("RUB", new Item("RUB","Jogos com Ruby", "Livro", 8));
		ITENS.put("GAL", new Item("GAL","Galaxy Tab", "Tablet", 3));
		ITENS.put("IP4", new Item("IP4","IPhone 4 C", "Celular", 7));
		ITENS.put("IP5", new Item("IP5","IPhone 5", "Celular", 3));
		ITENS.put("IP6", new Item("IP6","IPhone 6 S", "Celular", 10));
		ITENS.put("MOX", new Item("MOX","Moto X", "Celular", 6));
		ITENS.put("MOG", new Item("MOG","Moto G", "Celular", 8));
		ITENS.put("MXX", new Item("MXX","Moto MAXX", "Celular", 2));
	}



}
