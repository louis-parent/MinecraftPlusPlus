package fr.minecraftpp.item.armor;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IArmorMaterial;
import net.minecraft.item.Item;

public class DynamicLeggings extends ItemLeggings implements IDynamicItem
{
	private final String ID;

	public DynamicLeggings(String name, IArmorMaterial material)
	{
		super(material);
		
		this.ID = name + "Leggings";
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
		return "leggings";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
