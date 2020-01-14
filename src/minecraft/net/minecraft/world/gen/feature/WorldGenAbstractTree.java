package net.minecraft.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenAbstractTree extends WorldGenerator
{
	public WorldGenAbstractTree(boolean notify)
	{
		super(notify);
	}

	/**
	 * returns whether or not a tree can grow into a block For example, a tree
	 * will not grow into stone
	 */
	protected boolean canGrowInto(Block blockType)
	{
		Material material = blockType.getDefaultState().getMaterial();
		return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.getBlock(Blocks.GRASS) || blockType == Blocks.getBlock(Blocks.DIRT) || blockType == Blocks.getBlock(Blocks.LOG) || blockType == Blocks.getBlock(Blocks.LOG2) || blockType == Blocks.getBlock(Blocks.SAPLING) || blockType == Blocks.getBlock(Blocks.VINE);
	}

	public void generateSaplings(World worldIn, Random random, BlockPos pos)
	{
	}

	/**
	 * sets dirt at a specific location if it isn't already dirt
	 */
	protected void setDirtAt(World worldIn, BlockPos pos)
	{
		if (worldIn.getBlockState(pos).getBlock() != Blocks.getBlock(Blocks.DIRT))
		{
			this.setBlockAndNotifyAdequately(worldIn, pos, Blocks.getBlock(Blocks.DIRT).getDefaultState());
		}
	}
}
