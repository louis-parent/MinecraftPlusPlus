package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.block.HarvestLevel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class DynamicMetal extends DynamicOre
{
	public DynamicMetal(String typeName, int textureId, HarvestLevel harvestLevel)
	{
		super(typeName, textureId, null, 1, harvestLevel, 0, 0);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}
