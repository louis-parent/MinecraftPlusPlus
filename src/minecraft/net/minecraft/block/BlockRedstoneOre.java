package net.minecraft.block;

import java.util.Random;

import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.manager.block.OreRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BlockRedstoneOre extends BlockOre
{
	private final boolean isOn;

	public BlockRedstoneOre(boolean isOn)
	{
		super();

		if (isOn)
		{
			this.setTickRandomly(true);
			OreRegistry.unregister(this);
		}

		this.isOn = isOn;
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn)
	{
		return 30;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		this.activate(worldIn, pos);
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block)
	 */
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		this.activate(worldIn, pos);
		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY)
	{
		this.activate(worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY);
	}

	private void activate(World worldIn, BlockPos pos)
	{
		this.spawnParticles(worldIn, pos);

		if (this == Blocks.getBlock(Blocks.REDSTONE_ORE))
		{
			worldIn.setBlockState(pos, Blocks.getBlock(Blocks.LIT_REDSTONE_ORE).getDefaultState());
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (this == Blocks.getBlock(Blocks.LIT_REDSTONE_ORE))
		{
			worldIn.setBlockState(pos, Blocks.getBlock(Blocks.REDSTONE_ORE).getDefaultState());
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.getItem(Items.REDSTONE);
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return this.quantityDropped(random) + random.nextInt(fortune + 1);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random)
	{
		return 4 + random.nextInt(2);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

		if (this.getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this))
		{
			int i = 1 + worldIn.rand.nextInt(5);
			this.dropXpOnBlockBreak(worldIn, pos, i);
		}
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (this.isOn)
		{
			this.spawnParticles(worldIn, pos);
		}
	}

	private void spawnParticles(World worldIn, BlockPos pos)
	{
		Random random = worldIn.rand;
		double d0 = 0.0625D;

		for (int i = 0; i < 6; ++i)
		{
			double d1 = pos.getX() + random.nextFloat();
			double d2 = pos.getY() + random.nextFloat();
			double d3 = pos.getZ() + random.nextFloat();

			if (i == 0 && !worldIn.getBlockState(pos.up()).isOpaqueCube())
			{
				d2 = pos.getY() + 0.0625D + 1.0D;
			}

			if (i == 1 && !worldIn.getBlockState(pos.down()).isOpaqueCube())
			{
				d2 = pos.getY() - 0.0625D;
			}

			if (i == 2 && !worldIn.getBlockState(pos.south()).isOpaqueCube())
			{
				d3 = pos.getZ() + 0.0625D + 1.0D;
			}

			if (i == 3 && !worldIn.getBlockState(pos.north()).isOpaqueCube())
			{
				d3 = pos.getZ() - 0.0625D;
			}

			if (i == 4 && !worldIn.getBlockState(pos.east()).isOpaqueCube())
			{
				d1 = pos.getX() + 0.0625D + 1.0D;
			}

			if (i == 5 && !worldIn.getBlockState(pos.west()).isOpaqueCube())
			{
				d1 = pos.getX() - 0.0625D;
			}

			if (d1 < pos.getX() || d1 > pos.getX() + 1 || d2 < 0.0D || d2 > pos.getY() + 1 || d3 < pos.getZ() || d3 > pos.getZ() + 1)
			{
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Blocks.getBlock(Blocks.REDSTONE_ORE));
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(Blocks.getBlock(Blocks.REDSTONE_ORE)), 1, this.damageDropped(state));
	}

	@Override
	public void decorate(BiomeDecorator decorator, World world, Random rand)
	{
		if(ModManager.IS_VANILLA_ENABLED)
		{			
			ChunkGeneratorSettings settings = ChunkGeneratorSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
			decorator.uniformOreGeneration(world, rand, settings.redstoneCount, new WorldGenMinable(this.getDefaultState(), settings.redstoneSize), settings.redstoneMinHeight, settings.redstoneMaxHeight);
		}
	}
}
