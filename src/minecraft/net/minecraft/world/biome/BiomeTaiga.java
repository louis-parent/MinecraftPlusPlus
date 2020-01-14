package net.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeTaiga extends Biome
{
	private static WorldGenTaiga1 PINE_GENERATOR;
	private static WorldGenTaiga2 SPRUCE_GENERATOR;
	private static WorldGenMegaPineTree MEGA_PINE_GENERATOR;
	private static WorldGenMegaPineTree MEGA_SPRUCE_GENERATOR;
	private static WorldGenBlockBlob FOREST_ROCK_GENERATOR;
	private final BiomeTaiga.Type type;
	
	private double noiseVal;

	public BiomeTaiga(BiomeTaiga.Type typeIn, Biome.BiomeProperties properties)
	{
		super(properties);
		this.type = typeIn;
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 8, 4, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
		this.theBiomeDecorator.treesPerChunk = 10;

		if (typeIn != BiomeTaiga.Type.MEGA && typeIn != BiomeTaiga.Type.MEGA_SPRUCE)
		{
			this.theBiomeDecorator.grassPerChunk = 1;
			this.theBiomeDecorator.mushroomsPerChunk = 1;
		}
		else
		{
			this.theBiomeDecorator.grassPerChunk = 7;
			this.theBiomeDecorator.deadBushPerChunk = 1;
			this.theBiomeDecorator.mushroomsPerChunk = 3;
		}
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random rand)
	{
		if ((this.type == BiomeTaiga.Type.MEGA || this.type == BiomeTaiga.Type.MEGA_SPRUCE) && rand.nextInt(3) == 0)
		{
			return this.type != BiomeTaiga.Type.MEGA_SPRUCE && rand.nextInt(13) != 0 ? getMEGA_PINE_GENERATOR() : getMegaSpruceGenerator();
		}
		else
		{
			return rand.nextInt(3) == 0 ? getPineGenerator() : getSpruceGenerator();
		}
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return rand.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos)
	{
		if (this.type == BiomeTaiga.Type.MEGA || this.type == BiomeTaiga.Type.MEGA_SPRUCE)
		{
			int i = rand.nextInt(3);

			for (int j = 0; j < i; ++j)
			{
				int k = rand.nextInt(16) + 8;
				int l = rand.nextInt(16) + 8;
				BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
				getFOREST_ROCK_GENERATOR().generate(worldIn, rand, blockpos);
			}
		}

		DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

		for (int i1 = 0; i1 < 7; ++i1)
		{
			int j1 = rand.nextInt(16) + 8;
			int k1 = rand.nextInt(16) + 8;
			int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
			DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
		}

		super.decorate(worldIn, rand, pos);
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
	{
		if (this.type == BiomeTaiga.Type.MEGA || this.type == BiomeTaiga.Type.MEGA_SPRUCE)
		{
			this.noiseVal = noiseVal;
		}

		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}
	
	@Override
	public IBlockState getFillerBlock()
	{
		return Blocks.getBlock(Blocks.DIRT).getDefaultState();
	}
	
	@Override
	public IBlockState getTopBlock()
	{
		if (noiseVal > 1.75D)
		{
			return Blocks.getBlock(Blocks.DIRT).getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
		}
		else if (noiseVal > -0.95D)
		{
			return Blocks.getBlock(Blocks.DIRT).getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
		}
		else
		{
			return Blocks.getBlock(Blocks.GRASS).getDefaultState();
		}
	}

	public static WorldGenTaiga1 getPineGenerator()
	{
		if(PINE_GENERATOR == null)
		{
			PINE_GENERATOR = new WorldGenTaiga1();
		}
		
		return PINE_GENERATOR;
	}

	public static WorldGenTaiga2 getSpruceGenerator()
	{
		if(SPRUCE_GENERATOR == null)
		{
			SPRUCE_GENERATOR = new WorldGenTaiga2(false);
		}
		
		return SPRUCE_GENERATOR;
	}

	public static WorldGenMegaPineTree getMEGA_PINE_GENERATOR()
	{
		if(MEGA_PINE_GENERATOR == null)
		{
			MEGA_PINE_GENERATOR = new WorldGenMegaPineTree(false, false);
		}
		
		return MEGA_PINE_GENERATOR;
	}

	public static WorldGenMegaPineTree getMegaSpruceGenerator()
	{
		if(MEGA_SPRUCE_GENERATOR == null)
		{
			MEGA_SPRUCE_GENERATOR = new WorldGenMegaPineTree(false, true);
		}
		
		return MEGA_SPRUCE_GENERATOR;
	}

	public static WorldGenBlockBlob getFOREST_ROCK_GENERATOR()
	{
		if(FOREST_ROCK_GENERATOR == null)
		{
			FOREST_ROCK_GENERATOR = new WorldGenBlockBlob(Blocks.getBlock(Blocks.MOSSY_COBBLESTONE), 0);
		}
		
		return FOREST_ROCK_GENERATOR;
	}

	public static enum Type
	{
		NORMAL, MEGA, MEGA_SPRUCE;
	}
}
