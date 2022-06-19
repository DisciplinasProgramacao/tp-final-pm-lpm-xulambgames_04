package main.domain.jogo;

public class Regular extends Jogo implements IDesconto{
	
	public static final double PCT_DESCONTO_MAX = 0.3;
	
	private double desconto;
	private double precoOriginal;
	
	public Regular(String nome, double valor) {
		this.desconto = 0;
		this.nome = nome;
		this.precoOriginal = valor;
		this.preco = calcularPreco(valor);;
	}
	

	public void setDesconto(double valor) {
		if(valor > PCT_DESCONTO_MAX || valor < 0)
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
	
}
