package net.minecraft.block;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAir extends Block
{
	protected BlockAir()
	{
		super(Material.AIR);
	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static
	 * model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids,
	 * INVISIBLE to skip all rendering
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
	{
		return false;
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
	}

	/**
	 * Whether this Block can be replaced directly by other blocks (true for
	 * e.g. tall grass)
	 */
	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
