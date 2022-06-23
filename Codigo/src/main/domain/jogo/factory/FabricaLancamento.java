package main.domain.jogo.factory;

import main.domain.jogo.Lancamento;

/**
 * Classe FabricaLancamento, cujo propósito é instanciar a classe Lancamento.
 */
public class FabricaLancamento implements IFabricaJogos {
	public Lancamento criar() {
		return new Lancamento();
	}
}
