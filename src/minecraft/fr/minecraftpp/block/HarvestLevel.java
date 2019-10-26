package fr.minecraftpp.block;

import java.util.Random;

public enum HarvestLevel
{
	WOOD,
	STONE,
	IRON,
	DIAMOND;
	
	public int getHarvestLevel()
	{
		return this.ordinal();
	}

	public static HarvestLevel getRandomHarvestLevel(Random rng)
	{
		return HarvestLevel.values()[rng.nextInt(HarvestLevel.values().length)];
	}
}
