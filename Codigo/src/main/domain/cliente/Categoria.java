package main.domain.cliente;

import main.domain.jogo.IDesconto;

/**
 * CADASTRADO: sem mensalidade e sem desconto.
 * 
 * EMPOLGADOS: pagam uma mensalidade de R$10 e com isso
 * obtêm um desconto de 10% em cada compra realizada.
 * 
 * FANATICOS: pagam uma mensalidade mais alta, R$25,
 * mas têm direito a um desconto de 30% em cada compra.
 */
public enum Categoria implements IDesconto {
	EMPOLGADO(10.0, 0.1),
	FANATICO(25.0, 0.3),
	CADASTRADO(0, 0);

	private double mensalidade;
	private double pctDesconto;

	/**
	 * Construtor do Enum de categorias do cliente
	 * 
	 * @param valor
	 * @param desconto
	 */
	Categoria(double valor, double desconto) {
		this.mensalidade = valor;
		this.pctDesconto = desconto;
	}

	/**
	 * 
	 * @return valor da mensalidade com base na categoria.
	 */
	public double mensalidade() {
		return this.mensalidade;
	}

	/**
	 * 
	 * @return a porcentagem de desconto com base na categoria.
	 */
	@Override
	public double calcularDesconto() {
		return this.pctDesconto;
	}
}
