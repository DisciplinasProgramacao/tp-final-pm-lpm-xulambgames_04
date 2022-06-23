package main.domain.jogo;

/**
 * Classe Premium é a classe mais básica descentende de Jogo, não possui valores
 * adicionais e nem de desconto.
 */
public class Premium extends Jogo {
	private static final long serialVersionUID = 15345323L;

	/**
	 * Construtor da classe.
	 * 
	 * @param nome
	 * @param valor
	 */
	public Premium(String nome, double valor) {
		this.nome = nome;
		this.preco = valor;
		setPreco(valor);
	}

	/**
	 * Construtor vazio da classe.
	 * preco definido como 0.
	 */
	public Premium() {
		this.preco = 0;
	}

	/**
	 * retorna o proprio valor do jogo.
	 * 
	 * @param valor
	 * @return valor
	 */
	@Override
	public Double calcularPreco(double valor) {
		return valor;
	}

	/**
	 * Define o preco do jogo
	 * 
	 * @param valor
	 */
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
