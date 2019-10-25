package fr.minecraftpp.item;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlock;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.generator.IDynamicBlock;
import fr.minecraftpp.generator.IDynamicItem;
import fr.minecraftpp.item.armor.ItemBoots;
import fr.minecraftpp.item.armor.ItemChestplate;
import fr.minecraftpp.item.armor.ItemHelmet;
import fr.minecraftpp.item.armor.ItemLeggings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

@Mod("minecraftpp")
public class ModItem extends Item
{
	public static List<IDynamicItem> REGISTRY = new ArrayList<IDynamicItem>();
	private static final int ID_START = 1266;
	
	public static MaterialScenarium MATERIAL_SCENARIUM = new MaterialScenarium();

	public static void registerBlockItems()
	{
		registerItemBlock(ModBlocks.SCENARITE_ORE);
		registerItemBlock(ModBlocks.SCENARIUM_BLOCK);
		
		for(IDynamicBlock block : ModBlock.REGISTRY)
		{
			registerItemBlock(block.getBlock());
		}
	}

	public static void registerItems()
	{
		registerItem(1256, "scenarium", new ItemScenarium());

		registerItem(1257, "scenarium_sword", new ItemSword(MATERIAL_SCENARIUM).setUnlocalizedName("scenariumSword"));
		registerItem(1258, "scenarium_pickaxe", new ItemPickaxe(MATERIAL_SCENARIUM).setUnlocalizedName("scenariumPickaxe"));
		registerItem(1259, "scenarium_axe", new ItemAxe(MATERIAL_SCENARIUM).setUnlocalizedName("scenariumAxe"));
		registerItem(1260, "scenarium_spade", new ItemSpade(MATERIAL_SCENARIUM).setUnlocalizedName("scenariumShovel"));
		registerItem(1261, "scenarium_hoe", new ItemHoe(MATERIAL_SCENARIUM).setUnlocalizedName("scenariumHoe"));

		registerItem(1262, "scenarium_helmet", (new ItemHelmet(MATERIAL_SCENARIUM)).setUnlocalizedName("scenariumHelmet"));
		registerItem(1263, "scenarium_chestplate", (new ItemChestplate(MATERIAL_SCENARIUM)).setUnlocalizedName("scenariumChestplate"));
		registerItem(1264, "scenarium_leggings", (new ItemLeggings(MATERIAL_SCENARIUM)).setUnlocalizedName("scenariumLeggings"));
		registerItem(1265, "scenarium_boots", (new ItemBoots(MATERIAL_SCENARIUM)).setUnlocalizedName("scenariumBoots"));
				
		for (int i = 0; i < REGISTRY.size(); i++)
		{
			IDynamicItem item = REGISTRY.get(i);
			registerItem(ID_START + i, item.getID(), item.getItem());
		}
	}
	
	public static void setItemToRegister(IDynamicItem item)
	{
		REGISTRY.add(item);
	}
}
