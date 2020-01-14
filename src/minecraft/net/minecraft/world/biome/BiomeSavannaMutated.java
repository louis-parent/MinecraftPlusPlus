package net.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeSavannaMutated extends BiomeSavanna
{
	private double noiseVal;
	
	public BiomeSavannaMutated(Biome.BiomeProperties properties)
	{
		super(properties);
		this.theBiomeDecorator.treesPerChunk = 2;
		this.theBiomeDecorator.flowersPerChunk = 2;
		this.theBiomeDecorator.grassPerChunk = 5;
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
	{
		this.noiseVal = noiseVal;
		this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}
	
	@Override
	public IBlockState getFillerBlock()
	{
		if(noiseVal > 1.75D)
		{
			return Blocks.getBlock(Blocks.STONE).getDefaultState();
		}
		else
		{
			return Blocks.getBlock(Blocks.DIRT).getDefaultState();
		}
	}
	
	@Override
	public IBlockState getTopBlock()
	{
		if (noiseVal > 1.75D)
		{
			return Blocks.getBlock(Blocks.STONE).getDefaultState();
		}
		else if (noiseVal > -0.5D)
		{
			return Blocks.getBlock(Blocks.DIRT).getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
		}
		else
		{
			return Blocks.getBlock(Blocks.GRASS).getDefaultState();
		}
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos)
	{
		this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
	}
}
