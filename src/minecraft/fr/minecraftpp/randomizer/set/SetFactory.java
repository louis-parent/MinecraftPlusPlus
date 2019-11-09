package fr.minecraftpp.randomizer.set;

import java.util.List;
import java.util.Random;

import fr.minecraftpp.enumeration.OreProperties;
import fr.minecraftpp.generation.OreRarity;

public class SetFactory
{

	public static ISet generateSet(List<OreProperties> properties, Random rand)
	{
		SimpleSet newSet = createProperSet(properties, rand);

		generateProperties(newSet, properties);

		return newSet;
	}

	private static SimpleSet createProperSet(List<OreProperties> properties, Random rand)
	{
		SimpleSet newSet;
		if (properties.contains(OreProperties.METAL))
		{
			newSet = new MetalSet(rand, OreRarity.getRarityFrom(properties, rand));
		}
		else if (properties.contains(OreProperties.MATERIAL))
		{

			newSet = new MaterialSet(rand, OreRarity.getRarityFrom(properties, rand));
		}
		else
		{
			newSet = new SimpleSet(rand, OreRarity.getRarityFrom(properties, rand));
		}
		return newSet;
	}

	private static void generateProperties(SimpleSet newSet, List<OreProperties> properties)
	{
		for (OreProperties oreProperties : properties)
		{
			switch (oreProperties)
			{
				case REDSTONE:
					newSet.setRedstone();
					break;
				case BLUEDYE:
					newSet.setBlueDye();
					break;
				case CURRENCY:
					newSet.setCurrency();
					break;
				case BEACON:
					newSet.setBeacon();
					break;
				case FUEL:
					newSet.setFuel();
					break;
				case ENCHANT_CURRENCY:
					newSet.setEnchantCurrency();
					break;
				case COAL:
					newSet.setCoal();
					break;
				case IRON:
					newSet.setIron();
					break;
				case DIAMOND:
					newSet.setDiamond();
					break;
				default:
					break;
			}
		}

	}
}
