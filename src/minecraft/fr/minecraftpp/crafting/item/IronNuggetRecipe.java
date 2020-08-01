package fr.minecraftpp.crafting.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class IronNuggetRecipe extends ShapedRecipes
{

	public IronNuggetRecipe(String group, int recipeWidth, int recipeHeight, NonNullList<Ingredient> recipeItems, ItemStack recipeOutput)
	{
		super(group, recipeWidth, recipeHeight, recipeItems, recipeOutput);
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		boolean isAMatch = super.matches(inv, worldIn);

		if (isAMatch) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (!inv.getStackInRowAndColumn(i, j).getItem().equals(Items.OLD_IRON_INGOT) && !inv.getStackInRowAndColumn(i, j).getItem().equals(Items.EMPTY_ITEM)) {
						isAMatch = false;
					}
				}
			}
		}
		
		return isAMatch;
	}
}
