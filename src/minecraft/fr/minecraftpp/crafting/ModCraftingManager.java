package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.item.ModItems;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ModCraftingManager
{
	private static List<IRecipe> recipeList = new ArrayList();

	public static void register(IRecipe recipe)
	{
		recipeList.add(recipe);
	}
	
	/*
	 * TODO Try/Catch pour IllegalArgument des blueprints
	 */
	public static void registerRecipes()
	{
		try
		{
			new RecipeBlock(ModItems.SCENARIUM, ModBlocks.SCENARIUM_BLOCK);
			new RecipePickaxe(ModItems.SCENARIUM, ModItems.SCENARIUM_PICKAXE);
			new RecipeShovel(ModItems.SCENARIUM, ModItems.SCENARIUM_SPADE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < recipeList.size(); i++)
		{
			CraftingManager.registerRecipe("DynRecipe" + i, recipeList.get(i));
		}
	}
}
