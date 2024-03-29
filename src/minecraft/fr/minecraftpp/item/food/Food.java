package fr.minecraftpp.item.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class Food implements IFood
{
	private int amount;
	private float saturation;
	private boolean isWolfFood;

	private int useDuration;
	private boolean isAlwaysEdible;

	private PotionEffect potionEffect;
	private float potionProbability;

	public Food(int amount, float saturation)
	{
		this(amount, saturation, false);
	}

	public Food(int amount, float saturation, boolean isWolfFood)
	{
		this(amount, saturation, isWolfFood, false);
	}

	public Food(int amount, float saturation, boolean isWolfFood, boolean isAlwaysEdible)
	{
		this(amount, saturation, isWolfFood, isAlwaysEdible, 32);
	}

	public Food(int amount, float saturation, boolean isWolfFood, boolean isAlwaysEdible, int useDuration)
	{
		this.amount = amount;
		this.saturation = saturation;
		this.isWolfFood = isWolfFood;
		this.useDuration = useDuration;
		this.isAlwaysEdible = isAlwaysEdible;

		this.potionEffect = null;
		this.potionProbability = 0;
	}

	@Override
	public int getItemUseDuration(ItemStack stack)
	{
		return this.useDuration;
	}

	@Override
	public int getFoodAmount(ItemStack stack)
	{
		return this.amount;
	}

	@Override
	public float getSaturationAmount(ItemStack stack)
	{
		return this.saturation;
	}

	@Override
	public boolean isEdibleByWolf()
	{
		return this.isWolfFood;
	}

	@Override
	public boolean isAlwaysEdible()
	{
		return this.isAlwaysEdible;
	}

	@Override
	public PotionEffect getPotionEffect()
	{
		return this.potionEffect;
	}

	@Override
	public float getPotionProbability()
	{
		return this.potionProbability;
	}

	@Override
	public void foodUseStatEffect(EntityPlayer entityplayer)
	{
	}

	public void setPotionEffectAndProbability(PotionEffect effect, float probability)
	{
		this.potionEffect = effect;
		this.potionProbability = probability;
	}
	
	@Override
	public String getFoodInfo()
	{
		String str = "food{";
		
		str += "amount=" + this.amount + ", ";
		str += "saturation=" + this.saturation;
		
		if (this.isWolfFood) {
			str += ", wolf food";
		}
		
		if (this.isAlwaysEdible) {
			str += ", always edible";
		}
		
		return str + "}";
	}
}
