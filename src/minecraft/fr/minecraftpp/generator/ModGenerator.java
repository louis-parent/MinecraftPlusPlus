package fr.minecraftpp.generator;

import java.util.Random;

import fr.minecraftpp.item.DynamicItem;

public class ModGenerator
{
	public static void generateOre()
	{
		Random r = new Random();
		
		new DynamicItem(r.nextInt(6) + 1, false, 0, false, false);
	}
}
