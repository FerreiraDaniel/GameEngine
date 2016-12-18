package com.dferreira.commons;

/**
 * Represents one color with (Red, Green, Blue, Alpha blend) components
 */
public class ColorRGBA extends ColorRGB {

	/**
	 * Alpha blend component
	 */
	public float a;

	/**
	 * @param r
	 *            Red component
	 * @param g
	 *            Green component
	 * @param b
	 *            Blue component
	 * @param a
	 *            Alpha blend
	 */
	public ColorRGBA(float r, float g, float b, float a) {
		super(r, g, b);
		this.a = a;
	}

	/**
	 * 
	 * @param color	Creates one rgba color from a RGB
	 */
	public ColorRGBA(ColorRGB color) {
		super(color.r, color.g, color.b);
		this.a = 1.0f;
	}
}
