package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory
{
	private final IMerchant theMerchant;
	private final NonNullList<ItemStack> theInventory = NonNullList.<ItemStack>getInstanceFilledWith(3, ItemStack.EMPTY_ITEM_STACK);
	private final EntityPlayer thePlayer;
	private MerchantRecipe currentRecipe;
	private int currentRecipeIndex;

	public InventoryMerchant(EntityPlayer thePlayerIn, IMerchant theMerchantIn)
	{
		this.thePlayer = thePlayerIn;
		this.theMerchant = theMerchantIn;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return this.theInventory.size();
	}

	@Override
	public boolean isStackNotValid()
	{
		for (ItemStack itemstack : this.theInventory)
		{
			if (!itemstack.isNotValid())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.theInventory.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = this.theInventory.get(index);

		if (index == 2 && !itemstack.isNotValid())
		{
			return ItemStackHelper.getAndSplit(this.theInventory, index, itemstack.getStackSize());
		}
		else
		{
			ItemStack itemstack1 = ItemStackHelper.getAndSplit(this.theInventory, index, count);

			if (!itemstack1.isNotValid() && this.inventoryResetNeededOnSlotChange(index))
			{
				this.resetRecipeAndSlots();
			}

			return itemstack1;
		}
	}

	/**
	 * if par1 slot has changed, does resetRecipeAndSlots need to be called?
	 */
	private boolean inventoryResetNeededOnSlotChange(int slotIn)
	{
		return slotIn == 0 || slotIn == 1;
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.theInventory, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.theInventory.set(index, stack);

		if (!stack.isNotValid() && stack.getStackSize() > this.getInventoryStackLimit())
		{
			stack.setStackSize(this.getInventoryStackLimit());
		}

		if (this.inventoryResetNeededOnSlotChange(index))
		{
			this.resetRecipeAndSlots();
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName()
	{
		return "mob.villager";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's
	 * username in chat
	 */
	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with
	 * Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.theMerchant.getCustomer() == player;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot. For guis use Slot.isItemValid
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty()
	{
		this.resetRecipeAndSlots();
	}

	public void resetRecipeAndSlots()
	{
		this.currentRecipe = null;
		ItemStack itemstack = this.theInventory.get(0);
		ItemStack itemstack1 = this.theInventory.get(1);

		if (itemstack.isNotValid())
		{
			itemstack = itemstack1;
			itemstack1 = ItemStack.EMPTY_ITEM_STACK;
		}

		if (itemstack.isNotValid())
		{
			this.setInventorySlotContents(2, ItemStack.EMPTY_ITEM_STACK);
		}
		else
		{
			MerchantRecipeList merchantrecipelist = this.theMerchant.getRecipes(this.thePlayer);

			if (merchantrecipelist != null)
			{
				MerchantRecipe merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack, itemstack1, this.currentRecipeIndex);

				if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled())
				{
					this.currentRecipe = merchantrecipe;
					this.setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
				}
				else if (!itemstack1.isNotValid())
				{
					merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack1, itemstack, this.currentRecipeIndex);

					if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled())
					{
						this.currentRecipe = merchantrecipe;
						this.setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
					}
					else
					{
						this.setInventorySlotContents(2, ItemStack.EMPTY_ITEM_STACK);
					}
				}
				else
				{
					this.setInventorySlotContents(2, ItemStack.EMPTY_ITEM_STACK);
				}
			}

			this.theMerchant.verifySellingItem(this.getStackInSlot(2));
		}
	}

	public MerchantRecipe getCurrentRecipe()
	{
		return this.currentRecipe;
	}

	public void setCurrentRecipeIndex(int currentRecipeIndexIn)
	{
		this.currentRecipeIndex = currentRecipeIndexIn;
		this.resetRecipeAndSlots();
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		this.theInventory.clear();
	}
}
