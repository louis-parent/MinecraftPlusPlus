package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.manager.crafting.ModCraftingManager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

@Mod("Minecraftpp")
public abstract class ModAbstractRecipe implements IRecipe
{
	protected ItemStack recipeResult;

	public ModAbstractRecipe(Item result)
	{
		this(result.getAsStack());
	}

	public ModAbstractRecipe(ItemStack stack)
	{
		this.recipeResult = stack;
		ModCraftingManager.register(this);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return this.getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		ItemStack result = this.recipeResult.copy();
		result.setStackSize(this.getRecipeOutputQuantity());

		return result;
	}

	public int getRecipeOutputQuantity()
	{
		return this.recipeResult.getStackSize();
	}
}
