package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.domain.jogo.Jogo;
import main.domain.jogo.Promocional;
import main.domain.jogo.Regular;
import main.domain.jogo.factory.FabricaLancamento;
import main.domain.jogo.factory.FabricaPremium;
import main.domain.jogo.factory.FabricaPromocional;
import main.domain.jogo.factory.FabricaRegular;
import main.domain.jogo.factory.IFabricaJogos;

public class JogoTest {
	
	IFabricaJogos lancamento;
	IFabricaJogos promocional;
	IFabricaJogos regular;
	IFabricaJogos premium;
	Jogo jogo;
	
	@BeforeEach
	public void init() {
		lancamento = new FabricaLancamento();
		regular = new FabricaRegular();
		premium = new FabricaPremium();
		promocional = new FabricaPromocional();
	}
	
	@Test
	public void valorJogoLancamentoComAdicional() {
		jogo = lancamento.criar("Stray", 350);
		assertEquals(385, jogo.getPreco());
	}
	
	@Test
	public void valorJogoPremium() {
		jogo = premium.criar("Death Stranding", 150);
		assertEquals(150, jogo.getPreco());
	}
	
	// Region Jogo Regular
	
	@Test
	public void valorJogoRegularSemAtribuirDesconto() {
		jogo = regular.criar("Sonic Racing", 160);
		assertEquals(160, jogo.getPreco());
	}
	
	@Test
	public void valorJogoRegularComDescontoAcimaDoPermitido() {
		Regular jogo = (Regular) regular.criar("Sonic Racing", 160);
		jogo.setDesconto(0.4);
		assertEquals(160, jogo.getPreco());
	}
	
	@Test
	public void valorJogoRegularComDescontoAbaixoDoPermitido() {
		Regular jogo = (Regular) regular.criar("Sonic Racing", 160);
		jogo.setDesconto(-0.5);
		assertEquals(160, jogo.getPreco());
	}
	
	@Test
	public void valorJogoRegularComDescontoDentroDoPermitido() {
		Regular jogo = (Regular) regular.criar("Sonic Racing", 160);
		jogo.setDesconto(0.3);
		assertEquals(112, jogo.getPreco());
	}
	
	// ##EndRegion
	
	// Region Jogo Promocional
	@Test
	public void valorJogoPromocionalSemAtribuirDesconto() {
		jogo = promocional.criar("A Way Out", 30);
		assertEquals(15, jogo.getPreco());
	}
	
	@Test
	public void valorJogoPromocionalComDescontoAbaixoDoMinimo() {
		Promocional jogo = (Promocional) promocional.criar("A Way Out", 30);
			jogo.setDesconto(0.4);
		assertEquals(15, jogo.getPreco());
	}
	
	@Test
	public void valorJogoPromocionalComDescontoAcimaDoMaximo() {
		Promocional jogo = (Promocional) promocional.criar("A Way Out", 30);
			jogo.setDesconto(0.8);
		assertEquals(15, jogo.getPreco());
	}
	
	@Test
	public void valorJogoPromocionalComDescontoAceitavel() {
		Promocional jogo = (Promocional) promocional.criar("A Way Out", 30);
			jogo.setDesconto(0.6);
		assertEquals(12, jogo.getPreco());
	}
	// ##End Region
}
