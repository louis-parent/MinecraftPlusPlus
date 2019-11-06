package net.minecraft.item;

import fr.minecraftpp.item.food.IFood;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFood extends Item implements IFood
{
	/** Number of ticks to run while 'EnumAction'ing until result. */
	public final int itemUseDuration;

	/** The amount this food item heals the player. */
	private final int healAmount;
	private final float saturationModifier;

	/** Whether wolves like this food (true for raw and cooked porkchop). */
	private final boolean isWolfsFavoriteMeat;

	/**
	 * If this field is true, the food can be consumed even if the player don't
	 * need to eat.
	 */
	private boolean alwaysEdible;

	/**
	 * represents the potion effect that will occurr upon eating this food. Set
	 * by setPotionEffect
	 */
	private PotionEffect potionId;

	/** probably of the set potion effect occurring */
	private float potionEffectProbability;

	public ItemFood(int amount, float saturation, boolean isWolfFood)
	{
		this.itemUseDuration = 32;
		this.healAmount = amount;
		this.isWolfsFavoriteMeat = isWolfFood;
		this.saturationModifier = saturation;
		this.setCreativeTab(CreativeTabs.FOOD);
	}

	public ItemFood(int amount, boolean isWolfFood)
	{
		this(amount, 0.6F, isWolfFood);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		return IFood.super.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return IFood.super.getItemUseAction(stack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		return IFood.super.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean isFood()
	{
		return IFood.super.isFood();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public int getHealAmount(ItemStack stack)
	{
		return this.healAmount;
	}

	@Override
	public float getSaturationModifier(ItemStack stack)
	{
		return this.saturationModifier;
	}

	@Override
	public boolean isWolfsFavoriteMeat()
	{
		return this.isWolfsFavoriteMeat;
	}

	public ItemFood setPotionEffect(PotionEffect effect, float probability)
	{
		this.potionId = effect;
		this.potionEffectProbability = probability;
		return this;
	}

	/**
	 * Set the field 'alwaysEdible' to true, and make the food edible even if
	 * the player don't need to eat.
	 */
	public ItemFood setAlwaysEdible()
	{
		this.alwaysEdible = true;
		return this;
	}

	@Override
	public boolean isAlawaysEdible()
	{
		return this.alwaysEdible;
	}

	@Override
	public PotionEffect getPotionEffect()
	{
		return this.potionId;
	}

	@Override
	public float getPotionProbability()
	{
		return this.potionEffectProbability;
	}

	@Override
	public void foodUseStatEffect(EntityPlayer entityplayer)
	{
		entityplayer.addStat(StatList.getObjectUseStats(this));
	}
}
