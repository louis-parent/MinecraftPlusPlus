package fr.minecraftpp.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.minecraftpp.generator.set.ISet;
import fr.minecraftpp.generator.set.MaterialSet;
import fr.minecraftpp.generator.set.MetalSet;
import fr.minecraftpp.generator.set.SimpleSet;
import net.minecraft.client.renderer.color.ItemColors;

public class SetManager
{
	private static List<ISet> sets = new ArrayList<ISet>();
	
	public static void generateOre()
	{
		Random r = new Random(5);
		
		sets.add(new SimpleSet(r));
		sets.add(new MaterialSet(r));
		sets.add(new MetalSet(r));
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
	
	public static void registerColors(ItemColors itemcolors)
	{
		for (ISet set : sets)
		{
			set.registerColors(itemcolors);
		}
	}
}
