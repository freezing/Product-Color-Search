package model;

public class Color {
	private int red;
	private int green;
	private int blue;
	
	public Color() {}
	
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getRed() {
		return red;
	}
	
	public Color setRed(int red) {
		this.red = red;
		return this;
	}
	
	public int getGreen() {
		return green;
	}
	
	public Color setGreen(int green) {
		this.green = green;
		return this;
	}
	
	public int getBlue() {
		return blue;
	}
	
	public Color setBlue(int blue) {
		this.blue = blue;
		return this;
	}	
}
