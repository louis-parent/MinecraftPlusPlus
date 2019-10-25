package fr.minecraftpp.block;

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
}
