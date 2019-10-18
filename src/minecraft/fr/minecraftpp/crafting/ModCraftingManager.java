package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.crafting.tools.RecipeAxe;
import fr.minecraftpp.crafting.tools.RecipeHoe;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.crafting.tools.RecipeSword;
import fr.minecraftpp.item.ModItems;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod("Minecraftpp")
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
			new RecipeSword(ModItems.SCENARIUM, ModItems.SCENARIUM_SWORD);
			new RecipePickaxe(ModItems.SCENARIUM, ModItems.SCENARIUM_PICKAXE);
			new RecipeAxe(ModItems.SCENARIUM, ModItems.SCENARIUM_AXE);
			new RecipeShovel(ModItems.SCENARIUM, ModItems.SCENARIUM_SPADE);
			new RecipeHoe(ModItems.SCENARIUM, ModItems.SCENARIUM_HOE);
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
