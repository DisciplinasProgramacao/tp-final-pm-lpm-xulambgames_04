package main.domain.cliente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.domain.Recibo;
import main.domain.jogo.Jogo;

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
	 * @param categoria - Categoria do cliente (Cadastrado, Empolgado, Fanatico)
	 */
	public Cliente(Categoria categoria) {
		this.setCategoria(categoria);
		this.recibos = new LinkedList<Recibo>();
	}

	/**
	 * Altera categoria e muda preco da mensalidade
	 * 
	 * @param categoria
	 */
	public Cliente(Categoria categoria, String nome) {
		this.nome = nome;
		this.setCategoria(categoria);
		this.setPrecoMensalidade(this.categoria.mensalidade());
		this.recibos = new LinkedList<Recibo>();
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		this.setPrecoMensalidade(this.categoria.mensalidade());
	}

	public String getNome() {
		return this.nome;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public List<Recibo> getRecibos() {
		return this.recibos;
	}

	public double getPrecoMensalidade() {
		return this.precoMensalidade;
	}

	/**
	 * (?) Mudar pra protected ou private
	 * 
	 * @param precoMensalidade
	 */
	private void setPrecoMensalidade(double precoMensalidade) {
		this.precoMensalidade = precoMensalidade;
	}

	/**
	 * 
	 * @param recibo      -
	 * @param valorAPagar -
	 * @return -
	 */
	public boolean comprar(Recibo recibo, double valorAPagar) {
		double precoRecibo = recibo.getValor();
		double total = valorAPagar;
		if (precoRecibo * (1 - this.categoria.pctDesconto()) <= valorAPagar) {
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
					sb.append("\n-------------");
				});
		return sb.toString();
	}

	public String historico() {
		return relatorio(this.recibos);
	}

	public String historicoPorJogo(Jogo jogo) {
		List<Recibo> historico = this.recibos.stream().filter(r -> r.getJogos().contains(jogo))
				.collect(Collectors.toList());
		if (historico.size() == 0) {
			return "Voce ainda nao comprou o jogo '" + jogo.getNome() + "'";
		}
		return relatorio(historico);
	}

	public <T> String historicoPorCategoria(Class<T> categoria) {
		List<Recibo> rbs = new ArrayList<>();

		for (Recibo r : recibos) {
			List<Jogo> lista = r.getJogos().stream()
					.filter(j -> j.getClass().equals(categoria.getClass()))
					.collect(Collectors.toList());
			if (lista.size() > 0) {
				rbs.add(r);
			}
		}
		return relatorio(rbs);
	}

	public String historicoPorData(LocalDate data) {
		List<Recibo> historico = this.recibos.stream().filter(r -> r.getData() == data).collect(Collectors.toList());
		if (historico.size() == 0) {
			return "Voce nao realizou nenhuma compra no dia " + data;
		}
		return relatorio(historico);
	}
}
