package net.minecraft.enchantment;

import fr.minecraftpp.inventory.EntityEquipmentSlot;

public class EnchantmentKnockback extends Enchantment
{
	protected EnchantmentKnockback(Enchantment.EnchantmentRarity rarityIn, EntityEquipmentSlot... slots)
	{
		super(rarityIn, EnumEnchantmentType.WEAPON, slots);
		this.setName("knockback");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
		return 5 + 20 * (enchantmentLevel - 1);
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel()
	{
		return 2;
	}
}
