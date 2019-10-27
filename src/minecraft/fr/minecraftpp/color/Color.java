package fr.minecraftpp.color;

import java.util.Random;

public class Color
{
	public static final Color WHITE = new Color(255, 255, 255);
	
	private int red;
	private int green;
	private int blue;
	
	public Color(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int asInt()
	{
		int rgbCondensed = (this.red << 16) + (this.green << 8) + this.blue;
		return rgbCondensed;
	}
	
	public static Color getRandomColor(Random rand)
	{
		return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	}
}
