package net.minecraft.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

public class BiomeEnd extends Biome
{
	public BiomeEnd(Biome.BiomeProperties properties)
	{
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
		this.theBiomeDecorator = new BiomeEndDecorator();
	}

	/**
	 * takes temperature, returns color
	 */
	@Override
	public int getSkyColorByTemp(float currentTemperature)
	{
		return 0;
	}
	
	
	@Override
	public IBlockState getTopBlock()
	{
		return Blocks.getBlock(Blocks.DIRT).getDefaultState();
	}

	@Override
	public IBlockState getFillerBlock()
	{
		return Blocks.getBlock(Blocks.DIRT).getDefaultState();
	}
}
