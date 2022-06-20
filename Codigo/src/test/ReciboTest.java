package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.domain.Recibo;
import main.domain.jogo.Lancamento;
import main.domain.jogo.Premium;
import main.domain.jogo.Promocional;
import main.domain.jogo.Regular;
import main.domain.jogo.factory.FabricaLancamento;
import main.domain.jogo.factory.FabricaPremium;
import main.domain.jogo.factory.FabricaPromocional;
import main.domain.jogo.factory.FabricaRegular;
import main.domain.jogo.factory.IFabricaJogos;

public class ReciboTest {
	IFabricaJogos lancamento;
	IFabricaJogos promocional;
	IFabricaJogos regular;
	IFabricaJogos premium;
	
	Promocional jogoPromocional;
	Regular jogoRegular;
	Premium jogoPremium;
	Lancamento jogoLancamento;
	
	
	Recibo recibo;
	
	@BeforeEach
	public void init() {
		
		lancamento = new FabricaLancamento();
		regular = new FabricaRegular();
		premium = new FabricaPremium();
		promocional = new FabricaPromocional();
		
		
		jogoPromocional = (Promocional) promocional.criar("A Way Out", 30);
		jogoPromocional.setDesconto(0.8);
		
		jogoRegular = (Regular) regular.criar("Sonic Racing", 160);
		jogoRegular.setDesconto(0.3);
		
		jogoPremium = (Premium) premium.criar("Death Stranding", 150);
		
		jogoLancamento = (Lancamento) lancamento.criar("Stray", 350);
		
		recibo = new Recibo(LocalDate.now());
	}
	
	@Test
	public void ReciboToString() {
		StringBuilder sb = new StringBuilder();

		recibo.addJogo(jogoPromocional);
		recibo.addJogo(jogoRegular);
		recibo.addJogo(jogoPremium);
		
		sb.append("\nData: " + LocalDate.now() + "\n"
				+ "\nTitulos: \n"
				+ "==========\n"
				+ jogoPromocional.getNome() + "\n"
				+ jogoRegular.getNome() + "\n"
				+ jogoPremium.getNome() + "\n"
				+ "=========="
				+ "\nDesconto: 0.0%"
				+ "\nValor Total: " + recibo.getValor()
				);
		sb.append("\nValor Pago: " + 0.0);
		
		assertEquals(sb.toString(), recibo.toString());	
	}
	
	// Region 20% de desconto
	
	@Test
	public void descontoComDoisLancamentos() {
		Lancamento jogoLancamento2 = (Lancamento) lancamento.criar("Elden Ring", 250);
		
		recibo.addJogo(jogoLancamento2);
		recibo.addJogo(jogoLancamento);
		
		assertEquals(0.2, recibo.calcularDesconto());
	}
	
	@Test
	public void descontoComDoisPremiumEUmJogo() {
		Premium jogoPremium2 = (Premium) premium.criar("Elden Ring", 250);
		
		recibo.addJogo(jogoPremium2);
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoLancamento);
		
		assertEquals(0.2, recibo.calcularDesconto());
	}
	
	@Test
	public void descontoComTresPremium() {
		Premium jogoPremium2 = (Premium) premium.criar("Elden Ring", 250);
		Premium jogoPremium3 = (Premium) premium.criar("The Legend of Zelda: Breath of the Wild", 500);

		
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		recibo.addJogo(jogoPremium3);
		
		assertEquals(0.2, recibo.calcularDesconto());
	}
	
	@Test
	public void descontoComTresRegularesEUmAcima() {
		Regular jogoRegular2 = (Regular) regular.criar("Elden Ring", 250);
		Regular jogoRegular3 = (Regular) regular.criar("The Legend of Zelda: Breath of the Wild", 500);

		
		recibo.addJogo(jogoRegular);
		recibo.addJogo(jogoRegular2);
		recibo.addJogo(jogoRegular3);
		recibo.addJogo(jogoLancamento);
		
		assertEquals(0.2, recibo.calcularDesconto());
	}
	
	@Test
	public void descontoComCincoRegulares() {
		Regular jogoRegular2 = (Regular) regular.criar("Elden Ring", 250);
		Regular jogoRegular3 = (Regular) regular.criar("The Legend of Zelda: Breath of the Wild", 500);
		Regular jogoRegular4 = (Regular) regular.criar("Marvel's Spider-Man: Miles Morales", 100);
		Regular jogoRegular5 = (Regular) regular.criar("Sack Boy: Uma grande aventura", 200);

		
		recibo.addJogo(jogoRegular);
		recibo.addJogo(jogoRegular2);
		recibo.addJogo(jogoRegular3);
		recibo.addJogo(jogoRegular4);
		recibo.addJogo(jogoRegular5);
		
		assertEquals(0.2, recibo.calcularDesconto());
	}
	
	// ##EndRegion
	
	// Region 10% de desconto
	
	@Test
	public void descontoComDoisPremium() {
		Premium jogoPremium2 = (Premium) premium.criar("Elden Ring", 250);
		
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		
		assertEquals(0.1, recibo.calcularDesconto());
	}
	
	@Test
	public void descontoComQuatroRegulares() {
		Regular jogoRegular2 = (Regular) regular.criar("Elden Ring", 250);
		Regular jogoRegular3 = (Regular) regular.criar("The Legend of Zelda: Breath of the Wild", 500);
		Regular jogoRegular4 = (Regular) regular.criar("Marvel's Spider-Man: Miles Morales", 100);

		
		recibo.addJogo(jogoRegular);
		recibo.addJogo(jogoRegular2);
		recibo.addJogo(jogoRegular3);
		recibo.addJogo(jogoRegular4);
		
		assertEquals(0.1, recibo.calcularDesconto());
	}
	
	// ##EndRegion
	
	// Region precoTotal
	
	@Test
	public void precoTotalCom20deDesconto() {
		Premium jogoPremium2 = (Premium) premium.criar("Elden Ring", 150);
		Premium jogoPremium3 = (Premium) premium.criar("The Legend of Zelda: Breath of the Wild", 150);

		
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		recibo.addJogo(jogoPremium3);
		
		assertEquals(360, recibo.getValor());
	}
	
	public void precoTotalCom10deDesconto() {
		Premium jogoPremium2 = (Premium) premium.criar("Elden Ring", 150);
		
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		
		assertEquals(270, recibo.getValor());
	}
	// ##EndRegion
	
}
