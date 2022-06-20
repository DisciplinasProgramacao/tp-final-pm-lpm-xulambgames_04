package main.domain.cliente;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import main.domain.Recibo;

public class Cliente implements Serializableble {
	private static final long serialVersionUID = 1L;
	private Categoria categoria;
	private List<Recibo> recibos;

	private double precoMensalidade;
	private String nome;
	private String senha;

	public Cliente(Categoria categoria) {
		this.setCategoria(categoria);
		this.setPrecoMensalidade(this.categoria.getValor());
		this.recibos = new LinkedList<Recibo>();
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		this.setPrecoMensalidade(this.categoria.getValor());
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

	public void setPrecoMensalidade(double precoMensalidade) {
		this.precoMensalidade = precoMensalidade;
	}

	public boolean comprar(Recibo recibo, double valorAPagar) {
		double precoRecibo = recibo.getValor();
		double total = valorAPagar;
		if (precoRecibo * (1 - this.categoria.pctDesconto()) <= valorAPagar) {
			this.recibos.add(recibo);
			total = recibo.getValor();
		}
		return recibo.pagar(total, valorAPagar);
	}

	public String gerarHistorico() {
		StringBuilder sb = new StringBuilder();

		this.recibos.stream()
				.forEach(r -> {
					sb.append("Recibo: \n");
					sb.append("-------------");
					sb.append(r.toString());
					sb.append("\n-------------");
				});
		return sb.toString();
	}

}
