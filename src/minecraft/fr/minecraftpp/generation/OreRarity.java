package fr.minecraftpp.generation;

import java.util.List;
import java.util.Random;

import fr.minecraftpp.enumeration.OreProperties;

public class OreRarity
{
	private static final int DEFAULT_VEIN_AMOUNT = 15;
	private static final int DEFAULT_VEIN_DENSITY = 18;
	private static final int DEFAULT_HEIGHT = 128;

	private static final int MIN_VEIN_AMOUNT = 1;
	private static final int MIN_VEIN_DENSITY = 5;
	private static final int MIN_HEIGHT = 8;

	private int veinAmount;
	private int veinDensity;
	private int maxHeight;

	private OreRarity()
	{
		this(DEFAULT_VEIN_AMOUNT, DEFAULT_VEIN_DENSITY, DEFAULT_HEIGHT);
	}

	private OreRarity(int veinAmount, int veinDensity, int maxHeight)
	{
		this.veinAmount = veinAmount;
		this.veinDensity = veinDensity;
		this.maxHeight = maxHeight;
	}

	public int getVeinAmount()
	{
		return veinAmount;
	}

	public int getVeinDensity()
	{
		return veinDensity;
	}

	public int getMaxHeight()
	{
		return maxHeight;
	}

	public static OreRarity getRarityFrom(List<OreProperties> properties, Random rand)
	{
		OreRarity rarity = OreRarity.getForTier(OreProperties.getMaxTier(properties), rand);

		rarity.applyProperties(properties);

		return rarity;
	}

	private static OreRarity getForTier(int tier, Random rand)
	{
		OreRarity rarity = new OreRarity();

		if (tier == OreProperties.UNDEFINED_TIER)
		{
			tier = rand.nextInt(4);
		}

		rarity.setMaxHeight((int) (DEFAULT_HEIGHT * Math.pow(2, -tier)));
		return rarity;
	}

	public void setVeinAmount(int veinAmount)
	{
		this.veinAmount = Math.max(veinAmount, MIN_VEIN_AMOUNT);
	}

	public void setVeinDensity(int veinDensity)
	{
		this.veinDensity = Math.max(veinDensity, MIN_VEIN_DENSITY);
	}

	private void setMaxHeight(int height)
	{
		this.maxHeight = Math.max(height, MIN_HEIGHT);
	}

	private void applyProperties(List<OreProperties> properties)
	{
		for (OreProperties oreProperties : properties)
		{
			this.increase(oreProperties.getRarity());
		}
	}

	private void increase(int times)
	{
		for (int i = 0; i < times; i++)
		{
			this.increase();
		}
	}

	private void increase()
	{
		this.setVeinAmount(this.getVeinAmount() - 3);
		this.setVeinDensity(this.getVeinDensity() - 2);
	}
}
