package fr.minecraftpp.manager.renderer;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.manager.block.ModBlock;
import fr.minecraftpp.manager.block.ModBlocks;
import fr.minecraftpp.manager.item.ModItem;
import fr.minecraftpp.manager.item.ModItems;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;

@Mod("minecraftpp")
public class ModRenderItem extends RenderItem
{
	public ModRenderItem(TextureManager textureManager, ModelManager modelManager, ItemColors colors)
	{
		super(textureManager, modelManager, colors);
	}

	public static void registerBlockItemRender(RenderItem renderer)
	{
		renderer.registerBlock(ModBlocks.SCENARITE_ORE, "scenarite_ore");
		renderer.registerBlock(ModBlocks.SCENARIUM_BLOCK, "scenarium_block");

		for (IDynamicBlock block : ModBlock.REGISTRY)
		{
			renderer.registerBlock(block.getBlock(), block.getID());
		}
	}

	public static void registerItemRender(RenderItem renderer)
	{
		renderer.registerItem(ModItems.SCENARIUM, "scenarium");

		renderer.registerItem(ModItems.SCENARIUM_SWORD, "scenarium_sword");
		renderer.registerItem(ModItems.SCENARIUM_PICKAXE, "scenarium_pickaxe");
		renderer.registerItem(ModItems.SCENARIUM_AXE, "scenarium_axe");
		renderer.registerItem(ModItems.SCENARIUM_SPADE, "scenarium_spade");
		renderer.registerItem(ModItems.SCENARIUM_HOE, "scenarium_hoe");

		renderer.registerItem(ModItems.SCENARIUM_HELMET, "scenarium_helmet");
		renderer.registerItem(ModItems.SCENARIUM_CHESTPLATE, "scenarium_chestplate");
		renderer.registerItem(ModItems.SCENARIUM_LEGGINGS, "scenarium_leggings");
		renderer.registerItem(ModItems.SCENARIUM_BOOTS, "scenarium_boots");

		for (IDynamicItem item : ModItem.REGISTRY)
		{
			renderer.registerItem(item.getItem(), item.getID());
		}
	}
}
