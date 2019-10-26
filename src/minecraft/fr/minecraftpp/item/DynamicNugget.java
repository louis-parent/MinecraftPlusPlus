package fr.minecraftpp.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DynamicNugget extends Item implements IDynamicItem
{
	private final String ID;

	public DynamicNugget(String name)
	{
		super();
		
		this.ID = name + "Nugget";
		this.setUnlocalizedName(this.ID);
		
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

}
