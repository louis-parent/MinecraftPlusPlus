package net.minecraft.block;

import java.util.Random;

import fr.minecraftpp.block.IFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block implements IFalling
{
	public static boolean fallInstantly;

	public BlockFalling()
	{
		super(Material.SAND);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	public BlockFalling(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		IFalling.super.onBlockAdded(world, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos)
	{
		IFalling.super.neighborChanged(state, world, pos, block, neighborPos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		IFalling.super.updateTick(world, pos, state, rand);
	}

	@Override
	public int tickRate(World world)
	{
		return IFalling.super.tickRate(world);
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		IFalling.super.randomDisplayTick(state, world, pos, rand);
	}

	@Override
	public Block getBlock()
	{
		return this;
	}

	public int getDustColor(IBlockState state)
	{
		return -16777216;
	}
}
