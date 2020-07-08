package fr.minecraftpp.enumeration;

import java.util.List;

public enum OreProperties
{
	EMPTY("EMPTY", OreProperties.UNDEFINED_TIER, 0), BLUEDYE("bluedye", OreProperties.UNDEFINED_TIER, 0), CURRENCY("currency", OreProperties.UNDEFINED_TIER, 5), BEACON("beacon", OreProperties.UNDEFINED_TIER, 2), FUEL("fuel", OreProperties.UNDEFINED_TIER, 1), MATERIAL("material", OreProperties.UNDEFINED_TIER, 1), METAL("metal", OreProperties.UNDEFINED_TIER, 0), COAL("coal", 0, 0), IRON("iron", 1, 1), GOLD("gold", 2, 2), ENCHANT_CURRENCY("enchant", 2, 3), REDSTONE("redstone", 3, 5), DIAMOND("diamond", 3, 3);

	public static final int UNDEFINED_TIER = -1;

	private final String name;
	private final int tier;
	private final int rarity;

	private OreProperties(String name, int tier, int rarity)
	{
		this.name = name;
		this.tier = tier;
		this.rarity = rarity;
	}

	public int getTier()
	{
		return this.tier;
	}

	public int getRarity()
	{
		return this.rarity;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public static OreProperties fromString(String value)
	{
		for (OreProperties prop : OreProperties.values())
		{
			if (value.contains(prop.toString()))
			{
				return prop;
			}
		}

		return EMPTY;
	}

	public static int getMaxTier(List<OreProperties> properties)
	{
		int max = UNDEFINED_TIER;

		for (OreProperties prop : properties)
		{
			if (prop.getTier() > max)
			{
				max = prop.getTier();
			}
		}

		return max;
	}
}
