package fr.minecraftpp.item.armor;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.ItemArmor;

public class ItemLeggings extends ItemArmor
{
	public ItemLeggings(ArmorMaterial material)
	{
		super(material, EntityArmorSlot.LEGS);
	}
}
