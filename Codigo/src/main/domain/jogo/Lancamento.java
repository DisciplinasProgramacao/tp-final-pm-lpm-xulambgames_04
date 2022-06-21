package main.domain.jogo;

public class Lancamento extends Jogo implements IAdicional{
	public static final double PCT_ADICIONAL = 0.10;
	
	private double precoOriginal;
	
	public Lancamento(String nome, double valor) {
		this.nome = nome;
		this.precoOriginal = valor;
		this.preco = calcularPreco(this.precoOriginal);;
		
	}

	@Override
	public Double calcularPreco(double valor) {
		return valor + calcularAdicional();
	}

	@Override
	public double calcularAdicional() {
		return this.precoOriginal * PCT_ADICIONAL;
	}

}
