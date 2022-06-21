package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class FaberCastel {
	private final static String YELLOW = UiColors.YELLOW.colorize();
	private final static String RED = UiColors.RED.colorize();
	private final static String BLACK = UiColors.BLACK.colorize();
	private final static String GREEN = UiColors.GREEN.colorize();
	private final static String BLUE = UiColors.BLUE.colorize();
	private final static String PURPLE = UiColors.PURPLE.colorize();
	private final static String CIAN = UiColors.CIAN.colorize();
	private final static String WHITE = UiColors.WHITE.colorize();

	private final static String BG_BLACK = UiColors.BGBLACK.colorize();
	private final static String BG_RED = UiColors.BGRED.colorize();
	private final static String BG_GREEN = UiColors.BGGREEN.colorize();
	private final static String BG_YELLOW = UiColors.BGYELLOW.colorize();
	private final static String BG_BLUE = UiColors.BGBLUE.colorize();
	private final static String BG_PURPLE = UiColors.BGPURPLE.colorize();
	private final static String BG_CIAN = UiColors.BGCIAN.colorize();
	private final static String BG_WHITE = UiColors.BGWHITE.colorize();

	private final static String RESET = UiColors.RESET.colorize();

	public static String getRandomColor() {
		Random rand = new Random();

		ArrayList<String> colors = new ArrayList<>();
		colors.addAll(Arrays.asList(YELLOW, RED, BLACK, GREEN, BLUE, PURPLE, CIAN, WHITE));

		int number = rand.nextInt() & colors.size();

		return colors.get(rand.nextInt(colors.size()));
	}

	public static String colorize(String text) {
		StringBuilder sb = new StringBuilder();

		IntStream modText = text.chars();

		modText.mapToObj(Character::toChars)
				.forEach(c -> sb.append(getRandomColor() + String.copyValueOf(c) + RESET));

		return sb.toString();
	}

	public static String inRed(String text) {
		StringBuilder sb = new StringBuilder();
		sb.append(RED + text + RESET);
		return sb.toString();
	}

	public static String inCian(String text) {
		StringBuilder sb = new StringBuilder();

		sb.append(CIAN + text + RESET);

		return sb.toString();
	}

	public static String inGreen(String text) {
		StringBuilder sb = new StringBuilder();

		sb.append(GREEN + text + RESET);

		return sb.toString();
	}
}
