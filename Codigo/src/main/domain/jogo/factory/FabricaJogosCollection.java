package main.domain.jogo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Optional;

import main.domain.jogo.Jogo;

public class FabricaJogosCollection {
	private HashMap<String, IFabricaJogos> games;

	public FabricaJogosCollection() {
		games = new HashMap<>();
	}

	public void addFactory(String key, IFabricaJogos factory) {
		if (key != "" && factory != null)
			this.games.put(key.toLowerCase(), factory);
	}

	public Jogo create(String key) {
		Optional<IFabricaJogos> factory = Optional.ofNullable(
				games.get(key.toLowerCase()));

		return factory.map(IFabricaJogos::criar)
				.orElseThrow(InvalidParameterException::new);
	}
}
