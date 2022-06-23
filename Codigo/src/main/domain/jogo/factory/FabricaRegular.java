package main.domain.jogo.factory;

import main.domain.jogo.Regular;

/**
 * Classe FabricaRegular, cujo propósito é instanciar a classe de jogos
 * Regular.
 */
public class FabricaRegular implements IFabricaJogos {
	public Regular criar() {
		return new Regular();
	}
}
