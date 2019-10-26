package fr.minecraftpp.item.tool;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class DynamicSword extends ItemSword implements IDynamicItem
{
	private final String ID;

	public DynamicSword(String name, IToolMaterial material)
	{
		super(material);
		
		this.ID = name + "Sword";
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
		return "sword";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
