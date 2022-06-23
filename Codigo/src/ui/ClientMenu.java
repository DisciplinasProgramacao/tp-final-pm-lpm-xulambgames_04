package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import main.domain.Recibo;
import main.domain.cliente.Categoria;
import main.domain.cliente.Cliente;
import main.domain.jogo.Jogo;
import main.domain.jogo.Lancamento;
import main.domain.jogo.Premium;
import main.domain.jogo.Promocional;
import main.domain.jogo.Regular;
import util.EscritaDeArquivo;
import util.LeituraDeArquivo;

public class ClientMenu {
	private Menu clientsLoginMenu = new Menu("Clientes", "Login");
	private Menu clientsMenu = new Menu("Clientes", "Login");
	private Menu clientGamesFilterMenu = new Menu("Clientes", "Menu");
	private Menu clientRegisterMenu = new Menu("Clientes", "Planos");
	private Menu clientHistoricoPorJogoMenu = new Menu("Clientes", "Tipos de jogos");

	private HashMap<Integer, String> clientsLoginOptions = new HashMap<>();
	private HashMap<Integer, String> clientsOptions = new HashMap<>();
	private HashMap<Integer, String> clientGamesFilterOptions = new HashMap<>();
	private HashMap<Integer, String> clientGamesHistoryOptions = new HashMap<>();
	private HashMap<Integer, String> clientRegisterOptions = new HashMap<>();

	private EscritaDeArquivo<Cliente> escritaCliente = new EscritaDeArquivo<>();
	private EscritaDeArquivo<Recibo> escritaRecibo = new EscritaDeArquivo<>();
	private List<Recibo> recibos = new ArrayList<>();

	private static Map<String, Cliente> clients = new HashMap<>();
	private static Map<String, Jogo> games = new HashMap<>();

	private static String clientsFile;
	private String recibosFile;

	public ClientMenu(Map<String, Cliente> clientes, Map<String, Jogo> jogos, String recibosFile,
			List<Recibo> recibosList,
			String clientsFileName) {

		this.recibosFile = recibosFile;
		clients = clientes;
		clientsFile = clientsFileName;
		games = jogos;
		recibos = recibosList;

		clientsOptions.put(1, "Meus jogos");
		clientsOptions.put(2, "Comprar jogos");
		clientsOptions.put(0, "Voltar");

		clientsLoginOptions.put(1, "Login");
		clientsLoginOptions.put(2, "Cadastre-se");
		clientsLoginOptions.put(0, "Voltar");

		clientGamesFilterOptions.put(1, "Comprar jogos");
		clientGamesFilterOptions.put(2, "Historico de compras");
		clientGamesFilterOptions.put(3, "Historico por jogo");
		clientGamesFilterOptions.put(4, "Historico por data");
		clientGamesFilterOptions.put(5, "Historico por categoria");
		clientGamesFilterOptions.put(0, "Voltar");

		clientRegisterOptions.put(1, "Empolgado");
		clientRegisterOptions.put(2, "Cadastrado");
		clientRegisterOptions.put(3, "Fanático");

		clientGamesHistoryOptions.put(1, "Lancamento");
		clientGamesHistoryOptions.put(2, "Regular");
		clientGamesHistoryOptions.put(3, "Promocional");
		clientGamesHistoryOptions.put(4, "Premium *-*");
		clientGamesHistoryOptions.put(0, "Voltar");

		clientsLoginMenu.setOptions(clientsLoginOptions);
		clientsMenu.setOptions(clientsOptions);
		clientGamesFilterMenu.setOptions(clientGamesFilterOptions);
		clientRegisterMenu.setOptions(clientRegisterOptions);
		clientHistoricoPorJogoMenu.setOptions(clientGamesHistoryOptions);
	}

	public Cliente loginHandler(Scanner input) throws Exception {
		LeituraDeArquivo.carregarClientesDeArquivoTexto(clientsFile, clients);
		System.out.println(Menu.stringer("Digite seu nome: "));
		String nome = input.nextLine();

		Optional<Cliente> clienteLogado = clients.values().stream()
				.filter(c -> c.getNome().equalsIgnoreCase(nome))
				.findFirst();

		return clienteLogado.orElseThrow(() -> new Exception("Cliente não encontrado!"));
	}

	public void clientGameFilter(Scanner input, Cliente cliente) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4));
		clientHistoricoPorJogoMenu.mainMenu();
		int option = Menu.optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				System.out.println(cliente.historicoPorCategoria(Lancamento.class));
				break;
			case 2:
				System.out.println(cliente.historicoPorCategoria(Regular.class));
				break;
			case 3:
				System.out.println(cliente.historicoPorCategoria(Promocional.class));
				break;
			case 4:
				System.out.println(cliente.historicoPorCategoria(Premium.class));
				break;
			default:
				break;
		}
	}

	public void loggedClientMenu(Scanner input, Cliente cliente) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4, 5));
		clientGamesFilterMenu.mainMenu();

		int option = Menu.optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				AdministratorMenu.printGames();
				buyGames(cliente, input);
				break;
			case 2:
				System.out.println(Menu.stringer(cliente.historico()));
				Menu.pausaTeclado(input);
				break;
			case 3:
				// Historico por jogo
				System.out.print("Insira o nome do jogo no qual voce deseja pesquisar: ");
				Jogo jogoPesquisado = games.get(input.nextLine());
				if (jogoPesquisado == null) {
					System.out.println("Jogo nao encontrado");
				} else {
					System.out.println(jogoPesquisado.getNome());
					System.out.println(cliente.getNome());
				}
				Menu.pausaTeclado(input);
				break;
			case 4:
				// Historico por data
				System.out.print("Insira o ano: ");
				int ano = Integer.parseInt(input.nextLine());
				System.out.print("Insira o mes: ");
				int mes = Integer.parseInt(input.nextLine());
				System.out.print("Insira o dia: ");
				int dia = Integer.parseInt(input.nextLine());

				LocalDate data = LocalDate.of(ano, mes, dia);

				System.out.println(cliente.historicoPorData(data));

				Menu.pausaTeclado(input);
				break;
			case 5:
				// Historico por categoria
				clientGameFilter(input, cliente);
				break;
			default:
				return;
		}
		loggedClientMenu(input, cliente);
	}

	public void buyGames(Cliente cliente, Scanner input) {
		Menu.clearScreen();
		AdministratorMenu.printGames();
		System.out.println(Menu
				.stringer("\nDigite o nome dos jogos desejados separadamente por ',': "));
		String[] jogosEscolhidos;
		Recibo recibo = new Recibo(LocalDate.now());
		try {
			jogosEscolhidos = input.nextLine().split(",");

			for (int i = 0; i < jogosEscolhidos.length; i++) {
				Jogo jogo = searchGame(jogosEscolhidos[i]);
				recibo.addJogo(jogo);
			}
			double valorDoCliente = recibo.getValor() * (1 - cliente.getCategoria().pctDesconto());

			System.out.println(Menu.stringer("\nValor do pedido: R$ " + valorDoCliente, UiColors.CIAN));
			System.out.print(Menu.stringer("Pagamento: R$ "));

			double pagamento = -1.0;
			while (pagamento == -1.0) {
				pagamento = Menu.optionHandler(input.nextLine());
			}

			if (cliente.comprar(recibo, pagamento)) {
				recibos.add(recibo);

				escritaCliente.salvarBinario(clients, clientsFile);
				escritaRecibo.salvarBinario(recibos, recibosFile);
				System.out.println(Menu.stringer("Comprado com sucesso!"));
				Menu.pausaTeclado(input);
				loggedClientMenu(input, cliente);
			} else {
				System.out.println(Menu.stringer("Dinheiro insuficiente!", UiColors.RED));
				Menu.pausaTeclado(input);
				buyGames(cliente, input);
			}

		} catch (NoSuchElementException e) {
			System.out.println(Menu.stringer("Escolha um jogo!", UiColors.RED));
		} catch (Exception e) {
			System.out.println(Menu.stringer(e.getMessage(), UiColors.RED));
		} finally {
			Menu.pausaTeclado(input);
			loggedClientMenu(input, cliente);
		}
	}

	public Jogo searchGame(String gameName) throws Exception {
		return Optional.ofNullable(games.get(gameName)).orElseThrow(() -> new Exception("Jogo não encotrado!"));
	}

	public void createClientMenu(Scanner input) throws Exception {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3));
		Cliente novo;
		String nome;

		clientRegisterMenu.mainMenu();
		int option = Menu.optionHandler(input.nextLine(), validOptions);
		Menu.clearScreen();
		switch (option) {
			case 1:
				System.out.println(Menu.stringer("Insira seu nome de EMPOLGADO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.EMPOLGADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				escritaCliente.salvarBinario(clients, clientsFile);
				break;
			case 2:
				System.out.println(Menu.stringer("Insira seu nome: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.CADASTRADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				escritaCliente.salvarBinario(clients, clientsFile);
				break;
			case 3:
				System.out.println(Menu.stringer("Insira seu nome de FANATICO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.FANATICO, nome);
				clients.put(novo.getNome(), novo);
				escritaCliente.salvarBinario(clients, clientsFile);
				break;
			default:
		}

		switchClientsMenu(input);
	}

	public void switchClientsMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2));

		clientsLoginMenu.mainMenu();
		int option = Menu.optionHandler(input.nextLine(), validOptions);

		switch (option) {

			case 1:
				try {
					loggedClientMenu(input, loginHandler(input));
				} catch (Exception e) {
					System.out.println(Menu.stringer("Cliente não encontrado\n", UiColors.RED));
					Menu.pausaTeclado(input);
					switchClientsMenu(input);
				}
				break;
			case 2:
				clientRegisterMenu.mainMenu();
				createClient(input);
				break;
			default:
				return;
		}
	}

	public void createClient(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3));
		Cliente novo;
		String nome;

		clientRegisterMenu.mainMenu();
		int option = Menu.optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				Menu.clearScreen();
				System.out.println(Menu.stringer("Insira seu nome de EMPOLGADO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.EMPOLGADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				escritaCliente.salvarBinario(clients, clientsFile);

				break;
			case 2:
				Menu.clearScreen();
				System.out.println(Menu.stringer("Insira seu nome: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.CADASTRADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				escritaCliente.salvarBinario(clients, clientsFile);
				break;
			case 3:
				Menu.clearScreen();
				System.out.println(Menu.stringer("Insira seu nome de FANATICO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.FANATICO, nome);
				clients.put(novo.getNome(), novo);
				escritaCliente.salvarBinario(clients, clientsFile);
				break;
			default:
		}
		switchClientsMenu(input);
	}

	public static void printClients() {
		LeituraDeArquivo.carregarClientesDeArquivoTexto(clientsFile, clients);
		for (Cliente c : clients.values()) {
			System.out.println(Menu.stringer(c.getNome()));
		}
	}
}
