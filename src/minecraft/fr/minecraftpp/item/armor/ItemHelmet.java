package fr.minecraftpp.item.armor;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.ItemArmor;

public class ItemHelmet extends ItemArmor
{
	public ItemHelmet(ArmorMaterial material)
	{
		super(material, EntityArmorSlot.HEAD);
	}
}
