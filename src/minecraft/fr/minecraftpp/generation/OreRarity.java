package fr.minecraftpp.generation;

import java.util.List;
import java.util.Random;

import fr.minecraftpp.enumeration.OreProperties;

public class OreRarity
{
	private static final int MAX_VEIN_AMOUNT = 20;
	private static final int MAX_VEIN_DENSITY = 20;
	private static final int MAX_HEIGHT = 128;

	private static final int DEFAULT_VEIN_AMOUNT = 1;
	private static final int DEFAULT_VEIN_DENSITY = 5;
	private static final int DEFAULT_HEIGHT = 16;

	private int veinAmount;
	private int veinDensity;
	private int maxHeight;
	
	private Random rand;

	private OreRarity(Random rand)
	{
		this(DEFAULT_VEIN_AMOUNT, DEFAULT_VEIN_DENSITY, DEFAULT_HEIGHT, rand);
	}

	private OreRarity(int veinAmount, int veinDensity, int maxHeight, Random rand)
	{
		this.veinAmount = veinAmount;
		this.veinDensity = veinDensity;
		this.maxHeight = maxHeight;
		
		this.rand = rand;
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
		OreRarity rarity = new OreRarity(rand);

		if (tier == OreProperties.UNDEFINED_TIER)
		{
			tier = rand.nextInt(4);
		}

		rarity.setMaxHeight((int) (MAX_HEIGHT * Math.pow(2, -tier)));
		return rarity;
	}

	public void setVeinAmount(int veinAmount)
	{
		this.veinAmount = Math.min(veinAmount, MAX_VEIN_AMOUNT);
	}

	public void setVeinDensity(int veinDensity)
	{
		this.veinDensity = Math.min(veinDensity, MAX_VEIN_DENSITY);
	}

	private void setMaxHeight(int height)
	{
		this.maxHeight = Math.min(height, MAX_HEIGHT);
	}

	private void applyProperties(List<OreProperties> properties)
	{
		for (OreProperties oreProperties : properties)
		{
			this.decreaseRarity(oreProperties.getUsefullness());
		}
		
		int bonus = 0;
		
		int generatedInt = this.rand.nextInt(12) + 1;

		bonus += generatedInt > 6 ? 1 : 0;
		bonus += generatedInt > 9 ? 1 : 0;
		bonus += generatedInt == 12 ? 1 : 0;
		
		this.decreaseRarity(bonus);
	}

	private void decreaseRarity(int times)
	{
		for (int i = 0; i < times; i++)
		{
			this.decreaseRarity();
		}
	}

	private void decreaseRarity()
	{
		int maxIncrease = 4;
		int separation = this.rand.nextInt(maxIncrease + 1);
		this.setVeinAmount(this.getVeinAmount() + separation);
		this.setVeinDensity(this.getVeinDensity() + (maxIncrease - separation));
	}
}
