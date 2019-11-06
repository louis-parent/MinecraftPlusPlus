package fr.minecraftpp.item.tool;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class DynamicAxe extends ItemAxe implements IDynamicItem
{
	private final String ID;

	public DynamicAxe(String name, IToolMaterial material)
	{
		super(material);

		this.ID = name + "Axe";
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
		return "axe";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
