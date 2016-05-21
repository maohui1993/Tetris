package org.oucho.tetris;


class Box {
	private int color;

	/* ************************************************
	 * The color is also used to indicate the box ****
	 * state. '0' means that the box is free *********
	 * ************************************************/
	public int getColor() {
		return color;
	}

	/* ************************************************
	 * The color is also used to indicate the box
	 * state. Setting color to '0' is marking the
	 * box as free
	 * ************************************************/
	public void setColor(int color) {
		this.color = color;
	}

	/* ************************************************
	 * Defines the position and size of the box and
	 * marks it as free
	 * ************************************************/
	public Box(){
		color = Values.COLOR_NONE;
	}
}
