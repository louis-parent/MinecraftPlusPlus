package fr.minecraftpp.manager.renderer;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.manager.block.ModBlock;
import fr.minecraftpp.manager.item.ModItem;
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
		for (IDynamicBlock block : ModBlock.REGISTRY)
		{
			renderer.registerBlock(block.getBlock(), block.getID());
		}
	}

	public static void registerItemRender(RenderItem renderer)
	{
		for (IDynamicItem item : ModItem.REGISTRY)
		{
			renderer.registerItem(item.getItem(), item.getID());
		}
	}
}
