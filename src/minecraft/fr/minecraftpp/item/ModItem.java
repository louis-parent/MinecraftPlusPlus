package fr.minecraftpp.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.item.armor.ItemBoots;
import fr.minecraftpp.item.armor.ItemChestplate;
import fr.minecraftpp.item.armor.ItemHelmet;
import fr.minecraftpp.item.armor.ItemLeggings;
import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.ToolType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

@Mod("minecraftpp")
public class ModItem extends Item
{	
	public static MaterialScenarium MATERIA_SCENARIUM = new MaterialScenarium();
	
	public static void registerBlockItems()
	{
        registerItemBlock(ModBlocks.SCENARITE_ORE);
        registerItemBlock(ModBlocks.SCENARIUM_BLOCK);
	}
	
	public static void registerItems()
	{
		registerItem(1256, "scenarium", new ItemScenarium());
		
		registerItem(1257, "scenarium_sword", new ItemSword(MATERIA_SCENARIUM).setUnlocalizedName("scenariumSword"));
		registerItem(1258, "scenarium_pickaxe", new ItemPickaxe(MATERIA_SCENARIUM).setUnlocalizedName("scenariumPickaxe"));
		registerItem(1259, "scenarium_axe", new ItemAxe(MATERIA_SCENARIUM).setUnlocalizedName("scenariumAxe"));
		registerItem(1260, "scenarium_spade", new ItemSpade(MATERIA_SCENARIUM).setUnlocalizedName("scenariumShovel"));
		registerItem(1261, "scenarium_hoe", new ItemHoe(MATERIA_SCENARIUM).setUnlocalizedName("scenariumHoe"));
		
		registerItem(1262, "scenarium_helmet", (new ItemHelmet(MATERIA_SCENARIUM)).setUnlocalizedName("scenariumHelmet"));
        registerItem(1263, "scenarium_chestplate", (new ItemChestplate(MATERIA_SCENARIUM)).setUnlocalizedName("scenariumChestplate"));
        registerItem(1264, "scenarium_leggings", (new ItemLeggings(MATERIA_SCENARIUM)).setUnlocalizedName("scenariumLeggings"));
        registerItem(1265, "scenarium_boots", (new ItemBoots(MATERIA_SCENARIUM)).setUnlocalizedName("scenariumBoots"));
	}
}
