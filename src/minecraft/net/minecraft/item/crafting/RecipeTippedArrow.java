package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeTippedArrow implements IRecipe
{
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		if (inv.getWidth() == 3 && inv.getHeight() == 3)
		{
			for (int i = 0; i < inv.getWidth(); ++i)
			{
				for (int j = 0; j < inv.getHeight(); ++j)
				{
					ItemStack itemstack = inv.getStackInRowAndColumn(i, j);

					if (itemstack.isNotValid())
					{
						return false;
					}

					Item item = itemstack.getItem();

					if (i == 1 && j == 1)
					{
						if (item != Items.LINGERING_POTION)
						{
							return false;
						}
					}
					else if (item != Items.ARROW)
					{
						return false;
					}
				}
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack itemstack = inv.getStackInRowAndColumn(1, 1);

		if (itemstack.getItem() != Items.LINGERING_POTION)
		{
			return ItemStack.EMPTY_ITEM_STACK;
		}
		else
		{
			ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
			PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
			PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
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
		return NonNullList.<ItemStack>getInstanceFilledWith(inv.getSizeInventory(), ItemStack.EMPTY_ITEM_STACK);
	}

	@Override
	public boolean hideInCraftingTabs()
	{
		return true;
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int p_194133_1_, int p_194133_2_)
	{
		return p_194133_1_ >= 2 && p_194133_2_ >= 2;
	}
}
