package fr.minecraftpp.item.tool;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class DynamicHoe extends ItemHoe implements IDynamicItem
{
	private final String ID;

	public DynamicHoe(String name, IToolMaterial material)
	{
		super(material);

		this.ID = name + "Hoe";
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
		return "hoe";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
