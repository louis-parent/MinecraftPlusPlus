package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.item.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

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
