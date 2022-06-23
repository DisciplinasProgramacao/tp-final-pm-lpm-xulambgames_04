package main.domain.jogo;

/**
 * Classe de jogos Promocionais, implementa a interface IDesconto para definir a
 * regra de negócio do jogo.
 */
public class Promocional extends Jogo implements IDesconto {
	private static final long serialVersionUID = 14234236L;
	// Vendidos por um valor entre 30 e 50% do preço original
	private static final double PCT_DESCONTO_MIN = 0.50; // 50% do valor total
	private static final double PCT_DESCONTO_MAX = 0.70; // 70% do valor total

	private double pctDesconto;
	private double precoOriginal;

	/**
	 * Construtor da classe
	 * 
	 * @param nome
	 * @param valor
	 */
	public Promocional(String nome, double valor) {
		this.pctDesconto = PCT_DESCONTO_MIN;
		this.nome = nome;
		setPreco(valor);
	}

	/**
	 * construtor vazio da classe,
	 * preco = 0.
	 * desconto = desconto mínimo
	 */
	public Promocional() {
		this.precoOriginal = 0;
		this.preco = 0;
		this.pctDesconto = PCT_DESCONTO_MIN;
	}

	/**
	 * retorna o desconto mínimo possivel para esse jogo.
	 * 
	 * @return desconto minimo
	 */
	public double getPctDescontoMin() {
		return PCT_DESCONTO_MIN;
	}

	/**
	 * retorna o desconto máximo possivel para esse jogo.
	 * 
	 * @return desconto máximo
	 */
	public double getPctDescontoMax() {
		return PCT_DESCONTO_MAX;
	}

	/**
	 * define a porcentagem de desconto sobre o valor original do jogo.
	 * 
	 * descontoMin < Valor < descontoMax
	 * 
	 * @param valor
	 */
	public void setDesconto(double valor) {
		if (valor > PCT_DESCONTO_MAX || valor < PCT_DESCONTO_MIN) {
			return;
		}
		this.pctDesconto = valor;
		this.preco = this.calcularPreco(this.precoOriginal);
	}

	/**
	 * calcula o preco total do jogo subtraíndo o desconto do valor original.
	 * 
	 * @return preco com desconto aplicado.
	 */
	@Override
	public Double calcularPreco(double valor) {
		return this.precoOriginal - calcularDesconto();
	}

	/**
	 * calcula o desconto que será aplicado sobre o valor original do jogo.
	 * 
	 * @return valor do desconto.
	 */
	@Override
	public double calcularDesconto() {
		return this.precoOriginal * pctDesconto;
	}

	/**
	 * define o preco original do jogo e calcula o preco total do jogo, já com o
	 * desconto atribuído.
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
		sb.append("Promocional!"
				+ super.toString()
				+ "\nPreco Original: " + this.precoOriginal + "\n-----------------");

		return sb.toString();
	}
}
