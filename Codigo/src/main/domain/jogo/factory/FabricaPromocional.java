package main.domain.jogo.factory;

import main.domain.jogo.Promocional;

public class FabricaPromocional implements IFabricaJogos {

	public Promocional criar() {
		return new Promocional();
	}
}
