package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import ui.Menu;
import ui.UiColors;

public class EscritaDeArquivo<T> {

	public EscritaDeArquivo() {

	}

	public void salvarBinario(Map<String, T> objects, String arq) {
		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(new FileOutputStream(arq));
			for (T obj : objects.values()) {
				saida.writeObject(obj);
			}
			saida.close();
			System.out.println(Menu.stringer("Cadastro efetuado com sucesso! ", UiColors.GREEN));
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
