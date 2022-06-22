package ui;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
	private String name;
	private String description;
	private static boolean wColor;

	private HashMap<Integer, String> options;

	public Menu(String name, String description, HashMap<Integer, String> options) {
		this.name = name;
		this.options = options;
		this.description = description;
		wColor = false;
	}

	public Menu(String name, String description) {
		this.name = name;
		this.description = description;
		wColor = false;
	}

	public static void wColor(boolean color) {
		wColor = color;
	}

	public void setOptions(HashMap<Integer, String> options) {
		this.options = options;
	}

	public static void clearScreen() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	private String Header() {
		StringBuilder sb = new StringBuilder();

		sb.append("===================== " + this.name + " =====================\n"
				+ "                        " + this.description + "\n");

		sb.append("=====================");

		for (int i = 0; i < this.name.length() + 1; i++) {
			sb.append("=");
		}
		sb.append("======================\n");

		return sb.toString();
	}

	private String options() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		options.forEach((key, value) -> {
			sb.append(key + " - " + value + "\n");
		});
		return sb.toString();
	}

	public void mainMenu() {
		StringBuilder sb = new StringBuilder();
		clearScreen();
		sb.append(Header());
		sb.append(options());

		System.out.println(stringer(sb.toString()));
	}

	public void subMenu() {
		StringBuilder sb = new StringBuilder();
		clearScreen();
		sb.append(Header());

		System.out.println(stringer(sb.toString()));

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
		double opt;
		try {
			opt = Double.valueOf(input);
		} catch (Exception e) {
			System.out.println(Menu.stringer("\n!! Valor não aceito !!\n"));
			return -1.0;
		}
		return opt;
	}

	public static String stringer(String string) {
		return wColor ? FaberCastel.colorize(string) : string;
	}

	public static String stringer(String string, UiColors color) {
		return wColor ? FaberCastel.inColor(string, color) : string;
	}

	public static void pausaTeclado(Scanner input) {
		System.out.println(stringer("\nPressione qualquer tecla para continuar"));
		try {
			input.nextLine();
		} catch (NoSuchElementException e) {
			return;
		}
	}
}
