package fr.minecraftpp.item;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod("minecraftpp")
public class ModItems extends Items
{
	public static Item SCENARIUM;
	public static Item SCENARIUM_SWORD;
	public static Item SCENARIUM_PICKAXE;
	public static Item SCENARIUM_AXE;
	public static Item SCENARIUM_SPADE;
	public static Item SCENARIUM_HOE;

	public static void staticSetter()
	{
		SCENARIUM = getRegisteredItem("scenarium");
		SCENARIUM_SWORD = getRegisteredItem("scenarium_sword");
		SCENARIUM_PICKAXE = getRegisteredItem("scenarium_pickaxe");
		SCENARIUM_AXE = getRegisteredItem("scenarium_axe");
		SCENARIUM_SPADE = getRegisteredItem("scenarium_spade");
		SCENARIUM_HOE = getRegisteredItem("scenarium_hoe");
	}
}
