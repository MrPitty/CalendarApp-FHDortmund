package Appointment;

import java.awt.*;

public class Day {
	double x, y;
	boolean isEmpty;
	Color color;

	public Day(double x, double y, int count, Color color) {
		this.x = x;
		this.y = y;
		isEmpty = count == 0;
		this.color = color;
	}

	public double getXCoord() {
		return x;
	}

	public double getYCoord() {
		return y;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public Color getColor() {
		return color;
	}
}
