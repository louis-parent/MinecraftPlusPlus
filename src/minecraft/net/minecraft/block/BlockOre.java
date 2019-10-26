package net.minecraft.block;

import java.util.Random;

import fr.minecraftpp.manager.block.OreRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BlockOre extends Block
{
	public BlockOre()
	{
		this(Material.ROCK.getMaterialMapColor());
	}

	public BlockOre(MapColor color)
	{
		super(Material.ROCK, color);
		
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		
		OreRegistry.register(this);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (this == Blocks.COAL_ORE)
		{
			return Items.COAL;
		}
		else if (this == Blocks.DIAMOND_ORE)
		{
			return Items.DIAMOND;
		}
		else if (this == Blocks.LAPIS_ORE)
		{
			return Items.DYE;
		}
		else if (this == Blocks.EMERALD_ORE)
		{
			return Items.EMERALD;
		}
		else
		{
			return this == Blocks.QUARTZ_ORE ? Items.QUARTZ : Item.getItemFromBlock(this);
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random)
	{
		return this == Blocks.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))
		{
			int i = random.nextInt(fortune + 2) - 1;

			if (i < 0)
			{
				i = 0;
			}

			return this.quantityDropped(random) * (i + 1);
		}
		else
		{
			return this.quantityDropped(random);
		}
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

		if (this.getItemDropped(state, world.rand, fortune) != Item.getItemFromBlock(this))
		{
			int i = 0;

			if (this == Blocks.COAL_ORE)
			{
				i = MathHelper.getInt(world.rand, 0, 2);
			}
			else if (this == Blocks.DIAMOND_ORE)
			{
				i = MathHelper.getInt(world.rand, 3, 7);
			}
			else if (this == Blocks.EMERALD_ORE)
			{
				i = MathHelper.getInt(world.rand, 3, 7);
			}
			else if (this == Blocks.LAPIS_ORE)
			{
				i = MathHelper.getInt(world.rand, 2, 5);
			}
			else if (this == Blocks.QUARTZ_ORE)
			{
				i = MathHelper.getInt(world.rand, 2, 5);
			}

			this.dropXpOnBlockBreak(world, pos, i);
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called
	 * when the block gets destroyed. It returns the metadata of the dropped
	 * item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state)
	{
		return this == Blocks.LAPIS_ORE ? EnumDyeColor.BLUE.getDyeDamage() : 0;
	}
	
	public void decorate(BiomeDecorator decorator, World world, Random rand)
	{
		ChunkGeneratorSettings settings = ChunkGeneratorSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
		
		if(this == Blocks.LAPIS_ORE)
		{
			decorator.spreadOreGeneration(world, rand, settings.lapisCount, new WorldGenMinable(this.getDefaultState(), settings.lapisSize), settings.lapisCenterHeight, settings.lapisSpread);
		}
		else if (this == Blocks.COAL_ORE)
		{
			decorator.uniformOreGeneration(world, rand, settings.coalCount, new WorldGenMinable(this.getDefaultState(), settings.coalSize), settings.coalMinHeight, settings.coalMaxHeight);
		}
		else if (this == Blocks.DIAMOND_ORE)
		{
			decorator.uniformOreGeneration(world, rand, settings.diamondCount, new WorldGenMinable(this.getDefaultState(), settings.diamondSize), settings.diamondMinHeight, settings.diamondMaxHeight);
		}
		else if (this == Blocks.IRON_ORE)
		{
			decorator.uniformOreGeneration(world, rand, settings.ironCount, new WorldGenMinable(this.getDefaultState(), settings.ironSize), settings.ironMinHeight, settings.ironMaxHeight);
		}
		else if (this == Blocks.GOLD_ORE)
		{
			decorator.uniformOreGeneration(world, rand, settings.goldCount, new WorldGenMinable(this.getDefaultState(), settings.goldSize), settings.goldMinHeight, settings.goldMaxHeight);
		}
	}
}
