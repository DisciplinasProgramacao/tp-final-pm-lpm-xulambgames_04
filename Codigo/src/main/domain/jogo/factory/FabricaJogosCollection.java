package main.domain.jogo.factory;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Optional;

import main.domain.jogo.Jogo;

public class FabricaJogosCollection {
	private HashMap<String, IFabricaJogos> gamesFactory;

	public FabricaJogosCollection() {
		gamesFactory = new HashMap<>();
	}

	public void addFactory(String key, IFabricaJogos factory) {
		if (key != "" && factory != null)
			this.gamesFactory.put(key.toLowerCase(), factory);
	}

	public Jogo create(String key) {
		Optional<IFabricaJogos> factory = Optional.ofNullable(
				gamesFactory.get(key.toLowerCase()));

		return factory.map(IFabricaJogos::criar)
				.orElseThrow(InvalidParameterException::new);
	}

	public void toString1() {
		gamesFactory.values().stream().forEach(g -> System.out.println(g.getClass().getName()));
	}
}
