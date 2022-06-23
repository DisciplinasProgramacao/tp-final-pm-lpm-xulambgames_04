package main.domain.jogo;

public class Premium extends Jogo {
	// Lançamentos Vendidos com adicional de 10% ao preço original
	private static final long serialVersionUID = 15345323L;

	public Premium(String nome, double valor) {
		this.nome = nome;
		this.preco = valor;
		setPreco(valor);
	}

	public Premium() {
		this.preco = 0;
	}

	@Override
	public Double calcularPreco(double valor) {
		// TODO Auto-generated method stub
		return valor;
	}

	@Override
	public void setPreco(double valor) {
		this.preco = valor;
		this.preco = calcularPreco(this.preco);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Premium!:"
				+ super.toString() + "\n-----------------");

		return sb.toString();
	}
}
