package main.domain.jogo.factory;

import main.domain.jogo.Lancamento;

public class FabricaLancamento implements IFabricaJogos {
	public Lancamento criar(String nome, double preco) {
		return new Lancamento(nome, preco);
	}
}
