package main.domain.jogo;

public class Regular extends Jogo implements IDesconto {
	// Vendidos por um valor entre 70 e 100% do preço original
	private static final long serialVersionUID = 1L;

	public static final double PCT_DESCONTO_MAX = 0.3;

	private double desconto;
	private double precoOriginal;

	public Regular(String nome, double valor) {
		this.desconto = 0;
		this.nome = nome;
		setPreco(valor);
	}

	public Regular() {
		this.desconto = 0;
		this.precoOriginal = 0;
		this.preco = 0;
	}

	public void setDesconto(double valor) {
		if (valor > PCT_DESCONTO_MAX || valor < 0)
			return;
		this.desconto = valor;
		this.preco = calcularPreco(this.precoOriginal);
	}

	@Override
	public double calcularDesconto() {
		return this.desconto * this.precoOriginal;
	}

	@Override
	public Double calcularPreco(double valor) {
		return valor - calcularDesconto();
	}

	@Override
	public void setPreco(double valor) {
		this.precoOriginal = valor;
		this.preco = calcularPreco(valor);
	}
}
