package fr.minecraftpp.item.crafting;

import java.util.HashMap;
import java.util.Map;

import fr.minecraftpp.item.ModItems;
import net.minecraft.item.Item;

public class ModFuel
{
	private static Map<Item, Integer> burnTime = new HashMap<Item, Integer>();
	
	public static boolean isFuel(Item item)
	{
		return burnTime.containsKey(item);
	}
	
	public static int getBurnTimeFor(Item item)
	{
		return burnTime.get(item);
	}
	
	public static void registerBurnTime()
	{
		burnTime.put(ModItems.SCENARIUM, 1600);
	}
}
