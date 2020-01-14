package fr.minecraftpp.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.minecraftpp.enumeration.OreProperties;
import fr.minecraftpp.randomizer.backtrack.Backtrack;
import fr.minecraftpp.randomizer.set.ISet;
import fr.minecraftpp.randomizer.set.SetFactory;
import fr.minecraftpp.variant.Variant;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;

public class SetManager
{
	private static final int NUMBER_OF_ORES = 7;

	private static List<ISet> sets;

	public static void generateOre()
	{
		Random r = new Random(1999);
		
		Variant.initInstance(r);
		
		sets = new ArrayList<ISet>();
		
		Map<Integer, List<OreProperties>> sortedSolution = getSortedSolution(Backtrack.generateSolution(r, NUMBER_OF_ORES));

		for (List<OreProperties> properties : sortedSolution.values())
		{
			sets.add(SetFactory.generateSet(properties, r));
		}

		for (int i = 0; i < sets.size(); i++)
		{
			System.out.println(sets.get(i).getSetName() + ": " + sortedSolution.values().toArray()[i]);
		}
	}

	public static void register()
	{
		for (ISet set : sets)
		{
			set.register();
		}
	}

	public static void setupEffects()
	{
		for (ISet set : sets)
		{
			set.setupEffects();
		}
	}

	public static void generateRecipes()
	{
		for (ISet set : sets)
		{
			set.addRecipes();
		}
	}

	public static void registerBlockColors(BlockColors blockColors)
	{
		for (ISet set : sets)
		{
			set.registerBlockColors(blockColors);
		}
	}

	public static void registerItemColors(ItemColors itemColors)
	{
		for (ISet set : sets)
		{
			set.registerItemColors(itemColors);
		}
	}

	private static Map<Integer, List<OreProperties>> getSortedSolution(Map<String, Integer> solution)
	{
		Map<Integer, List<OreProperties>> sortedSolution = new HashMap<Integer, List<OreProperties>>();

		for (Map.Entry<String, Integer> entry : solution.entrySet())
		{
			Integer value = entry.getValue();
			String key = entry.getKey();

			if (!sortedSolution.containsKey(value))
			{
				sortedSolution.put(value, new ArrayList<OreProperties>());
			}

			sortedSolution.get(value).add(OreProperties.fromString(key));
		}

		return sortedSolution;
	}
}
