package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot
{
	/** Merchant's inventory. */
	private final InventoryMerchant theMerchantInventory;

	/** The Player whos trying to buy/sell stuff. */
	private final EntityPlayer thePlayer;
	private int removeCount;

	/** "Instance" of the Merchant. */
	private final IMerchant theMerchant;

	public SlotMerchantResult(EntityPlayer player, IMerchant merchant, InventoryMerchant merchantInventory, int slotIndex, int xPosition, int yPosition)
	{
		super(merchantInventory, slotIndex, xPosition, yPosition);
		this.thePlayer = player;
		this.theMerchant = merchant;
		this.theMerchantInventory = merchantInventory;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor
	 * slots as well as furnace fuel.
	 */
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of
	 * the second int arg. Returns the new stack.
	 */
	@Override
	public ItemStack decrStackSize(int amount)
	{
		if (this.getHasStack())
		{
			this.removeCount += Math.min(amount, this.getStack().getStackSize());
		}

		return super.decrStackSize(amount);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood. Typically increases an internal count then calls
	 * onCrafting(item).
	 */
	@Override
	protected void onCrafting(ItemStack stack, int amount)
	{
		this.removeCount += amount;
		this.onCrafting(stack);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood.
	 */
	@Override
	protected void onCrafting(ItemStack stack)
	{
		stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);
		this.removeCount = 0;
	}

	@Override
	public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_)
	{
		this.onCrafting(p_190901_2_);
		MerchantRecipe merchantrecipe = this.theMerchantInventory.getCurrentRecipe();

		if (merchantrecipe != null)
		{
			ItemStack itemstack = this.theMerchantInventory.getStackInSlot(0);
			ItemStack itemstack1 = this.theMerchantInventory.getStackInSlot(1);

			if (this.doTrade(merchantrecipe, itemstack, itemstack1) || this.doTrade(merchantrecipe, itemstack1, itemstack))
			{
				this.theMerchant.useRecipe(merchantrecipe);
				p_190901_1_.addStat(StatList.TRADED_WITH_VILLAGER);
				this.theMerchantInventory.setInventorySlotContents(0, itemstack);
				this.theMerchantInventory.setInventorySlotContents(1, itemstack1);
			}
		}

		return p_190901_2_;
	}

	private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem)
	{
		ItemStack itemstack = trade.getItemToBuy();
		ItemStack itemstack1 = trade.getSecondItemToBuy();

		if (firstItem.getItem() == itemstack.getItem() && firstItem.getStackSize() >= itemstack.getStackSize())
		{
			if (!itemstack1.isNotValid() && !secondItem.isNotValid() && itemstack1.getItem() == secondItem.getItem() && secondItem.getStackSize() >= itemstack1.getStackSize())
			{
				firstItem.decreaseStackSize(itemstack.getStackSize());
				secondItem.decreaseStackSize(itemstack1.getStackSize());
				return true;
			}

			if (itemstack1.isNotValid() && secondItem.isNotValid())
			{
				firstItem.decreaseStackSize(itemstack.getStackSize());
				return true;
			}
		}

		return false;
	}
}
