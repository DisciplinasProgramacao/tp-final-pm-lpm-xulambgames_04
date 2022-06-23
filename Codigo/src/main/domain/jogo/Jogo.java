package main.domain.jogo;

import java.io.Serializable;

/**
 * Classe Jogo, classe serializada e superclasse de todos os jogos da
 * XulambsGames.
 */
public abstract class Jogo implements Serializable {
	private static final long serialVersionUID = 131231251255L;

	protected double preco;
	protected String nome;

	/**
	 * Método abstrato para calcular o preço total do jogo.
	 * 
	 * @param valor valor do jogo.
	 */
	public abstract Double calcularPreco(double valor);

	/**
	 * Método abstrato para definir o preço com base nas regras de negócio do jogo.
	 * 
	 * @param preco
	 */
	public abstract void setPreco(double preco);

	/**
	 * 
	 * @return preco do jogo
	 */
	public double getPreco() {
		return this.preco;
	}

	/**
	 * @return nome no jogo.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Define o nome do jogo.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Relatório básico do jogo com seu nome e preco.
	 * 
	 * @return relatorio
	 */
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
