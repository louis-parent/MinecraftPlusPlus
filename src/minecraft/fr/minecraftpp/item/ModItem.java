package fr.minecraftpp.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSimpleFoiled;

@Mod("minecraftpp")
public class ModItem extends Item
{
	public static void registerBlockItems()
	{
        registerItemBlock(ModBlocks.SCENARITE_ORE);
        registerItemBlock(ModBlocks.SCENARIUM_BLOCK);
	}
	
	public static void registerItems()
	{
		registerItem(1256, "scenarium", new ItemScenarium());
	}
}
