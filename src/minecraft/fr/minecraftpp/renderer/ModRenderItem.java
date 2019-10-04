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
	public ModRenderItem(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_) { super(p_i46552_1_, p_i46552_2_, p_i46552_3_); }
	
	public static void registerBlockItemRender(RenderItem renderer)
	{
		renderer.registerBlock(ModBlocks.SCENARITE_ORE, "scenarite_ore");
	}

	public static void registerItemRender(RenderItem renderer)
	{
		renderer.registerItem(ModItems.SCENARIUM, "scenarium");
	}
}
