package fr.minecraftpp.item.armor;

import fr.minecraftpp.inventory.EntityArmorSlot;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

public interface IArmorMaterial
{
	public int getRenderIndex();

    public int getDurability(EntityArmorSlot armorType);

    public int getDamageReductionAmount(EntityArmorSlot armorType);

    public int getEnchantability();

    public SoundEvent getSoundEvent();

    public Item getRepairItem();

    public String getName();

    public float getToughness();
}
