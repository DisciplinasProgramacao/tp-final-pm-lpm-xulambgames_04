package main.domain.jogo;

import java.io.Serializable;

public abstract class Jogo implements Serializable {
	protected double preco;
	protected String nome;
	private static final long serialVersionUID = 1L;

	public abstract Double calcularPreco(double valor);

	public double getPreco() {
		return this.preco;
	}

	public String getNome() {
		return this.nome;
	}
}
