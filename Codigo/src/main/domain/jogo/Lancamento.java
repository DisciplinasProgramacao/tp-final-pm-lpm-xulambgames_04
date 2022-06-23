package main.domain.jogo;

/**
 * Jogo lancamento, descendente de Jogo e que implementa a classe IAdicional por
 * receber um valor adicional em cima do seu valor original.
 * 
 */
public class Lancamento extends Jogo implements IAdicional {
	private static final long serialVersionUID = 434121L;

	// Lançamentos Vendidos com adicional de 10% ao preço original
	public static final double PCT_ADICIONAL = 0.10;

	private double precoOriginal;

	public Lancamento(String nome, double valor) {
		this.nome = nome;
		setPreco(valor);
	}

	public Lancamento() {
		this.precoOriginal = 0;
		this.preco = 0;
	}

	/**
	 * Calcula o preco do jogo Lancamento adicionando o adicional.
	 * 
	 * @param valor
	 * @return preco total do jogo
	 */
	@Override
	public Double calcularPreco(double valor) {
		return valor + calcularAdicional();
	}

	/**
	 * Calcula o valor adicional em porcentagem em cima do preco original.
	 * 
	 * @return valor adicional
	 */
	@Override
	public double calcularAdicional() {
		return this.precoOriginal * PCT_ADICIONAL;
	}

	/**
	 * Define o preco do jogo e calcula o preco final com base na regra de negócio.
	 * 
	 * @param valor
	 */
	@Override
	public void setPreco(double valor) {
		this.precoOriginal = valor;
		this.preco = calcularPreco(this.precoOriginal);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Lancamento!:"
				+ super.toString()
				+ "\nPreco Original:" + this.precoOriginal + "\n-----------------");

		return sb.toString();
	}
}
