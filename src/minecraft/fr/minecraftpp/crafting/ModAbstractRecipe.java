package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

@Mod("Minecraftpp")
public abstract class ModAbstractRecipe implements IRecipe
{
	protected Item recipeResult;

	public ModAbstractRecipe(Item result)
	{
		ModCraftingManager.register(this);
		this.recipeResult = result;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return this.getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(recipeResult, this.getRecipeOutputQuantity());
	}

	public int getRecipeOutputQuantity()
	{
		return 1;
	}
}
