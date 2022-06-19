package main.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import main.domain.jogo.Jogo;

public class Recibo implements Serializable {
	private double valorTotal;
	private double valorOriginal;
	private LocalDate data;
	private List<Jogo> jogos;
	private boolean pago;
	private double valorPago;

	public Recibo(LocalDate data, Jogo jogo) {
		this.jogos = new LinkedList<>();
		this.addJogo(jogo);
		this.pago = false;
		this.valorOriginal = 0;
		calcularValorTotal();
		this.valorPago = 0;
	}

	public Recibo(LocalDate data, List<Jogo> jogos) {
		this.data = data;
		this.jogos = new LinkedList<>();
		this.jogos.addAll(jogos);
		this.pago = false;
		calcularValorTotal();
		this.valorPago = 0;
	}

	public Recibo(LocalDate data) {
		this.jogos = new LinkedList<>();
		this.data = data;
		this.valorTotal = 0;
		this.valorOriginal = 0;
		this.pago = false;
		this.valorPago = 0;
	}

	public boolean isPago() {
		return this.pago;
	}

	public boolean pagar(double valorAPagar, double valorPago) {
		this.setValorTotal();
		if (this.valorTotal <= valorAPagar) {
			this.pago = true;
			this.valorPago = valorPago;
			return true;
		}
		return false;
	}

	private void setValorTotal() {
		this.valorTotal = calcularValorTotal();
	}

	public void addJogo(Jogo jogo) {
		this.jogos.add(jogo);
		this.setValorTotal();
	}

	public void addJogos(List<Jogo> jogos) {
		this.jogos.addAll(jogos);
		this.setValorTotal();
	}

	public List<Jogo> getJogos() {
		return this.jogos;
	}

	public double getValor() {
		setValorTotal();
		return this.valorTotal;
	}

	public double calcularValorTotal() {
		this.valorOriginal = 0;
		jogos.stream()
				.forEach(j -> this.valorOriginal += j.getPreco());

		return this.valorOriginal * (1 - calcularDesconto());
	}

	public double calcularDesconto() {
		long qtdPromocionais = jogos.stream()
				.filter(j -> j.getClass().getSimpleName().equals("Promocional"))
				.count();

		long qtdRegulares = jogos.stream()
				.filter(j -> j.getClass().getSimpleName().equals("Regular"))
				.count();

		long qtdPremium = jogos.stream()
				.filter(j -> j.getClass().getSimpleName().equals("Premium"))
				.count();

		long qtdLancamentos = jogos.stream()
				.filter(j -> j.getClass().getSimpleName().equals("Lancamento"))
				.count();

		long totalJogos = qtdLancamentos + qtdPremium + qtdRegulares + qtdPromocionais;
		long totalJogosNRegulares = totalJogos - qtdRegulares;

		if (qtdLancamentos >= 2 || (qtdPremium == 2 && totalJogos > qtdPremium) || qtdPremium == 3 || qtdRegulares == 5
				|| (qtdRegulares == 3 && totalJogosNRegulares > 0)) {
			return 0.2;
		}
		if (qtdPremium == 2 || qtdRegulares == 4) {
			return 0.1;
		}
		return 0;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\nData: " + this.data + "\n");
		sb.append("\nTitulos: \n");
		sb.append("==========\n");
		for (Jogo j : jogos) {
			sb.append(j.getNome() + "\n");
		}
		sb.append("==========");
		sb.append("\nDesconto: " + (calcularDesconto() * 100) + "%");
		sb.append("\nValor Total: " + this.valorTotal);
		sb.append("\nValor Pago: " + this.valorPago);
		return sb.toString();
	}
}
