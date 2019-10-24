package net.minecraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block
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

	/**
	 * Called after the block is set in the Chunk data, but before the Tile
	 * Entity is set
	 */
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	/**
	 * Called when a neighboring block was changed and marks that this state
	 * should perform any checks during a neighbor change. Cases may include
	 * when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
	{
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			this.checkFallable(world, pos);
		}
	}

	private void checkFallable(World world, BlockPos pos)
	{
		if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)
		{
			int i = 32;

			if (!fallInstantly && world.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32)))
			{
				if (!world.isRemote)
				{
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos));
					this.onStartFalling(entityfallingblock);
					world.spawnEntityInWorld(entityfallingblock);
				}
			}
			else
			{
				world.setBlockToAir(pos);
				BlockPos blockpos;

				for (blockpos = pos.down(); canFallThrough(world.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down())
				{
					;
				}

				if (blockpos.getY() > 0)
				{
					world.setBlockState(blockpos.up(), this.getDefaultState());
				}
			}
		}
	}

	protected void onStartFalling(EntityFallingBlock fallingEntity)
	{
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn)
	{
		return 2;
	}

	public static boolean canFallThrough(IBlockState state)
	{
		Block block = state.getBlock();
		Material material = state.getMaterial();
		return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
	}

	public void onEndFalling(World worldIn, BlockPos pos, IBlockState p_176502_3_, IBlockState p_176502_4_)
	{
	}

	public void func_190974_b(World world, BlockPos pos)
	{
		
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(16) == 0)
		{
			BlockPos blockpos = pos.down();

			if (canFallThrough(worldIn.getBlockState(blockpos)))
			{
				double d0 = pos.getX() + rand.nextFloat();
				double d1 = pos.getY() - 0.05D;
				double d2 = pos.getZ() + rand.nextFloat();
				worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(stateIn));
			}
		}
	}

	public int getDustColor(IBlockState p_189876_1_)
	{
		return -16777216;
	}
}
