package main.domain.jogo.factory;

import main.domain.jogo.Premium;

public class FabricaPremium implements IFabricaJogos {
	public Premium criar() {
		return new Premium();
	}
}
