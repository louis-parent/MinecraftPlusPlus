package fr.minecraftpp.enumeration;

public enum OreProperties
{
	EMPTY("EMPTY"), REDSTONE("redstone"), BLUEDYE("bluedye"), CURRENCY("currency"), BEACON("beacon"), FUEL("fuel"), ENCHANT_CURRENCY("enchant"), MATERIAL("material"), METAL("metal");

	private final String name;

	private OreProperties(String name)
	{
		this.name = name;
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
}
