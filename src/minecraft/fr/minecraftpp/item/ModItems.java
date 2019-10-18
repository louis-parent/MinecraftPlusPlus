package fr.minecraftpp.item;

import java.lang.reflect.Field;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.language.ModLanguage;
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

	public static Item SCENARIUM_HELMET;
	public static Item SCENARIUM_CHESTPLATE;
	public static Item SCENARIUM_LEGGINGS;
	public static Item SCENARIUM_BOOTS;

	public static void staticSetter()
	{
		SCENARIUM = getRegisteredItem("scenarium");

		SCENARIUM_SWORD = getRegisteredItem("scenarium_sword");
		SCENARIUM_PICKAXE = getRegisteredItem("scenarium_pickaxe");
		SCENARIUM_AXE = getRegisteredItem("scenarium_axe");
		SCENARIUM_SPADE = getRegisteredItem("scenarium_spade");
		SCENARIUM_HOE = getRegisteredItem("scenarium_hoe");

		SCENARIUM_HELMET = getRegisteredItem("scenarium_helmet");
		SCENARIUM_CHESTPLATE = getRegisteredItem("scenarium_chestplate");
		SCENARIUM_LEGGINGS = getRegisteredItem("scenarium_leggings");
		SCENARIUM_BOOTS = getRegisteredItem("scenarium_boots");

		doNames();
	}

	public static void doNames()
	{
		for (Field field : ModItems.class.getDeclaredFields())
		{
			try
			{
				if (field.get(null) instanceof Item)
				{
					Item item = (Item) field.get(null);
					ModLanguage.addTranslation(item);

				}
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
