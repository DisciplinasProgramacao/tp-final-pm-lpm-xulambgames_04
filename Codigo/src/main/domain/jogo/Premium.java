package main.domain.jogo;

public class Premium extends Jogo {
	// Lançamentos Vendidos com adicional de 10% ao preço original

	public Premium(String nome, double valor) {
		this.nome = nome;
		this.preco = valor;
		this.preco = calcularPreco(valor);
	}

	@Override
	public Double calcularPreco(double valor) {
		// TODO Auto-generated method stub
		return valor;
	}

}
