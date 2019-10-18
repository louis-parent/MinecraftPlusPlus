package fr.minecraftpp.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.ToolType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

@Mod("minecraftpp")
public class ModItem extends Item
{
	public static final IToolMaterial SCENARIUM_TOOL_MATERIAL = new IToolMaterial() {

		@Override
		public int getMaxUses()
		{
			return 2160;
		}

		@Override
		public float getEfficiencyOnProperMaterial()
		{
			return 12.0F;
		}

		@Override
		public float getDamageVsEntity(ToolType toolType) 
		{
			switch (toolType) {
			case AXE:
				return 10.0F;
			case HOE:
				return 0.0F;
			default:
				return 5.0F;
			}
		}

		@Override
		public int getHarvestLevel()
		{
			return 4;
		}

		@Override
		public int getEnchantability() 
		{
			return 25;
		}

		@Override
		public float getAttackSpeed(ToolType toolType)
		{
			return toolType == ToolType.AXE ? -2.8F : 0.0F;
		}

		@Override
		public Item getMaterialItem() 
		{
			return ModItems.SCENARIUM;
		}
	};
	
	public static void registerBlockItems()
	{
        registerItemBlock(ModBlocks.SCENARITE_ORE);
        registerItemBlock(ModBlocks.SCENARIUM_BLOCK);
	}
	
	public static void registerItems()
	{
		registerItem(1256, "scenarium", new ItemScenarium());
		registerItem(1257, "scenarium_sword", new ItemSword(SCENARIUM_TOOL_MATERIAL).setUnlocalizedName("scenariumSword"));
		registerItem(1258, "scenarium_pickaxe", new ItemPickaxe(SCENARIUM_TOOL_MATERIAL).setUnlocalizedName("scenariumPickaxe"));
		registerItem(1259, "scenarium_axe", new ItemAxe(SCENARIUM_TOOL_MATERIAL).setUnlocalizedName("scenariumAxe"));
		registerItem(1260, "scenarium_spade", new ItemSpade(SCENARIUM_TOOL_MATERIAL).setUnlocalizedName("scenariumShovel"));
		registerItem(1261, "scenarium_hoe", new ItemHoe(SCENARIUM_TOOL_MATERIAL).setUnlocalizedName("scenariumHoe"));
	}
}
