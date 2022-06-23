package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import ui.Menu;
import ui.UiColors;

/**
 * Classe de escrita de arquivo definida por uma classe T.
 */
public class EscritaDeArquivo<T> {

	public EscritaDeArquivo() {

	}

	/**
	 * Salva uma lista de T objetos contidos em um Map
	 * 
	 * @param objects
	 * @param arquivo
	 */
	public void salvarBinario(Map<String, T> objects, String arq) {
		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(new FileOutputStream(arq));
			for (T obj : objects.values()) {
				saida.writeObject(obj);
			}
			saida.close();
			System.out.println(Menu.stringer("\nSalvo!", UiColors.GREEN));
		} catch (FileNotFoundException fe) {
			System.out.println("Arquivo não encontrado, ou permissão negada. Tente novamente com outro arquivo");
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("Problemas na operação de E/S. Contacte o suporte");
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Salva uma lista de T objetos contidos em uma lista
	 * 
	 * @param objects
	 * @param arquivo
	 */
	public void salvarBinario(List<T> objects, String arq) {
		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(new FileOutputStream(arq));
			for (T obj : objects) {
				saida.writeObject(obj);
			}
			saida.close();
		} catch (FileNotFoundException fe) {
			System.out.println("Arquivo não encontrado, ou permissão negada. Tente novamente com outro arquivo");
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("Problemas na operação de E/S. Contacte o suporte");
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}
}
