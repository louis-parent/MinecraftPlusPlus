package fr.minecraftpp.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFalling
{
	public default void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this.getBlock(), this.tickRate(world));
	}
	
	public default void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos)
	{
		world.scheduleUpdate(pos, this.getBlock(), this.tickRate(world));
	}

	public default void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			this.checkFallable(world, pos);
		}
	}

	public default void checkFallable(World world, BlockPos pos)
	{
		if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)
		{
			int i = 32;

			if (!BlockFalling.fallInstantly && world.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32)))
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
					world.setBlockState(blockpos.up(), this.getBlock().getDefaultState());
				}
			}
		}
	}

	public default void onStartFalling(EntityFallingBlock fallingEntity)
	{
	}
	
	public default int tickRate(World world)
	{
		return 2;
	}

	public static boolean canFallThrough(IBlockState state)
	{
		Block block = state.getBlock();
		Material material = state.getMaterial();
		return material == Material.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
	}

	public default void onEndFalling(World world, BlockPos pos, IBlockState state1, IBlockState state2)
	{
	}

	public default void func_190974_b(World world, BlockPos pos)
	{
		
	}

	public default void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (rand.nextInt(16) == 0)
		{
			BlockPos blockpos = pos.down();

			if (canFallThrough(world.getBlockState(blockpos)))
			{
				double d0 = pos.getX() + rand.nextFloat();
				double d1 = pos.getY() - 0.05D;
				double d2 = pos.getZ() + rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
			}
		}
	}

	public default int getDustColor(IBlockState state)
	{
		return -16777216;
	}
	
	public abstract Block getBlock();
}
