package fr.minecraftpp.item.food;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class NotFood implements IFood
{
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		return stack;
	}

	@Override
	public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.NONE;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	public boolean isFood()
	{
		return false;
	}

	@Override
	public int getHealAmount(ItemStack stack)
	{
		return 0;
	}

	@Override
	public float getSaturationModifier(ItemStack stack)
	{
		return 0;
	}

	@Override
	public boolean isWolfsFavoriteMeat()
	{
		return false;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 0;
	}

	@Override
	public boolean isAlawaysEdible()
	{
		return false;
	}

	@Override
	public PotionEffect getPotionEffect()
	{
		return null;
	}

	@Override
	public float getPotionProbability()
	{
		return 0;
	}

	@Override
	public void foodUseStatEffect(EntityPlayer entityplayer)
	{
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj.getClass().equals(NotFood.class))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
