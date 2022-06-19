package main.domain.jogo;

public abstract class Jogo{
	protected double preco;
	protected String nome;
	
	
	public abstract Double calcularPreco(double valor);
	
	public double getPreco() {
		return this.preco;
	}
	
	public String getNome() {
		return this.nome;
	}
}
