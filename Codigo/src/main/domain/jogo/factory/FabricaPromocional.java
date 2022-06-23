package main.domain.jogo.factory;

import main.domain.jogo.Promocional;

/**
 * Classe FabricaPromocional, cujo propósito é instanciar a classe de jogos
 * Promocional.
 */
public class FabricaPromocional implements IFabricaJogos {

	public Promocional criar() {
		return new Promocional();
	}
}
