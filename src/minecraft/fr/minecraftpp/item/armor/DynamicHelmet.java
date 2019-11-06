package fr.minecraftpp.item.armor;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DynamicHelmet extends ItemHelmet implements IDynamicItem
{
	private final String ID;

	public DynamicHelmet(String name, IArmorMaterial material)
	{
		super(material);

		this.ID = name + "Helmet";
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
		return "helmet";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

	@Override
	public int getColor(ItemStack stack)
	{
		return this.getArmorMaterial().getRgbCondensed();
	}
}
