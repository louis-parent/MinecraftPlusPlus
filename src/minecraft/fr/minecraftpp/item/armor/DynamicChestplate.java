package fr.minecraftpp.item.armor;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IArmorMaterial;
import net.minecraft.item.Item;

public class DynamicChestplate extends ItemChestplate implements IDynamicItem
{
	private final String ID;

	public DynamicChestplate(String name, IArmorMaterial material)
	{
		super(material);
		
		this.ID = name + "Chestplate";
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
		return "chestplate";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
