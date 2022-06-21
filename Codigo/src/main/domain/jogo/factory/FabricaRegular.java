package main.domain.jogo.factory;

import main.domain.jogo.Regular;

public class FabricaRegular implements IFabricaJogos {
	public Regular criar() {
		return new Regular();
	}
}
