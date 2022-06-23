package main.domain.jogo.factory;

import main.domain.jogo.Jogo;

/**
 * Interface IFabricaJogos, implementada nas classes de fábrica para
 * implementação do padrão de projetos Factory.
 */
public interface IFabricaJogos {
	public Jogo criar();
}
