package fr.minecraftpp.item.tool;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class DynamicShovel extends ItemSpade implements IDynamicItem
{
	private final String ID;

	public DynamicShovel(String name, IToolMaterial material)
	{
		super(material);
		
		this.ID = name + "Shovel";
		this.setUnlocalizedName(this.ID);
	}

	@Override
	public String getID()
	{
		return this.ID;
	}

	@Override
	public String getTextureName()
	{
		return "shovel";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
