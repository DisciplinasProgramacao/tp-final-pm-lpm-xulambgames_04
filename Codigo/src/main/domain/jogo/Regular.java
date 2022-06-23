package main.domain.jogo;

/**
 * Classe de jogos regulares, implementa a classe IDesconto pois em sua regra de
 * negócio é possivel atribuír um desconto de até 30% do valor original ao jogo.
 */
public class Regular extends Jogo implements IDesconto {
	private static final long serialVersionUID = 183211L;

	public static final double PCT_DESCONTO_MAX = 0.3;

	private double desconto;
	private double precoOriginal;

	/**
	 * Construtor da classe
	 * 
	 * @param nome
	 * @param valor
	 */
	public Regular(String nome, double valor) {
		this.desconto = 0;
		this.nome = nome;
		setPreco(valor);
	}

	/**
	 * Construtor vazio da classe.
	 * Desconto = 0.
	 * preco = 0.
	 */
	public Regular() {
		this.desconto = 0;
		this.precoOriginal = 0;
		this.preco = 0;
	}

	/**
	 * Retorna o desconto máximo que é possível aplicar sobre o jogo.
	 * 
	 * @return descontoMax
	 */
	public double getPctDescontoMax() {
		return PCT_DESCONTO_MAX;
	}

	/**
	 * define o valor de desconto que será aplicado sobre o jogo
	 * 
	 * ex: 0.0 <= valor <= 0.3
	 * 
	 * @param valor
	 */
	public void setDesconto(double valor) {
		if (valor > PCT_DESCONTO_MAX || valor < 0)
			return;
		this.desconto = valor;
		this.preco = calcularPreco(this.precoOriginal);
	}

	/**
	 * faz o calculo do desconto sobre o valor do jogo.
	 * 
	 * @return desconto
	 */
	@Override
	public double calcularDesconto() {
		return this.desconto * this.precoOriginal;
	}

	/**
	 * Calcula o preco total do jogo, já com o desconto aplicado.
	 * 
	 * @param valor
	 * @return preco
	 */
	@Override
	public Double calcularPreco(double valor) {
		return valor - calcularDesconto();
	}

	/**
	 * define o preco do jogo.
	 * 
	 * @param valor
	 */
	@Override
	public void setPreco(double valor) {
		this.precoOriginal = valor;
		this.preco = calcularPreco(valor);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Regular!:"
				+ super.toString()
				+ "\nPreco Original:" + this.precoOriginal + "\n-----------------");
		return sb.toString();
	}
}
