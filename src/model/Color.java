package model;

import java.util.Arrays;

public class Color {
	private int first;
	private int second;
	private int third;
	private int percentage;
	
	public Color() {}
	
	public Color(int first, int second, int third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public int getFirst() {
		return first;
	}
	
	public Color setFirst(int first) {
		this.first = first;
		return this;
	}
	
	public int getSecond() {
		return second;
	}
	
	public Color setSecond(int second) {
		this.second = second;
		return this;
	}
	
	public int getThird() {
		return third;
	}
	
	public Color setThird(int third) {
		this.third = third;
		return this;
	}

	public static Color create(double[] ds) {
		System.out.println(Arrays.toString(ds));
		return new Color((int)ds[0], (int)ds[1], (int)ds[2]);
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	public Color setPercentage(int percentage) {
		this.percentage = percentage;
		return this;
	}

	@Override
	public String toString() {
		return "Color [first=" + first + ", second=" + second + ", third=" + third + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first;
		result = prime * result + second;
		result = prime * result + third;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (first != other.first)
			return false;
		if (second != other.second)
			return false;
		if (third != other.third)
			return false;
		return true;
	}

	public int distance(Color color) {
		int f = this.first - color.first;
		int s = this.second - color.second;
		int t = this.third - color.third;
		return f * f + s * s + t * t;
	}
}
