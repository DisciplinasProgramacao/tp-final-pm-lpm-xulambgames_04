package ui;

import java.util.HashMap;

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

		printer(sb.toString());
	}

	public void subMenu() {
		StringBuilder sb = new StringBuilder();
		clearScreen();
		sb.append(Header());

		printer(sb.toString());

	}

	private void printer(String text) {
		if (this.wColor) {
			System.out.println(FaberCastel.colorize(text));
		} else
			System.out.println(text);
	}
}
