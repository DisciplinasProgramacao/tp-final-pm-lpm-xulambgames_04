package main.domain.jogo.factory;

import main.domain.jogo.Lancamento;

public class FabricaLancamento implements IFabricaJogos {
	public Lancamento criar() {
		return new Lancamento();
	}
}
