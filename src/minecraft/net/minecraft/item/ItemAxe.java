package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ItemAxe extends ItemTool
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

	public ItemAxe(IToolMaterial material)
	{
		super(material);
	}

	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Material material = state.getMaterial();
		if (material == Material.WOOD || material == Material.PLANTS || material == Material.VINE || EFFECTIVE_ON.contains(state.getBlock()))
			return this.efficiencyOnProperMaterial;
		else
			return 1.0F;
	}

	@Override
	public ToolType getToolType()
	{
		return ToolType.AXE;
	}
}
