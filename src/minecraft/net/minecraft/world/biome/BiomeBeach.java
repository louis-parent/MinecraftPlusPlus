package net.minecraft.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class BiomeBeach extends Biome
{
	public BiomeBeach(Biome.BiomeProperties properties)
	{
		super(properties);
		this.spawnableCreatureList.clear();
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.reedsPerChunk = 0;
		this.theBiomeDecorator.cactiPerChunk = 0;
	}
	
	@Override
	public IBlockState getTopBlock()
	{
		return Blocks.getBlock(Blocks.SAND).getDefaultState();
	}

	@Override
	public IBlockState getFillerBlock()
	{
		return Blocks.getBlock(Blocks.SAND).getDefaultState();
	}
}
