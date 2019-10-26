package fr.minecraftpp.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModGenerator
{
	private static List<ISet> sets = new ArrayList<ISet>();
	
	public static void generateOre()
	{
		Random r = new Random(5);
		
		sets.add(new SimpleSet(r));
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
}
