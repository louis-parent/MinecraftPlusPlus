package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShieldRecipes
{
	public static class Decoration implements IRecipe
	{
		@Override
		public boolean matches(InventoryCrafting inv, World worldIn)
		{
			ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;
			ItemStack itemstack1 = ItemStack.EMPTY_ITEM_STACK;

			for (int i = 0; i < inv.getSizeInventory(); ++i)
			{
				ItemStack itemstack2 = inv.getStackInSlot(i);

				if (!itemstack2.isNotValid())
				{
					if (itemstack2.getItem() == Items.getItem(Items.BANNER))
					{
						if (!itemstack1.isNotValid())
						{
							return false;
						}

						itemstack1 = itemstack2;
					}
					else
					{
						if (itemstack2.getItem() != Items.getItem(Items.SHIELD))
						{
							return false;
						}

						if (!itemstack.isNotValid())
						{
							return false;
						}

						if (itemstack2.getSubCompound("BlockEntityTag") != null)
						{
							return false;
						}

						itemstack = itemstack2;
					}
				}
			}

			if (!itemstack.isNotValid() && !itemstack1.isNotValid())
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv)
		{
			ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;
			ItemStack itemstack1 = ItemStack.EMPTY_ITEM_STACK;

			for (int i = 0; i < inv.getSizeInventory(); ++i)
			{
				ItemStack itemstack2 = inv.getStackInSlot(i);

				if (!itemstack2.isNotValid())
				{
					if (itemstack2.getItem() == Items.getItem(Items.BANNER))
					{
						itemstack = itemstack2;
					}
					else if (itemstack2.getItem() == Items.getItem(Items.SHIELD))
					{
						itemstack1 = itemstack2.copy();
					}
				}
			}

			if (itemstack1.isNotValid())
			{
				return itemstack1;
			}
			else
			{
				NBTTagCompound nbttagcompound = itemstack.getSubCompound("BlockEntityTag");
				NBTTagCompound nbttagcompound1 = nbttagcompound == null ? new NBTTagCompound() : nbttagcompound.copy();
				nbttagcompound1.setInteger("Base", itemstack.getMetadata() & 15);
				itemstack1.setTagInfo("BlockEntityTag", nbttagcompound1);
				return itemstack1;
			}
		}

		@Override
		public ItemStack getRecipeOutput()
		{
			return ItemStack.EMPTY_ITEM_STACK;
		}

		@Override
		public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
		{
			NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>getInstanceFilledWith(inv.getSizeInventory(), ItemStack.EMPTY_ITEM_STACK);

			for (int i = 0; i < nonnulllist.size(); ++i)
			{
				ItemStack itemstack = inv.getStackInSlot(i);

				if (itemstack.getItem().hasContainerItem())
				{
					nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
				}
			}

			return nonnulllist;
		}

		@Override
		public boolean hideInCraftingTabs()
		{
			return true;
		}

		@Override
		public boolean checkIfCraftingMatrixSizeIsCorrect(int p_194133_1_, int p_194133_2_)
		{
			return p_194133_1_ * p_194133_2_ >= 2;
		}
	}
}
