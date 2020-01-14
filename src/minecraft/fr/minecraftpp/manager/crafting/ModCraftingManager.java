package fr.minecraftpp.manager.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
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

	public static void registerRecipes()
	{
		for (int i = 0; i < recipeList.size(); i++)
		{
			CraftingManager.registerRecipe("DynRecipe" + i, recipeList.get(i));
		}
	}

	public static void resetRegistry()
	{
		recipeList.clear();
	}

}
