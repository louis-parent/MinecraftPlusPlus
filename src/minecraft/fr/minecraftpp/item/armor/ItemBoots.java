package fr.minecraftpp.item.armor;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.ItemArmor;

@Mod("minecraftpp")
public class ItemBoots extends ItemArmor
{
	public ItemBoots(IArmorMaterial material)
	{
		super(material, EntityArmorSlot.FEET);
	}
}
