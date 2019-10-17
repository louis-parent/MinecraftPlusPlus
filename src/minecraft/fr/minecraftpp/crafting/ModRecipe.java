package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.crafting.blueprint.Blueprint;
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
public class ModRecipe implements IRecipe
{
	static List<ModRecipe> recipeList = new ArrayList();
	
	protected Blueprint blueprint;
	protected Item recipeResult;

	public ModRecipe(Blueprint blueprint, Item result)
	{
		recipeList.add(this);
		this.recipeResult = result;
		this.blueprint = blueprint;
	}

	/*
	 * TODO Try/Catch pour IllegalArgument des blueprints
	 * TODO Extract to manager
	 */
	public static void registerRecipes()
	{
		new RecipeBlock(ModItems.SCENARIUM, ModBlocks.SCENARIUM_BLOCK);
		new RecipePickaxe(ModItems.SCENARIUM, ModItems.SCENARIUM_PICKAXE);
		
		for (int i = 0; i < recipeList.size(); i++)
		{
			CraftingManager.registerRecipe("DynRecipe" + i, recipeList.get(i));
		}
	}
	
	@Override 
	public boolean matches(InventoryCrafting inv, World world)
	{
		return this.blueprint.matches(inv);
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

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixWidth >= this.blueprint.getWidth() && craftingMatrixHeight >= this.blueprint.getHeight();
	}
	
	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.getInstanceWith(Ingredient.INGREDIENT_AIR, this.blueprint.toIngredients());
	}
}
