package main.domain.jogo;

public class Promocional extends Jogo implements IDesconto{
	
	public static final double PCT_DESCONTO_MIN = 0.50;
	public static final double PCT_DESCONTO_MAX = 0.70;
	
	private double pctDesconto;
	private double precoOriginal;
	
	public Promocional(String nome, double valor) {
		this.pctDesconto = PCT_DESCONTO_MIN;
		this.nome = nome;
		this.precoOriginal = valor;
		this.preco = calcularPreco(valor);
	}
	
	public void setDesconto(double valor) {
		if(valor > PCT_DESCONTO_MAX || valor < PCT_DESCONTO_MIN) {
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

}
