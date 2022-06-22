package util;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import main.domain.cliente.Cliente;
import main.domain.jogo.Jogo;

public class LeituraDeArquivo {
	
	public LeituraDeArquivo() {
		
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
}
