package main.domain.jogo;

public class Lancamento extends Jogo implements IAdicional {
	private static final long serialVersionUID = 1L;

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

	@Override
	public Double calcularPreco(double valor) {
		return valor + calcularAdicional();
	}

	@Override
	public double calcularAdicional() {
		return this.precoOriginal * PCT_ADICIONAL;
	}

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
				+ "\nPreco Original:" + this.precoOriginal);

		return sb.toString();
	}
}
