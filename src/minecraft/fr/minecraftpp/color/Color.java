package fr.minecraftpp.color;

import java.util.Random;

import fr.minecraftpp.util.normalDistribution.NormalDistribution;

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

	public static Color getRandomColorImproved(Random rand)
	{
		int R = generateColorIndex(rand);
		int G = generateColorIndex(rand);
		int B = generateColorIndex(rand);

		return new Color(R, G, B);
	}

	@Override
	public String toString()
	{
		return "{R: " + this.red + ", G: " + this.green + ", B: " + this.blue + "}";
	}

	private static int generateColorIndex(Random rand)
	{
		boolean isDark = rand.nextBoolean();

		if (!isDark)
		{
			return (int) (normalCDF(rand) * 255);
		}
		else
		{
			return (int) (255 - (normalCDF(rand) * 255));
		}
	}

	private static double normalCDF(Random rand)
	{
		try
		{
			NormalDistribution nd = new NormalDistribution(rand, 127, 74);
			return nd.cumulativeProbability(rand.nextInt(256));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}
}
