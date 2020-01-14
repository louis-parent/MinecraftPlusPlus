package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ItemAxe extends ItemTool
{
	private static Set<Block> EFFECTIVE_ON;

	private static Set<Block> getEffectiveOn()
	{
		if (EFFECTIVE_ON == null)
		{
			EFFECTIVE_ON = Sets.newHashSet(Blocks.getBlock(Blocks.PLANKS), Blocks.getBlock(Blocks.BOOKSHELF), Blocks.getBlock(Blocks.LOG), Blocks.getBlock(Blocks.LOG2), Blocks.getBlock(Blocks.CHEST), Blocks.getBlock(Blocks.PUMPKIN), Blocks.getBlock(Blocks.LIT_PUMPKIN), Blocks.getBlock(Blocks.MELON_BLOCK), Blocks.getBlock(Blocks.LADDER), Blocks.getBlock(Blocks.WOODEN_BUTTON), Blocks.getBlock(Blocks.WOODEN_PRESSURE_PLATE));
		}

		return EFFECTIVE_ON;
	}

	public ItemAxe(IToolMaterial material)
	{
		super(material);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Material material = state.getMaterial();
		if (material == Material.WOOD || material == Material.PLANTS || material == Material.VINE || getEffectiveOn().contains(state.getBlock()))
			return this.toolMaterial.getEfficiencyOnProperMaterial();
		else
			return 1.0F;
	}

	@Override
	public ToolType getToolType()
	{
		return ToolType.AXE;
	}
}
