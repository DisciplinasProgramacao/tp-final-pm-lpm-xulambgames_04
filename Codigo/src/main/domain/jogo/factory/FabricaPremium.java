package main.domain.jogo.factory;

import main.domain.jogo.Premium;

/**
 * Classe FabricaPremium, cujo propósito é instanciar a classe de jogos Premium.
 */
public class FabricaPremium implements IFabricaJogos {
	public Premium criar() {
		return new Premium();
	}
}
