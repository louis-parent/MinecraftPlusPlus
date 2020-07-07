package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.generation.OreRarity;
import fr.minecraftpp.item.DynamicItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DynamicOreGem extends DynamicOre
{
	private int minDropped;
	private int maxDropped;

	private int minXpDrop;
	private int maxXpDrop;

	private boolean isPoweredOre;

	public DynamicOreGem(String typeName, int textureId, OreRarity oreRarity, DynamicItem itemDropped, HarvestLevel harvestLevel, int minXpDrop, int maxXpDrop)
	{
		super(typeName, textureId, oreRarity, harvestLevel, itemDropped);

		this.minDropped = 1;
		this.maxDropped = 1;

		this.minXpDrop = minXpDrop;
		this.maxXpDrop = maxXpDrop;

		this.isPoweredOre = false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.item;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return minDropped + random.nextInt((maxDropped + 1) - minDropped);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

		this.dropXpOnBlockBreak(world, pos, MathHelper.getInt(world.rand, this.minXpDrop, this.maxXpDrop));
	}

	public void setPoweredOre(boolean isPoweredOre)
	{
		this.isPoweredOre = isPoweredOre;
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);
		this.spawnParticlesIfNeeded(world, pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.spawnParticlesIfNeeded(world, pos);
		return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		this.spawnParticlesIfNeeded(world, pos);
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		this.spawnParticlesIfNeeded(world, pos);
		super.onBlockDestroyedByPlayer(world, pos, state);
	}

	private void spawnParticlesIfNeeded(World world, BlockPos pos)
	{
		if (this.isPoweredOre)
		{
			Random random = world.rand;
			double d0 = 0.0625D;

			for (int i = 0; i < 6; ++i)
			{
				double d1 = pos.getX() + random.nextFloat();
				double d2 = pos.getY() + random.nextFloat();
				double d3 = pos.getZ() + random.nextFloat();

				if (i == 0 && !world.getBlockState(pos.up()).isOpaqueCube())
				{
					d2 = pos.getY() + 0.0625D + 1.0D;
				}

				if (i == 1 && !world.getBlockState(pos.down()).isOpaqueCube())
				{
					d2 = pos.getY() - 0.0625D;
				}

				if (i == 2 && !world.getBlockState(pos.south()).isOpaqueCube())
				{
					d3 = pos.getZ() + 0.0625D + 1.0D;
				}

				if (i == 3 && !world.getBlockState(pos.north()).isOpaqueCube())
				{
					d3 = pos.getZ() - 0.0625D;
				}

				if (i == 4 && !world.getBlockState(pos.east()).isOpaqueCube())
				{
					d1 = pos.getX() + 0.0625D + 1.0D;
				}

				if (i == 5 && !world.getBlockState(pos.west()).isOpaqueCube())
				{
					d1 = pos.getX() - 0.0625D;
				}

				if (d1 < pos.getX() || d1 > pos.getX() + 1 || d2 < 0.0D || d2 > pos.getY() + 1 || d3 < pos.getZ() || d3 > pos.getZ() + 1)
				{
					world.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	public int getAverageQuantityDropped()
	{
		return (this.maxDropped + this.minDropped) / 2;
	}

	public void increaseDrop()
	{
		this.minDropped *= 2;
		this.maxDropped *= 2.5;
	}
}
