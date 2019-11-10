package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.manager.item.ModItems;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BlockScenariteOre extends BlockOre
{
	public BlockScenariteOre()
	{
		super(MapColor.MAGENTA);

		this.setUnlocalizedName("scenariteOre");
		this.setHardness(20.0F);
		this.setResistance(420.0F);
		this.setSoundType(SoundType.ANVIL);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return rand.nextInt(4) == 0 ? ModItems.SCENARIUM : Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

	@Override
	public int getRequiredHarvestLevel()
	{
		return 2;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		if (!world.isRemote)
		{
			int i = this.quantityDroppedWithBonus(fortune, world.rand);

			for (int j = 0; j < i; ++j)
			{
				if (world.rand.nextFloat() <= chance)
				{
					Item item = this.getItemDropped(state, world.rand, fortune);

					if (item != Items.EMPTY_ITEM)
					{
						spawnAsEntity(world, pos, new ItemStack(item, 1, this.damageDropped(state)));
					}

					if (item != Item.getItemFromBlock(this))
					{
						this.dropXpOnBlockBreak(world, pos, MathHelper.getInt(world.rand, 4, 8));
					}
				}
			}
		}
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);
		this.spawnParticles(world, pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.spawnParticles(world, pos);
		return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		this.spawnParticles(world, pos);
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		this.spawnParticles(world, pos);
		super.onBlockDestroyedByPlayer(world, pos, state);
	}

	private void spawnParticles(World world, BlockPos pos)
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

	@Override
	public void decorate(BiomeDecorator decorator, World world, Random rand)
	{
		//decorator.uniformOreGeneration(world, rand, 15, new WorldGenMinable(this.getDefaultState(), 10, BlockMatcher.forBlock(Blocks.STONE)), 0, 256);
	}
}
