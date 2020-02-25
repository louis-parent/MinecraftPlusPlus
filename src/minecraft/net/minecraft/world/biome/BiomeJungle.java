package net.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeJungle extends Biome
{
	private final boolean isEdge;
	
	public BiomeJungle(boolean isEdgeIn, Biome.BiomeProperties properties)
	{
		super(properties);
		this.isEdge = isEdgeIn;

		if (isEdgeIn)
		{
			this.theBiomeDecorator.treesPerChunk = 2;
		}
		else
		{
			this.theBiomeDecorator.treesPerChunk = 50;
		}

		this.theBiomeDecorator.grassPerChunk = 25;
		this.theBiomeDecorator.flowersPerChunk = 4;

		if (!isEdgeIn)
		{
			this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		}

		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityParrot.class, 40, 1, 2));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random rand)
	{
		if (rand.nextInt(10) == 0)
		{
			return getBIG_TREE_FEATURE();
		}
		else if (rand.nextInt(2) == 0)
		{
			return new WorldGenShrub(getJungleLog(), getOakLeaf());
		}
		else
		{
			return !this.isEdge && rand.nextInt(3) == 0 ? new WorldGenMegaJungle(false, 10, 20, getJungleLog(), getJungleLeaf()) : new WorldGenTrees(false, 4 + rand.nextInt(7), getJungleLog(), getJungleLeaf(), true);
		}
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos)
	{
		super.decorate(worldIn, rand, pos);
		int i = rand.nextInt(16) + 8;
		int j = rand.nextInt(16) + 8;
		BlockPos height = worldIn.getHeight(pos.add(i, 0, j));
		int y = height.getY();
		int k = rand.nextInt(y * 2);
		(new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
		WorldGenVines worldgenvines = new WorldGenVines();

		for (int j1 = 0; j1 < 50; ++j1)
		{
			k = rand.nextInt(16) + 8;
			int l = 128;
			int i1 = rand.nextInt(16) + 8;
			worldgenvines.generate(worldIn, rand, pos.add(k, 128, i1));
		}
	}

	public static IBlockState getJungleLog()
	{
		return Blocks.getBlock(Blocks.LOG).getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
	}

	public static IBlockState getJungleLeaf()
	{
		return Blocks.getBlock(Blocks.LEAVES).getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
	}

	public static IBlockState getOakLeaf()
	{
		return Blocks.getBlock(Blocks.LEAVES).getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
	}
}
