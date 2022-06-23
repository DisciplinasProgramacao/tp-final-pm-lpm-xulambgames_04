package main.domain.cliente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.domain.Recibo;
import main.domain.jogo.Jogo;

/**
 * Classe Cliente utilizada na XulambsGames para representar o usuário que tem a
 * possibilidade de comprar diversos jogos.
 */
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1412489124L;

	private Categoria categoria;
	private List<Recibo> recibos;

	private double precoMensalidade;
	private String nome;
	private String senha;

	/**
	 * Contrutor de Cliente
	 * 
	 * @param categoria - Categoria do cliente (Cadastrado, Empolgado, Fanático)
	 */
	public Cliente(Categoria categoria) {
		this.setCategoria(categoria);
		this.recibos = new LinkedList<Recibo>();
	}

	/**
	 * Altera categoria e muda preço da mensalidade
	 * 
	 * @param categoria
	 */
	public Cliente(Categoria categoria, String nome) {
		this.nome = nome;
		this.setCategoria(categoria);
		this.setPrecoMensalidade(this.categoria.mensalidade());
		this.recibos = new LinkedList<Recibo>();
	}

	/**
	 * Define a categoria do cliente.
	 * 
	 * @param categoria
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		this.setPrecoMensalidade(this.categoria.mensalidade());
	}

	/**
	 * Retorna o nome do cliente.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Retorna a categoria do cliente
	 * 
	 * @return categoria do cliente
	 */
	public Categoria getCategoria() {
		return this.categoria;
	}

	/**
	 * Retorna uma lista com todos os recibos do cliente.
	 * 
	 * @return recibos do cliente
	 */
	public List<Recibo> getRecibos() {
		return this.recibos;
	}

	/**
	 * Retorna o preco da mensalidade definido na categoria.
	 * 
	 * @return preco da mensalidade
	 */
	public double getPrecoMensalidade() {
		return this.precoMensalidade;
	}

	/**
	 * Define o preco da mensalidade.
	 * 
	 * @param precoMensalidade
	 */
	private void setPrecoMensalidade(double precoMensalidade) {
		this.precoMensalidade = precoMensalidade;
	}

	/**
	 * Método de pagamento do cliente, onde ele informa o recibo e o pagamento e,
	 * caso seja aceito (valor do pagamento >= valor do recibo) adiciona o recibo à
	 * lista de recibos do cliente.
	 * 
	 * @param recibo      - recibo no qual o cliente está pagando
	 * @param valorAPagar - valor informado pelo cliente para o pagamento do recibo.
	 * @return - true caso o pagamento seja superior ao valor calculado no recibo.
	 */
	public boolean comprar(Recibo recibo, double valorAPagar) {
		double precoRecibo = recibo.getValor();
		double total = valorAPagar;
		if (precoRecibo * (1 - this.categoria.calcularDesconto()) <= valorAPagar) {
			this.recibos.add(recibo);
			total = recibo.getValor();
		}
		return recibo.pagar(total, valorAPagar);
	}

	/**
	 * Relatório do historico de compras/recibos.
	 * 
	 * @return String com detalhamento do historico.
	 */
	private String relatorio(List<Recibo> compras) {
		StringBuilder sb = new StringBuilder();

		compras.stream()
				.forEach(r -> {
					sb.append("Recibo: \n");
					sb.append("-------------");
					sb.append(r.toString());
					sb.append("\n-------------\n");
				});
		return sb.toString();
	}

	/**
	 * 
	 * @return o relatorico dos recibos.
	 */
	public String historico() {
		return relatorio(this.recibos);
	}

	/**
	 * Retorna um historico de jogos comprados pelo cliente com base no jogo
	 * escolhido.
	 * 
	 * @param jogo
	 * @return historico.
	 */
	public String historicoPorJogo(Jogo jogo) {
		List<Recibo> rbs = new ArrayList<>();

		for (Recibo r : recibos) {
			List<Jogo> lista = r.getJogos().stream()
					.filter(j -> j.getNome().equals(jogo.getNome()))
					.collect(Collectors.toList());
			if (lista.size() > 0) {
				rbs.add(r);
			}
		}

		if (rbs.size() == 0) {
			return "Voce ainda nao comprou o jogo '" + jogo.getNome() + "'";
		}

		return relatorio(rbs);
	}

	/**
	 * Retorna um historico de jogos comprados pelo cliente com base na categoria de
	 * jogos escolhida.
	 * 
	 * @param categoria
	 * @return historico
	 */
	public String historicoPorCategoria(String categoria) {
		List<Recibo> rbs = new ArrayList<>();

		for (Recibo r : recibos) {
			List<Jogo> lista = r.getJogos().stream()
					.filter(j -> j.getClass().getSimpleName().equals(categoria))
					.collect(Collectors.toList());

			if (lista.size() > 0) {
				rbs.add(r);
			}
		}
		return relatorio(rbs);
	}

	/**
	 * Retorna um historico de compras com base na data.
	 * 
	 * @param data
	 * @return historico.
	 */
	public String historicoPorData(LocalDate data) {
		List<Recibo> rbs = this.recibos.stream().filter(r -> r.getData().equals(data)).collect(Collectors.toList());

		if (rbs.size() == 0) {
			return "Voce nao realizou nenhuma compra no dia " + data;
		}

		return relatorio(rbs);
	}
}
