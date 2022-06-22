package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import main.domain.cliente.Categoria;
import main.domain.cliente.Cliente;
import main.domain.jogo.Jogo;
import main.domain.jogo.Lancamento;
import main.domain.jogo.Premium;
import main.domain.jogo.Promocional;
import main.domain.jogo.Regular;
import main.domain.jogo.factory.FabricaJogosCollection;
import main.domain.jogo.factory.IFabricaJogos;
import ui.FaberCastel;
import ui.Menu;

//Cliente comprar o jogo
//Filtros dentro do switch do cliente -> métodos prontos
//Listar jogos mais vendidos
//Jogos menos vendidos
//Valor médio no mês
public class XulambsGames {

	static Menu menu = new Menu("XulambsGames", "Welcome");
	static Menu gameMenu = new Menu("Jogos", "");
	static Menu createGameMenu = new Menu("Jogos", "Categorias");
	static Menu clientsLoginMenu = new Menu("Clientes", "Login");
	static Menu clientsMenu = new Menu("Clientes", "Login");
	static Menu clientGamesFilterMenu = new Menu("Clientes", "Menu");
	static Menu clientRegisterMenu = new Menu("Clientes", "Planos");
	static Menu administratorMenu = new Menu("Administração", "-------");
	static Menu clientHistoricoPorJogoMenu = new Menu("Clientes", "Tipos de jogos");

	static FabricaJogosCollection todasAsFabricas;

	static Map<String, Cliente> clients = new HashMap<>();
	static Map<String, Jogo> games = new HashMap<>();

	final static String clientsFile = "Clientes.txt";
	final static String gamesFile = "Games.txt";
	final static String factoryFilePath = "Factory.txt";

	public static void main(String[] args) {
		HashMap<Integer, String> menuOptions = new HashMap<>();
		HashMap<Integer, String> clientsLoginOptions = new HashMap<>();
		HashMap<Integer, String> clientsOptions = new HashMap<>();
		HashMap<Integer, String> administradorOptions = new HashMap<>();
		HashMap<Integer, String> clientRegisterOptions = new HashMap<>();
		HashMap<Integer, String> gamesRegisterOptions = new HashMap<>();
		HashMap<Integer, String> clientGamesFilterOptions = new HashMap<>();
		HashMap<Integer, String> clientGamesHistoryOptions = new HashMap<>();

		boolean color = false;
		Scanner input = new Scanner(System.in);

		factoryConfig();

		menuOptions.put(1, "Clientes");
		menuOptions.put(2, "Administração");
		menuOptions.put(0, "Sair");

		clientsLoginOptions.put(1, "Login");
		clientsLoginOptions.put(2, "Cadastre-se");
		clientsLoginOptions.put(0, "Voltar");

		clientsOptions.put(1, "Meus jogos");
		clientsOptions.put(2, "Comprar jogos");
		clientsOptions.put(0, "Voltar");

		administradorOptions.put(1, "Cadastrar Jogos");
		administradorOptions.put(2, "Jogos mais vendidos");
		administradorOptions.put(3, "Jogos menos vendidos");
		administradorOptions.put(4, "Valor mensal vendido");
		administradorOptions.put(5, "Catálogo de Clientes");
		administradorOptions.put(6, "Catálogo de Jogos");
		administradorOptions.put(0, "Voltar");

		clientGamesFilterOptions.put(1, "Historico de compras");
		clientGamesFilterOptions.put(2, "Historico por jogo");
		clientGamesFilterOptions.put(3, "Historico por data");
		clientGamesFilterOptions.put(4, "Historico por categoria");
		clientGamesFilterOptions.put(0, "Voltar");

		clientRegisterOptions.put(1, "Empolgado");
		clientRegisterOptions.put(2, "Cadastrado");
		clientRegisterOptions.put(3, "Fanático");

		gamesRegisterOptions.put(1, "Lancamento");
		gamesRegisterOptions.put(2, "Regular");
		gamesRegisterOptions.put(3, "Promocional");
		gamesRegisterOptions.put(4, "Premium *-*");
		gamesRegisterOptions.put(0, "Voltar");

		clientGamesHistoryOptions.put(1, "Lancamento");
		clientGamesHistoryOptions.put(2, "Regular");
		clientGamesHistoryOptions.put(3, "Promocional");
		clientGamesHistoryOptions.put(4, "Premium *-*");
		clientGamesHistoryOptions.put(0, "Voltar");

		menu.setOptions(menuOptions);
		clientsLoginMenu.setOptions(clientsLoginOptions);
		clientsMenu.setOptions(clientsOptions);
		administratorMenu.setOptions(administradorOptions);
		clientGamesFilterMenu.setOptions(clientGamesFilterOptions);
		clientRegisterMenu.setOptions(clientRegisterOptions);
		createGameMenu.setOptions(gamesRegisterOptions);
		clientHistoricoPorJogoMenu.setOptions(clientGamesHistoryOptions);

		System.out.print("Deseja ter a experência do menu colorido?(y/n)");
		color = input.nextLine().equals("y") || input.nextLine().equals("Y") ? true : false;

		if (color)
			Menu.wColor(color);

		switchMainMenu(input);
		input.close();
	}

	public static void switchMainMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2));
		menu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				switchClientsMenu(input);
				break;
			case 2:
				switchAdministratorMenu(input);
				break;
			default:
				break;
		}
	}

	// Region Clients Menu

	public static void switchClientsMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2));

		clientsLoginMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);

		switch (option) {

			case 1:

				break;
			case 2:
				loginHandler(input);
				break;
			case 3:
				clientRegisterMenu.mainMenu();
				createClient(input);
				break;
			default:
				switchMainMenu(input);
				break;
		}
	}

	public static void loginHandler(Scanner input) {
		System.out.println(FaberCastel.colorize("Digite seu nome: "));
		String nome = input.nextLine();

		Optional<Cliente> clienteLogado = clients.values().stream()
				.filter(c -> c.getNome().equals(nome))
				.findFirst();

		clienteLogado.ifPresentOrElse((c) -> loggedClientMenu(input, c), () -> {
			System.out.println("O nome não foi encontrado!");
			switchClientsMenu(input);
		});
	}

	public static void loggedClientMenu(Scanner input, Cliente cliente) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4));
		clientGamesFilterMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				// Historico de compras
				cliente.historico();
				break;
			case 2:
				// Historico por jogo
				Jogo jogo = games.get(input.nextLine());

				if (jogo == null) {
					cliente.historicoPorJogo(jogo);
				} else {
					System.out.print("Jogo nao existente");
				}
				break;
			case 3:
				// Historico por data
				// cliente.historicoPorData(data);
				break;
			case 4:
				// Historico por categoria
				// cliente.historicoPorCategoria(categoria);
				break;
			default:

				break;
		}
	}

	public static void clientGameFilter(Scanner input, Cliente cliente) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4));

		clientGamesFilterMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);
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

	public static void createClient(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3));
		Cliente novo;
		String nome;

		clientRegisterMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				Menu.clearScreen();
				System.out.println(FaberCastel.colorize("Insira seu nome de EMPOLGADO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.EMPOLGADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				salvarBinario(clients, clientsFile);
				break;
			case 2:
				Menu.clearScreen();
				System.out.println(FaberCastel.colorize("Insira seu nome: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.CADASTRADO, nome);
				clients.put(novo.getNome(), novo); // trocar por id
				salvarBinario(clients, clientsFile);
				break;
			case 3:
				Menu.clearScreen();
				System.out.println(FaberCastel.colorize("Insira seu nome de FANATICO: "));
				nome = input.nextLine();
				novo = new Cliente(Categoria.FANATICO, nome);
				clients.put(novo.getNome(), novo);
				salvarBinario(clients, clientsFile);
				break;
			default:
		}
		switchClientsMenu(input);
	}

	// #endregion

	// Region Administrator Menu

	public static void switchAdministratorMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

		administratorMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 1:
				createGameMenu(input);
				break;
			case 2:
				// Jogos mais vendidos
				break;
			case 3:
				// Jogos menos vendidos
				break;
			case 4:
				// Valor mensal vendido
				break;
			case 5:
				System.out.println(FaberCastel.colorize("\n\nClientes cadastrados:\n"));
				clients.clear();
				carregarClientesDeArquivoTexto(clientsFile, clients);
				printClients();
				pausaTeclado(input);
				break;
			case 6:
				System.out.println(FaberCastel.colorize("\n\nJogos Disponíveis:\n"));
				games.clear();
				carregarJogosDeArquivoTexto(gamesFile, games);
				printGames();
				pausaTeclado(input);
				break;
			default:
				switchMainMenu(input);
		}
		switchAdministratorMenu(input);
	}

	// #endregion

	// Region gameRegion

	public static void createGameMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2, 3, 4));

		createGameMenu.mainMenu();
		int option = optionHandler(input.nextLine(), validOptions);
		switch (option) {
			case 1:
				createGame(input, "Lancamento");
				break;
			case 2:
				createGame(input, "Regular");
				break;
			case 3:
				createGame(input, "Promocional");
				break;
			case 4:
				createGame(input, "Premium");
				break;
			default:
				switchAdministratorMenu(input);
		}
		switchAdministratorMenu(input);
	}

	public static void createGame(Scanner input, String categoria) {
		System.out.println(FaberCastel.colorize("\nConte o nome do jogo"));
		String name = input.nextLine();
		double value = -1.0;
		while (value == -1.0) {
			System.out
					.println(FaberCastel
							.colorize("\nBeleza, agora informe o valor do jogo\n(Utilize '.' no lugar de ',')"));
			value = optionHandler(input.nextLine());
		}

		Jogo novo = todasAsFabricas.create(categoria);
		novo.setNome(name);
		novo.setPreco(value);
		games.put(novo.getNome(), novo);

		salvarBinario(games, gamesFile);
		Menu.clearScreen();
		System.out.println(FaberCastel.inCian("\nJogo criado!\n"));
		pausaTeclado(input);
	}

	public static void printGames() {
		for (Jogo c : games.values()) {
			System.out.println(c.toString() + "\n");
		}
	}

	// #endregion

	// Region configuracoes e utilidades

	public static void printClients() {
		for (Cliente c : clients.values()) {
			System.out.println(c.getNome());
		}
	}

	static void factoryConfig() {
		todasAsFabricas = new FabricaJogosCollection();
		Scanner leitor = null;
		try {
			leitor = new Scanner(new File(factoryFilePath));
			while (leitor.hasNextLine()) {
				String[] dadosFabrica = leitor.nextLine().split(";");
				todasAsFabricas.addFactory(dadosFabrica[0],
						(IFabricaJogos) Class.forName(dadosFabrica[1]).getConstructor().newInstance());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de configuração de fábricas não encontrado. Favor verificar e reiniciar.");
			pausaTeclado(new Scanner(System.in));
			return;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Problema na criação de fábricas. Favor contactar o suporte.");
			pausaTeclado(new Scanner(System.in));
		} catch (ClassNotFoundException e) {
			System.out.println("Classe não encontrada: " + e.getMessage());
		}
	}

	public static void carregarClientesDeArquivoTexto(String nomeAqr, Map<String, Cliente> list) {
		try {
			Cliente novo = null;
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(nomeAqr));
			do {
				try {
					novo = (Cliente) file.readObject();
				} catch (EOFException e) {
					return;
				}
				if (novo != null) {
					list.put(novo.getNome(), novo);
				}
			} while (novo != null);
			file.close();
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void carregarJogosDeArquivoTexto(String nomeAqr, Map<String, Jogo> list) {
		try {
			Jogo novo = null;
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(nomeAqr));
			do {
				try {
					novo = (Jogo) file.readObject();
				} catch (EOFException e) {
					return;
				}
				if (novo != null) {
					list.put(novo.getNome(), novo);
				}
			} while (novo != null);
			file.close();
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Salva o objeto inteiro em formato serializado (Object)
	 * 
	 * @param frota Lista
	 * @param arq   Nome do arquivo a ser gerado
	 */
	public static <T> void salvarBinario(Map<String, T> objects, String arq) {
		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(new FileOutputStream(arq));
			for (T obj : objects.values()) {
				saida.writeObject(obj);
			}
			saida.close();
			System.out.println(FaberCastel.colorize("Cadastro efetuado com sucesso! "));
		} catch (FileNotFoundException fe) {
			System.out.println("Arquivo não encontrado, ou permissão negada. Tente novamente com outro arquivo");
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("Problemas na operação de E/S. Contacte o suporte");
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}

	public static void pausaTeclado(Scanner input) {
		System.out.println(FaberCastel.colorize("\nPressione qualquer tecla para continuar"));
		try {
			input.nextLine();
		} catch (NoSuchElementException e) {
			return;
		}
	}

	public static int optionHandler(String input, List<Integer> validOptions) {
		Integer opt;
		try {
			opt = Integer.valueOf(input);
		} catch (Exception e) {
			opt = 0;
		}
		if (!validOptions.contains(opt)) {
			return 0;
		}
		return opt;
	}

	public static Double optionHandler(String input) {
		Double opt;
		try {
			opt = Double.valueOf(input);
		} catch (Exception e) {
			System.out.println(FaberCastel.inRed("\n!! Valor não aceito !!\n"));
			return -1.0;
		}
		return opt;
	}
}
