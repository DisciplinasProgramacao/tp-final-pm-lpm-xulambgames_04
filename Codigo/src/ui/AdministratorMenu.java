package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.domain.cliente.Cliente;
import main.domain.jogo.Jogo;
import main.domain.jogo.factory.FabricaJogosCollection;
import util.EscritaDeArquivo;
import util.LeituraDeArquivo;

public class AdministratorMenu {

  private Menu createGameMenu = new Menu("Jogos", "Categorias");
  private Menu administratorMenu = new Menu("Administração", "-------");

  private Map<String, Cliente> clients = new HashMap<>();
  private static Map<String, Jogo> games = new HashMap<>();
  private FabricaJogosCollection todasAsFabricas;

  private EscritaDeArquivo<Jogo> escrita = new EscritaDeArquivo<>();
  private static String gamesFile;

  public AdministratorMenu(Map<String, Jogo> jogos, Map<String, Cliente> clients, String gamesFilename,
      FabricaJogosCollection todasAsFabricas) {
    this.clients = clients;
    games = jogos;
    gamesFile = gamesFilename;
    this.todasAsFabricas = todasAsFabricas;

    HashMap<Integer, String> gamesRegisterOptions = new HashMap<>();
    HashMap<Integer, String> administradorOptions = new HashMap<>();

    administradorOptions.put(1, "Cadastrar Jogos");
    administradorOptions.put(2, "Jogos mais vendidos");
    administradorOptions.put(3, "Jogos menos vendidos");
    administradorOptions.put(4, "Valor mensal vendido");
    administradorOptions.put(5, "Catálogo de Clientes");
    administradorOptions.put(6, "Catálogo de Jogos");
    administradorOptions.put(0, "Voltar");

    gamesRegisterOptions.put(1, "Lancamento");
    gamesRegisterOptions.put(2, "Regular");
    gamesRegisterOptions.put(3, "Promocional");
    gamesRegisterOptions.put(4, "Premium *-*");
    gamesRegisterOptions.put(0, "Voltar");

    administratorMenu.setOptions(administradorOptions);
    createGameMenu.setOptions(gamesRegisterOptions);
  }

  public void switchAdministratorMenu(Scanner input) {
    List<Integer> validOptions = new ArrayList<>();
    validOptions.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));

    administratorMenu.mainMenu();
    int option = Menu.optionHandler(input.nextLine(), validOptions);

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
        System.out.println(Menu.stringer("\n\nClientes cadastrados:\n"));
        ClientMenu.printClients();
        Menu.pausaTeclado(input);
        break;
      case 6:
        System.out.println(Menu.stringer("\n\nJogos Disponíveis:\n"));
        games.clear();
        LeituraDeArquivo.carregarJogosDeArquivoTexto(gamesFile, games);
        printGames();
        Menu.pausaTeclado(input);
        break;
    }
    switchAdministratorMenu(input);
  }

  // #endregion

  // Region gameRegion

  public void createGameMenu(Scanner input) {
    List<Integer> validOptions = new ArrayList<>();
    validOptions.addAll(Arrays.asList(1, 2, 3, 4));

    createGameMenu.mainMenu();
    int option = Menu.optionHandler(input.nextLine(), validOptions);
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

  public void createGame(Scanner input, String categoria) {
    System.out.println(FaberCastel.colorize("\nConte o nome do jogo"));
    String name = input.nextLine();
    double value = -1.0;
    while (value == -1.0) {
      System.out
          .println(FaberCastel
              .colorize("\nBeleza, agora informe o valor do jogo\n(Utilize '.' no lugar de ',')"));
      value = Menu.optionHandler(input.nextLine());
    }
    Jogo novo = todasAsFabricas.create(categoria);
    novo.setNome(name);
    novo.setPreco(value);
    games.put(novo.getNome(), novo);
    escrita.salvarBinario(games, gamesFile);

  }

  public static void printGames() {
    LeituraDeArquivo.carregarJogosDeArquivoTexto(gamesFile, games);

    for (Jogo c : games.values()) {
      System.out.println(Menu.stringer(c.getNome()));
    }
  }
}
