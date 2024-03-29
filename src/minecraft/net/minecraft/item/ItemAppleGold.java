package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemAppleGold extends ItemFood
{
	public ItemAppleGold(int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		this.setHasSubtypes(true);
		this.setRarity(Rarity.RARE);
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return super.hasEffect(stack) || stack.getMetadata() > 0;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public Rarity getRarity(ItemStack stack)
	{
		return stack.getMetadata() == 0 ? super.getRarity(stack) : super.getRarity(stack).next();
	}

	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if (!worldIn.isRemote)
		{
			if (stack.getMetadata() > 0)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
			}
			else
			{
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
			}
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab)
	{
		if (this.func_194125_a(itemIn))
		{
			tab.add(new ItemStack(this));
			tab.add(new ItemStack(this, 1, 1));
		}
	}
}
