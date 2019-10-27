package fr.minecraftpp.color;

import fr.minecraftpp.block.DynamicBlock;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.item.DynamicNugget;
import fr.minecraftpp.item.material.IColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class DynamicColor implements IItemColor, IBlockColor
{
	private IColored material;
	
	public DynamicColor(IColored material)
	{
		this.material = material;
	}
	
	public DynamicColor(DynamicItem item)
	{
		this((IColored) item);
	}
	
	public DynamicColor(DynamicNugget nugget)
	{
		this((IColored) nugget);
	}
	
	public DynamicColor(ItemArmor armor)
	{
		this(armor.getArmorMaterial());
	}
	
	public DynamicColor(ItemTool tool)
	{
		this(tool.getToolMaterial());
	}
	
	public DynamicColor(DynamicBlock block)
	{
		this((IColored) block);
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return this.material.getRgbCondensed();
	}

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex)
	{
		return this.material.getRgbCondensed();
	}

}
