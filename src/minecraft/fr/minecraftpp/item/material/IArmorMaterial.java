package fr.minecraftpp.item.material;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.util.Color;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

@Mod("minecraftpp")
public interface IArmorMaterial extends IColoredMaterial
{
	public int getArmorDurabilityFactor();

	public int getDamageReductionAmount(EntityArmorSlot armorType);

	public int getEnchantability();

	public SoundEvent getSoundEvent();

	public Item getRepairItem();

	public String getName();

	public float getToughness();

	public default int getDurability(EntityArmorSlot armorType)
	{
		return armorType.getBaseDurability() * getArmorDurabilityFactor();
	}
}
