package fr.minecraftpp.manager.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.armor.RecipeBoots;
import fr.minecraftpp.crafting.armor.RecipeChestplate;
import fr.minecraftpp.crafting.armor.RecipeHelmet;
import fr.minecraftpp.crafting.armor.RecipeLeggings;
import fr.minecraftpp.crafting.item.RecipeBlock;
import fr.minecraftpp.crafting.item.RecipeItemFromBlock;
import fr.minecraftpp.crafting.tools.RecipeAxe;
import fr.minecraftpp.crafting.tools.RecipeHoe;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.crafting.tools.RecipeSword;
import fr.minecraftpp.manager.block.ModBlocks;
import fr.minecraftpp.manager.item.ModItems;
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
		try
		{
			new RecipeBlock(ModItems.SCENARIUM, ModBlocks.SCENARIUM_BLOCK);
			new RecipeItemFromBlock(ModBlocks.SCENARIUM_BLOCK, ModItems.SCENARIUM);

			new RecipeSword(ModItems.SCENARIUM, ModItems.SCENARIUM_SWORD);
			new RecipePickaxe(ModItems.SCENARIUM, ModItems.SCENARIUM_PICKAXE);
			new RecipeAxe(ModItems.SCENARIUM, ModItems.SCENARIUM_AXE);
			new RecipeShovel(ModItems.SCENARIUM, ModItems.SCENARIUM_SPADE);
			new RecipeHoe(ModItems.SCENARIUM, ModItems.SCENARIUM_HOE);

			new RecipeHelmet(ModItems.SCENARIUM, ModItems.SCENARIUM_HELMET);
			new RecipeChestplate(ModItems.SCENARIUM, ModItems.SCENARIUM_CHESTPLATE);
			new RecipeLeggings(ModItems.SCENARIUM, ModItems.SCENARIUM_LEGGINGS);
			new RecipeBoots(ModItems.SCENARIUM, ModItems.SCENARIUM_BOOTS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		for (int i = 0; i < recipeList.size(); i++)
		{
			CraftingManager.registerRecipe("DynRecipe" + i, recipeList.get(i));
		}
	}
}
