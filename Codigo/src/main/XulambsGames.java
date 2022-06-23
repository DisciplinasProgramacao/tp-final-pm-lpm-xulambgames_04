package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.domain.Recibo;
import main.domain.cliente.Cliente;
import main.domain.jogo.Jogo;
import main.domain.jogo.factory.FabricaJogosCollection;
import main.domain.jogo.factory.IFabricaJogos;
import ui.AdministratorMenu;
import ui.ClientMenu;
import ui.Menu;
import util.LeituraDeArquivo;

//Cliente comprar o jogo
//Filtros dentro do switch do cliente -> métodos prontos
//Listar jogos mais vendidos
//Jogos menos vendidos
//Valor médio no mês

public class XulambsGames {

	static Map<String, Cliente> clients = new HashMap<>();
	static Map<String, Jogo> games = new HashMap<>();
	static List<Recibo> recibos = new ArrayList<>();

	final static String clientsFilePath = "Clientes.txt";
	final static String gamesFilePath = "Games.txt";
	final static String factoryFilePath = "Factory.txt";
	final static String recibosFilePath = "Recibos.txt";

	static FabricaJogosCollection todasAsFabricas = new FabricaJogosCollection();

	static Menu menu = new Menu("XulambsGames", "Welcome");
	static ClientMenu clientMenu = new ClientMenu(clients, games, recibosFilePath, recibos, clientsFilePath);
	static AdministratorMenu admMenu = new AdministratorMenu(
			games, clients, recibos, recibosFilePath, gamesFilePath,
			todasAsFabricas);

	public static void main(String[] args) {
		HashMap<Integer, String> menuOptions = new HashMap<>();
		boolean color = false;
		Scanner input = new Scanner(System.in);

		LeituraDeArquivo.carregarRecibosDeArquivoTexto(recibosFilePath, recibos);
		LeituraDeArquivo.carregarClientesDeArquivoTexto(clientsFilePath, clients);
		LeituraDeArquivo.carregarJogosDeArquivoTexto(gamesFilePath, games);
		factoryConfig();

		menuOptions.put(1, "Clientes");
		menuOptions.put(2, "Administração");
		menuOptions.put(0, "Sair");

		menu.setOptions(menuOptions);

		System.out.print("Deseja ter a experiência do menu colorido?(y/n)");
		color = input.nextLine().equalsIgnoreCase("y") ? true : false;

		if (color)
			Menu.wColor(color);

		try {
			while (true)
				switchMainMenu(input);

		} finally {
			input.close();
		}
	}

	public static void switchMainMenu(Scanner input) {
		List<Integer> validOptions = new ArrayList<>();
		validOptions.addAll(Arrays.asList(1, 2));
		menu.mainMenu();
		int option = Menu.optionHandler(input.nextLine(), validOptions);

		switch (option) {
			case 0:
				System.exit(0);
			case 1:
				clientMenu.switchClientsMenu(input);
				break;
			case 2:
				admMenu.switchAdministratorMenu(input);
				break;
		}
	}

	static void factoryConfig() {
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
			Menu.pausaTeclado(new Scanner(System.in));
			return;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Problema na criação de fábricas. Favor contactar o suporte.");
			Menu.pausaTeclado(new Scanner(System.in));
		} catch (ClassNotFoundException e) {
			System.out.println("Classe não encontrada: " + e.getMessage());
		}
	}
}
