package fr.minecraftpp.generator;

import java.util.Random;

import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.util.NameGenerator;

public class ModGenerator
{
	public static void generateOre()
	{
		Random r = new Random();
		String name = NameGenerator.generateName();
		
		new DynamicItem(name, r.nextInt(6) + 1, false, 0, false, false);
	}
}
