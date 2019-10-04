package fr.minecraftpp.renderer;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.item.ModItems;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;

@Mod("minecraftpp")
public class ModRenderItem extends RenderItem
{
	public ModRenderItem(TextureManager textureManager, ModelManager modelManager, ItemColors colors) { super(textureManager, modelManager, colors); }
	
	public static void registerBlockItemRender(RenderItem renderer)
	{
		renderer.registerBlock(ModBlocks.SCENARITE_ORE, "scenarite_ore");
		renderer.registerBlock(ModBlocks.SCENARIUM_BLOCK, "scenarium_block");
	}

	public static void registerItemRender(RenderItem renderer)
	{
		renderer.registerItem(ModItems.SCENARIUM, "scenarium");
	}
}
