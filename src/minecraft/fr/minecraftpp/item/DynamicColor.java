package fr.minecraftpp.item;

import fr.minecraftpp.item.material.IColoredMaterial;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class DynamicColor implements IItemColor
{
	private IColoredMaterial material;
	
	public DynamicColor(IColoredMaterial material)
	{
		this.material = material;
	}
	
	public DynamicColor(DynamicItem item)
	{
		this((IColoredMaterial) item);
	}
	
	public DynamicColor(DynamicNugget nugget)
	{
		this((IColoredMaterial) nugget);
	}
	
	public DynamicColor(ItemArmor armor)
	{
		this(armor.getArmorMaterial());
	}
	
	public DynamicColor(ItemTool tool)
	{
		this(tool.getToolMaterial());
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return tintIndex > 0 ? -1 : this.material.getRgbCondensed();
	}

}
