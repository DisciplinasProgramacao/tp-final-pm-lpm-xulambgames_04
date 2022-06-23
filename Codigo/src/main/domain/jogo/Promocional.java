package main.domain.jogo;

public class Promocional extends Jogo implements IDesconto {
	private static final long serialVersionUID = 14234236L;
	// Vendidos por um valor entre 30 e 50% do preço original
	private static final double PCT_DESCONTO_MIN = 0.50; // 50% do valor total
	private static final double PCT_DESCONTO_MAX = 0.70; // 70% do valor total

	private double pctDesconto;
	private double precoOriginal;

	public Promocional(String nome, double valor) {
		this.pctDesconto = PCT_DESCONTO_MIN;
		this.nome = nome;
		setPreco(valor);
	}

	public Promocional() {
		this.precoOriginal = 0;
		this.preco = 0;
	}

	public double getPctDescontoMin() {
		return PCT_DESCONTO_MIN;
	}

	public double getPctDescontoMax() {
		return PCT_DESCONTO_MAX;
	}

	public void setDesconto(double valor) {
		if (valor > PCT_DESCONTO_MAX || valor < PCT_DESCONTO_MIN) {
			return;
		}
		this.pctDesconto = valor;
		this.preco = this.calcularPreco(this.precoOriginal);
	}

	@Override
	public Double calcularPreco(double valor) {
		return this.precoOriginal - calcularDesconto();
	}

	@Override
	public double calcularDesconto() {
		return this.precoOriginal * pctDesconto;
	}

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
