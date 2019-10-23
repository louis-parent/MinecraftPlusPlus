package fr.minecraftpp.generator.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Food implements IFood
{
	private int amount;
	private float saturation;
	private boolean isWolfFood;

	public Food(int amount, float saturation, boolean isWolfFood)
	{
		this.amount = amount;
		this.saturation = saturation;
		this.isWolfFood = isWolfFood;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		return null;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHealAmount(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSaturationModifier(ItemStack stack)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isWolfsFavoriteMeat()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFood()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAlawaysEdible()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PotionEffect getPotionEffect()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getPotionProbability()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void foodUseStatEffect(EntityPlayer entityplayer)
	{
		// TODO Auto-generated method stub
		
	}

}
