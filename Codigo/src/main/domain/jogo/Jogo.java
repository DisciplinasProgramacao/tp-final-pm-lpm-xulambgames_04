package main.domain.jogo;

import java.io.Serializable;

public abstract class Jogo implements Serializable {
	private static final long serialVersionUID = 131231251255L;

	protected double preco;
	protected String nome;

	public abstract Double calcularPreco(double valor);

	public abstract void setPreco(double preco);

	public double getPreco() {
		return this.preco;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String relatorio() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nNome: " + this.getNome()
				+ "\nPreço: " + this.getPreco() + "\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return this.relatorio();
	}
}
