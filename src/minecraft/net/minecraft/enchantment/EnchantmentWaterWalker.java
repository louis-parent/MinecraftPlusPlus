package net.minecraft.enchantment;

import fr.minecraftpp.inventory.EntityEquipmentSlot;
import net.minecraft.init.Enchantments;

public class EnchantmentWaterWalker extends Enchantment
{
	public EnchantmentWaterWalker(Enchantment.EnchantmentRarity rarityIn, EntityEquipmentSlot... slots)
	{
		super(rarityIn, EnumEnchantmentType.ARMOR_FEET, slots);
		this.setName("waterWalker");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
		return enchantmentLevel * 10;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
		return this.getMinEnchantability(enchantmentLevel) + 15;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel()
	{
		return 3;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(Enchantment ench)
	{
		return super.canApplyTogether(ench) && ench != Enchantments.FROST_WALKER;
	}
}
