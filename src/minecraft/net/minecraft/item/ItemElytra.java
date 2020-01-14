package net.minecraft.item;

import javax.annotation.Nullable;

import fr.minecraftpp.inventory.EntityEquipmentSlot;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemElytra extends Item
{
	public ItemElytra()
	{
		this.maxStackSize = 1;
		this.setMaxDamage(432);
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
		this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter()
		{
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return ItemElytra.isBroken(stack) ? 0.0F : 1.0F;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	}

	public static boolean isBroken(ItemStack stack)
	{
		return stack.getItemDamage() < stack.getMaxDamage() - 1;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == Items.getItem(Items.LEATHER);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn)
	{
		ItemStack itemstack = worldIn.getHeldItem(playerIn);
		EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
		ItemStack itemstack1 = worldIn.getItemStackFromSlot(entityequipmentslot);

		if (itemstack1.isNotValid())
		{
			worldIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
			itemstack.setStackSize(0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}
}
