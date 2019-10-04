package fr.minecraftpp.item;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod("minecraftpp")
public class ModItems extends Items
{
	public static Item SCENARIUM;
	
	public static void staticSetter()
	{
		SCENARIUM = getRegisteredItem("scenarium");
	}
}
