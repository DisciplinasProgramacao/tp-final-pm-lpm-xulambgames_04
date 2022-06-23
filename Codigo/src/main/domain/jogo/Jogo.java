package main.domain.jogo;

import java.io.Serializable;

public abstract class Jogo implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Nome: " + this.getNome()
				+ "\nPreço: " + this.getPreco() + "\n");
		return sb.toString();
	}

}
