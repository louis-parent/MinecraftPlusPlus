package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public interface IRecipe
{
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	boolean matches(InventoryCrafting inv, World world);

	/**
	 * Returns an Item that is the result of this recipe
	 */
	ItemStack getCraftingResult(InventoryCrafting inv);

	boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight);

	ItemStack getRecipeOutput();

	default NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		NonNullList<ItemStack> list = NonNullList.<ItemStack>getInstanceFilledWith(inv.getSizeInventory(), ItemStack.EMPTY_ITEM_STACK);

		for (int i = 0; i < list.size(); ++i)
		{
			ItemStack itemstack = inv.getStackInSlot(i);

			if (itemstack.getItem().hasContainerItem())
			{
				list.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
			}
		}

		return list;
	}

	default NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.<Ingredient>getInstance();
	}

	default boolean hideInCraftingTabs()
	{
		return false;
	}

	default String getRecipeGroup()
	{
		return "";
	}
}
