package fr.minecraftpp.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

@Mod("Minecraftpp")
public abstract class GenericRecipe implements IRecipe {

	static List<GenericRecipe> recipeList = new ArrayList();
	
	public GenericRecipe() {
		recipeList.add(this);
	}
	
	public abstract String getRecipePath();
	
	public static void registerRecipes() {
		for (GenericRecipe recipe : recipeList) {
			CraftingManager.registerRecipe(recipe.getRecipePath(), recipe);
		}
	}
	
}
