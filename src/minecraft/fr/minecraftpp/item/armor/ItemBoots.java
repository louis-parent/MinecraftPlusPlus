package fr.minecraftpp.item.armor;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.ItemArmor;

public class ItemBoots extends ItemArmor
{
	public ItemBoots(IArmorMaterial material)
	{
		super(material, EntityArmorSlot.FEET);
	}
}
