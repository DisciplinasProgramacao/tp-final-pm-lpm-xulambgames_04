package util;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import main.domain.Recibo;
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
					file.close();
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
					file.close();
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

	public static void carregarRecibosDeArquivoTexto(String nomeAqr, List<Recibo> list) {
		try {
			Recibo novo = null;
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(nomeAqr));
			do {
				try {
					novo = (Recibo) file.readObject();
				} catch (EOFException e) {
					file.close();
					return;
				}
				if (novo != null) {
					list.add(novo);
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
