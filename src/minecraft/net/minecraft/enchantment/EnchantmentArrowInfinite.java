package net.minecraft.enchantment;

import fr.minecraftpp.inventory.EntityEquipmentSlot;

public class EnchantmentArrowInfinite extends Enchantment
{
	public EnchantmentArrowInfinite(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots)
	{
		super(rarityIn, EnumEnchantmentType.BOW, slots);
		this.setName("arrowInfinite");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
		return 20;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
		return 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(Enchantment ench)
	{
		return ench instanceof EnchantmentMending ? false : super.canApplyTogether(ench);
	}
}
