package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import main.domain.Recibo;
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
  private static List<Recibo> recibos = new ArrayList<>();
  private FabricaJogosCollection todasAsFabricas;

  private EscritaDeArquivo<Jogo> escrita = new EscritaDeArquivo<>();
  private static String gamesFilePath;
  private static String recibosFilePath;

  public AdministratorMenu(Map<String, Jogo> jogos, Map<String, Cliente> clients, List<Recibo> recibosList,
      String recibosFilename, String gamesFilePathname,
      FabricaJogosCollection todasAsFabricas) {
    recibos = recibosList;
    recibosFilePath = recibosFilename;
    this.clients = clients;
    games = jogos;
    gamesFilePath = gamesFilePathname;
    this.todasAsFabricas = todasAsFabricas;

    HashMap<Integer, String> gamesRegisterOptions = new HashMap<>();
    HashMap<Integer, String> administradorOptions = new HashMap<>();

    administradorOptions.put(1, "Cadastrar Jogos");
    administradorOptions.put(2, "Jogos mais vendidos");
    administradorOptions.put(3, "Jogos menos vendidos");
    administradorOptions.put(4, "Valor mensal vendido");
    administradorOptions.put(5, "Lista de Clientes");
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
        Jogo maisVendido = jogosMaisVendidos();

        if (maisVendido == null) {
          System.out.println("Nenhum jogo foi vendido");
        } else {
          System.out.println(maisVendido.getNome());
        }
        break;
      case 3:
        // Jogos menos vendidos
        break;
      case 4:
        double total = calcularValorTotalArrecadoMesAtual();
        System.out
            .println(Menu.stringer("Valor total arrecadado no mês " + LocalDate.now().getMonthValue() + " : " + total));

        break;
      case 5:
        System.out.println(Menu.stringer("\n\nClientes cadastrados:\n"));
        ClientMenu.printClients();
        Menu.pausaTeclado(input);
        break;
      case 6:
        System.out.println(Menu.stringer("\n\nJogos Disponíveis:\n"));
        games.clear();
        LeituraDeArquivo.carregarJogosDeArquivoTexto(gamesFilePath, games);
        printGames();
        Menu.pausaTeclado(input);
        break;
    }
    return;
  }

  public double calcularValorTotalArrecadoMesAtual() {
    OptionalDouble acm = recibos.stream()
        .filter(r -> r.getData().getMonth().equals(LocalDate.now().getMonth()))
        .mapToDouble(r -> r.getValor())
        .reduce((r1, r2) -> r1 + r2);

    Menu.pausaTeclado(new Scanner(System.in));
    return acm.orElse(0.0);
  }

  public Jogo jogosMaisVendidos() {
    // Lista todos os jogos
    // Agrupa os jogos (Jogo: numVendas)
    // Ordena pelo valor/numero de vendas
    // Jogo mais vendido retorna o primeiro
    // Jogo menos vendido retorna o ultimo

    List<Jogo> lista = recibos.stream()
        .flatMap(recibo -> recibo.getJogos().stream())
        .collect(Collectors.toList());

    Map<Jogo, Integer> result = new HashMap<>();

    for (Jogo t : lista) {
      Integer val = result.get(t);
      result.put(t, val == null ? 1 : val + 1);
    }

    Entry<Jogo, Integer> max = null;

    for (Entry<Jogo, Integer> e : result.entrySet()) {
      if (max == null || e.getValue() > max.getValue())
        max = e;
    }

    Menu.pausaTeclado(new Scanner(System.in));
    return max.getKey();
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
    escrita.salvarBinario(games, gamesFilePath);
  }

  public static void printGames() {
    LeituraDeArquivo.carregarJogosDeArquivoTexto(gamesFilePath, games);

    for (Jogo c : games.values()) {
      System.out.println(Menu.stringer(c.getNome()));
    }
  }
}