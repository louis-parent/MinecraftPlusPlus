package net.minecraft.item;

import fr.minecraftpp.color.Color;
import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.item.material.IArmorMaterial;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public enum ArmorMaterial implements IArmorMaterial
{
	LEATHER("leather", 5, new int[] { 1, 2, 3, 1 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 
	CHAIN("chainmail", 15, new int[] { 1, 4, 5, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F), 
	IRON("iron", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 
	DIAMOND("diamond", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F), 
	GOLD("gold", 7, new int[] { 1, 3, 5, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);

	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;

	private ArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness)
	{
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
	}

	@Override
	public int getArmorDurabilityFactor()
	{
		return this.maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EntityArmorSlot armorType)
	{
		return this.damageReductionAmountArray[armorType.getIndex()];
	}

	@Override
	public int getEnchantability()
	{
		return this.enchantability;
	}

	@Override
	public SoundEvent getSoundEvent()
	{
		return this.soundEvent;
	}

	@Override
	public Item getRepairItem()
	{
		if (this == LEATHER)
		{
			return Items.LEATHER;
		}
		else if (this == CHAIN)
		{
			return Items.IRON_INGOT;
		}
		else if (this == GOLD)
		{
			return Items.GOLD_INGOT;
		}
		else if (this == IRON)
		{
			return Items.IRON_INGOT;
		}
		else
		{
			return this == DIAMOND ? Items.DIAMOND : null;
		}
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public float getToughness()
	{
		return this.toughness;
	}
}