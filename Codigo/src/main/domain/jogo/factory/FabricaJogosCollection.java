package main.domain.jogo.factory;

import java.util.ArrayList;

import main.domain.jogo.Jogo;

public class FabricaJogosCollection {
	private ArrayList<IFabricaJogos> colecoes;
	
	public FabricaJogosCollection() {
		colecoes = new ArrayList<>();
	}
	
	public void addFabrica(IFabricaJogos fabrica) {
		this.colecoes.add(fabrica);
	}
	
	public Jogo criar(String tipo) {
		return null;
	}
}
