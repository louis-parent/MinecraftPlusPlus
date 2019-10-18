package fr.minecraftpp.item.armor;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.ItemArmor;

public class ItemChestplate extends ItemArmor
{
	public ItemChestplate(ArmorMaterial material)
	{
		super(material, EntityArmorSlot.CHEST);
	}
}
