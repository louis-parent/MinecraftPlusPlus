package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.item.ModItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod("Minecraftpp")
public abstract class AbstractRecipe implements IRecipe
{
	static List<AbstractRecipe> recipeList = new ArrayList();
	
	protected Item recipeResult;

	public AbstractRecipe(Item result)
	{
		recipeList.add(this);
		this.recipeResult = result;
	}

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
