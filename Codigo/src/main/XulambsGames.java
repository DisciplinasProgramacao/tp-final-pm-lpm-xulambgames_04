package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class XulambsGames {

	public static void main(String[] args) {
		Scanner ler = new Scanner(System.in);
		final String nomeArq = "Clientes.txt";
		// EscritaArq arquivo;
		// LeituraArq leituraArq = new LeituraArq(nomeArq);
		// ArrayList<Cliente> clientes = leituraArq.lerArquivoBinario();

		boolean sair = false;
		int opCliente = 0;

		String nomeGame;
		double precoGame;

		System.out.println("=============XulambsGames===============");
		System.out.println("\nSEJA BEM VINDO(A)\n");

		while (sair == false) {
			System.out.println("O que deseja?\n");
			System.out.println("1-Cadastrar-se");
			System.out.println("2-Localizar Cliente");
			System.out.println("3-Comprar um  jogo");
			System.out.println("4-Ver seu historico de compras");
			System.out.println("5-sair");

			switch (ler.nextInt()) {
				case 1:
					// #region categorias
					System.out.println("Selecione uma Categoria\n");
					System.out.println("1-EMPOLGADO");
					System.out.println("2-FANATICO");
					System.out.println("3-CADASTRADO");
					// #endregion
					switch (ler.nextInt()) {
						case 1:
							// Colocar Aqui os tipos de Cliente
							break;

						default:
							break;
					}

					break;

				case 2:
					// Localizar um Cliente especifico
					break;
				case 3:

					// #region categorias

					System.out.print("Digite o nome do jogo: ");
					nomeGame = ler.next();
					System.out.print("Digite o preco do jogo: ");
					precoGame = validarDouble(ler.next());

					if (precoGame == 0.0)
						break;

					System.out.println("Selecione uma Categoria\n");
					System.out.println("1-Premium");
					System.out.println("2-Promocional");
					System.out.println("3-Regular");
					// #endregion

					switch (ler.nextInt()) {

						case 1:
							// Colocar Aqui os tipos de Jogos
							break;

						default:
							break;
					}
					break;

				case 4:
					// Colocar Historico de clientes
					break;
				case 5:
					// Sair do Sitema e salvar dados no arquivo
					// arquivo = new EscritaArq(clientes, nomeArq);
					// arquivo.gravarArquivoBinario();
					sair = true;

					break;

				default:
					break;
			}

		}

		System.out.println("\nVOLTE SEMPRE");
	}

	private static double validarDouble(String preco) {
		double retornar = 0;

		try {
			retornar = Double.parseDouble(preco);
		} catch (Exception e) {
			System.out.println("\nNão foi possivel adicionar o jogo");
		}
		return retornar;
	}
}
