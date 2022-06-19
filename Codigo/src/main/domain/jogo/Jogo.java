package main.domain.jogo;

import java.io.Serializable;

public abstract class Jogo implements Serializable {
	protected double preco;
	protected String nome;

	public abstract Double calcularPreco(double valor);

	public double getPreco() {
		return this.preco;
	}

	public String getNome() {
		return this.nome;
	}
}
