package fr.minecraftpp.generator.item.food;

import fr.minecraftpp.item.DynamicItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public interface IFood
{	
	public default ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			entityplayer.getFoodStats().addStats(this, stack);
			world.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, world, entityplayer);
			this.foodUseStatEffect(entityplayer);

			if (entityplayer instanceof EntityPlayerMP)
			{
				CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP) entityplayer, stack);
			}
		}

		stack.decreaseStackSize(1);
		return stack;
	}
	
	public default void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote && this.getPotionEffect() != null && world.rand.nextFloat() < this.getPotionProbability())
		{
			player.addPotionEffect(new PotionEffect(this.getPotionEffect()));
		}
	}
	
	public default EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.EAT;
	}
	
	public default ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (player.canEat(this.isAlawaysEdible()))
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
	
	public default boolean isFood()
	{
		return true;
	}
	
	public abstract int getHealAmount(ItemStack stack);
	public abstract float getSaturationModifier(ItemStack stack);
	public abstract boolean isWolfsFavoriteMeat(); 
	public abstract int getMaxItemUseDuration(ItemStack stack);
	public abstract boolean isAlawaysEdible();
	public abstract PotionEffect getPotionEffect();
	public abstract float getPotionProbability();
	
	public abstract void foodUseStatEffect(EntityPlayer entityplayer);

	public static IFood getFoodFromItem(Item item)
	{
		if (item instanceof DynamicItem)
		{
			return ((DynamicItem) item).getFood();
		}
		else if(item instanceof ItemFood)
		{
			return (ItemFood) item;
		}
		else
		{
			return new NotFood();
		}
	}
}
