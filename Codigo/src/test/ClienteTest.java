package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.domain.Recibo;
import main.domain.cliente.Categoria;
import main.domain.cliente.Cliente;
import main.domain.jogo.Lancamento;
import main.domain.jogo.Premium;
import main.domain.jogo.Promocional;
import main.domain.jogo.Regular;
import main.domain.jogo.factory.FabricaLancamento;
import main.domain.jogo.factory.FabricaPremium;
import main.domain.jogo.factory.FabricaPromocional;
import main.domain.jogo.factory.FabricaRegular;
import main.domain.jogo.factory.IFabricaJogos;

public class ClienteTest {

	Cliente cadastrado;
	Cliente empolgado;
	Cliente fanatico;

	IFabricaJogos lancamento;
	IFabricaJogos promocional;
	IFabricaJogos jRegular;
	IFabricaJogos premium;

	Promocional jogoPromocional;
	Regular jogoRegular;
	Premium jogoPremium;
	Lancamento jogoLancamento;

	Recibo recibo;

	@BeforeEach
	public void init() {

		lancamento = new FabricaLancamento();
		jRegular = new FabricaRegular();
		premium = new FabricaPremium();
		promocional = new FabricaPromocional();

		jogoPromocional = (Promocional) promocional.criar();
		jogoPromocional.setNome("A Way Out");
		jogoPromocional.setPreco(30);
		jogoPromocional.setDesconto(0.8);

		jogoRegular = (Regular) jRegular.criar();
		jogoRegular.setNome("Sonic Racing");
		jogoRegular.setPreco(160);
		jogoRegular.setDesconto(0.3);

		jogoPremium = (Premium) premium.criar();
		jogoPremium.setNome("Death Stranding");
		jogoPremium.setPreco(150);

		jogoLancamento = (Lancamento) lancamento.criar();
		jogoLancamento.setNome("Stray");
		jogoLancamento.setPreco(250);

		recibo = new Recibo(LocalDate.now());

		cadastrado = new Cliente(Categoria.CADASTRADO);
		fanatico = new Cliente(Categoria.FANATICO);
		empolgado = new Cliente(Categoria.EMPOLGADO);
	}

	// Region Mensalidade

	@Test
	public void mensalidadeClienteRegular() {
		assertEquals(0, cadastrado.getPrecoMensalidade());
	}

	@Test
	public void mensalidadeClienteEmpolgado() {
		assertEquals(10, empolgado.getPrecoMensalidade());
	}

	@Test
	public void mensalidadeClienteFanatico() {
		assertEquals(25, fanatico.getPrecoMensalidade());
	}
	// ##EndRegion

	// Region Regular

	@Test
	public void comprarDoisJogosLancamento() {
		Lancamento jogoLancamento2 = (Lancamento) lancamento.criar();
		jogoLancamento2.setNome("Elden Ring");
		jogoLancamento2.setPreco(250);
		recibo.addJogo(jogoLancamento2);
		recibo.addJogo(jogoLancamento);

		assertEquals(true, cadastrado.comprar(recibo, 440));

	}

	// ##EndRegion

	// Region empolgado

	@Test
	public void comprarTresJogosPremium() {
		Premium jogoPremium2 = (Premium) premium.criar();
		jogoPremium2.setNome("Elden Ring");
		jogoPremium2.setPreco(150);
		Premium jogoPremium3 = (Premium) premium.criar();
		jogoPremium3.setNome("The Legend of Zelda: Breath of the Wild");
		jogoPremium3.setPreco(150);

		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		recibo.addJogo(jogoPremium3);

		assertEquals(true, empolgado.comprar(recibo, 324));

	}

	// ##EndRegion

	// Region fanatico
	@Test
	public void comprarDoisJogosPremium() {
		Premium jogoPremium2 = (Premium) premium.criar();
		jogoPremium2.setNome("Elden Ring");
		jogoPremium2.setPreco(150);
		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);

		assertEquals(true, fanatico.comprar(recibo, 189));
	}

	// ##EndRegion
	@Test
	public void geracaoDeRelatorio() {
		Premium jogoPremium2 = (Premium) premium.criar();
		jogoPremium2.setNome("Elden Ring");
		jogoPremium2.setPreco(150);

		StringBuilder sb = new StringBuilder();

		sb.append("Recibo:\n"
				+ "-------------\n"
				+ "Data: " + LocalDate.now() + "\n"
				+ "\r\n"
				+ "Titulos: \n"
				+ "==========\n"
				+ "Death Stranding\n"
				+ "Elden Ring\n"
				+ "==========\n"
				+ "Desconto: 10.0%\n"
				+ "Valor Total: 270.0\n"
				+ "Valor Pago: 189.0\n"
				+ "-------------\n");

		recibo.addJogo(jogoPremium);
		recibo.addJogo(jogoPremium2);
		fanatico.comprar(recibo, 189);

		assertEquals(sb, fanatico.historicoPorData(LocalDate.now()));
	}
}
