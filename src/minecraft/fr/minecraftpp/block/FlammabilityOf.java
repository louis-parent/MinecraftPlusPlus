package fr.minecraftpp.block;

import java.util.Random;

import fr.minecraftpp.anotation.Mod;

@Mod("Minecraftpp")
public enum FlammabilityOf
{
	STONE(0, 0, false), PLANKS(5, 20, false), LOG(5, 5, false), LEAVES(30, 60, false), PLANT(60, 100, false), HAY(60, 20, false), VINE(15, 100, false), BOOKSHELF(30, 20, false), NETHERRACK(0, 0, true);

	private final int encouragement;
	private final int flammability;
	private final boolean isInfinite;

	FlammabilityOf(int encouragement, int flammability, boolean isInfinite)
	{
		this.encouragement = encouragement;
		this.flammability = flammability;
		this.isInfinite = isInfinite;
	}

	public int getEncouragement()
	{
		return encouragement;
	}

	public int getFlammability()
	{
		return flammability;
	}

	public boolean isInfinite()
	{
		return isInfinite;
	}

	public boolean isFlammable()
	{
		return this.flammability != 0 && this.encouragement != 0;
	}
	
	public static FlammabilityOf getRandomFlammability(Random rand)
	{
		return FlammabilityOf.values()[rand.nextInt(FlammabilityOf.values().length)];
	}
}
