package ui;

public enum UiColors {
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CIAN("\u001B[36m"),
	WHITE("\u001B[37m"),
	
	BGBLACK("\u001B[40m"),
	BGRED("\u001B[41m"),
	BGGREEN("\u001B[42m"),
	BGYELLOW("\u001B[43m"),
	BGBLUE("\u001B[44m"),
	BGPURPLE("\u001B[45m"),
	BGCIAN("\u001B[46m"),
	BGWHITE("\u001B[47m"),
	
	RESET("\u001B[0m");

	private String ANSI_COLORNAME;
	
	UiColors(String color) {
		this.ANSI_COLORNAME = color;
	}
	
	public String colorize() {
		return this.ANSI_COLORNAME;
	}
}

