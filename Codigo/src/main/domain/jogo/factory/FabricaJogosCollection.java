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

	/**
	 * Adicionar uma fábria ao HashMap
	 * 
	 * @param key
	 * @param factory
	 */
	public void addFactory(String key, IFabricaJogos factory) {
		if (key != "" && factory != null)
			this.gamesFactory.put(key.toLowerCase(), factory);
	}

	/**
	 * Criar a fabrica com base em sua Key do HashMap (Seu nome).
	 * 
	 * @param key
	 * @return Qualquer categoria descentende de jogo informado pela key já
	 *         instanciado.
	 */
	public Jogo create(String key) {
		Optional<IFabricaJogos> factory = Optional.ofNullable(
				gamesFactory.get(key.toLowerCase()));

		return factory.map(IFabricaJogos::criar)
				.orElseThrow(InvalidParameterException::new);
	}
}
