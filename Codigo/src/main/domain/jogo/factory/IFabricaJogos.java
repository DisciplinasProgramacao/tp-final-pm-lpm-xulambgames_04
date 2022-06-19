package main.domain.jogo.factory;

import main.domain.jogo.Jogo;

public interface IFabricaJogos {
	public Jogo criar(String nome, double preco);
}
 