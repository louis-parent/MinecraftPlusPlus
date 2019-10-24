package net.minecraft.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryCraftResult implements IInventory
{
	private final NonNullList<ItemStack> stackResult = NonNullList.<ItemStack>getInstanceFilledWith(1, ItemStack.EMPTY_ITEM_STACK);
	private IRecipe field_193057_b;

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public boolean isStackNotValid()
	{
		for (ItemStack itemstack : this.stackResult)
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
		return this.stackResult.get(0);
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName()
	{
		return "Result";
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
	 * Removes up to a specified number of items from an inventory slot and
	 * returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndRemove(this.stackResult, 0);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.stackResult, 0);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stackResult.set(0, stack);
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
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty()
	{
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with
	 * Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
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
		this.stackResult.clear();
	}

	public void func_193056_a(@Nullable IRecipe p_193056_1_)
	{
		this.field_193057_b = p_193056_1_;
	}

	@Nullable
	public IRecipe func_193055_i()
	{
		return this.field_193057_b;
	}
}
