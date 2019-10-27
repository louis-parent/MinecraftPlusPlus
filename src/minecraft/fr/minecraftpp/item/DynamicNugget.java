package fr.minecraftpp.item;

import fr.minecraftpp.color.Color;
import fr.minecraftpp.item.material.IColored;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DynamicNugget extends Item implements IDynamicItem, IColored
{
	private final String ID;
	private final DynamicItem item;

	public DynamicNugget(String name, DynamicItem item)
	{
		super();
		
		this.ID = name + "Nugget";
		this.setUnlocalizedName(this.ID);
		
		this.item = item;
		
		this.setCreativeTab(CreativeTabs.MATERIALS);
	}

	@Override
	public String getID()
	{
		return this.ID;
	}

	@Override
	public String getTextureName()
	{
		return "nugget";
	}

	@Override
	public Item getItem()
	{
		return this;
	}
	
	@Override
	public boolean hasColor()
	{
		return this.item.hasColor();
	}

	@Override
	public Color getColor()
	{
		return this.item.getColor();
	}
}
