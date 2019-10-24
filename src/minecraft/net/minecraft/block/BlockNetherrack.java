package net.minecraft.block;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.FlammabilityOf;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockNetherrack extends Block
{
	public BlockNetherrack()
	{
		super(Material.ROCK);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return MapColor.NETHERRACK;
	}

	@Mod("Minecraftpp")
	@Override
	public FlammabilityOf getFlammability()
	{
		return FlammabilityOf.NETHERRACK;
	}
}
