package fr.minecraftpp.manager.crafting;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ModFurnaceRecipes
{
	private static List<FurnaceRecipe> toAdd = new ArrayList<FurnaceRecipe>();
	private static FurnaceRecipes RECIPES_INSTANCE;

	public static void furnaceRecipes(FurnaceRecipes instance)
	{
		RECIPES_INSTANCE = instance;

		for (FurnaceRecipe furnaceRecipe : toAdd)
		{
			addSmeltingRecipe(furnaceRecipe.getInput(), furnaceRecipe.getOutput(), furnaceRecipe.getExperience());
		}
	}

	public static void addSmelting(FurnaceRecipe smelting)
	{
		toAdd.add(smelting);
	}

	public static void addBlockSmelting(Block input, ItemStack stack, float experience)
	{
		addItemSmelting(Item.getItemFromBlock(input), stack, experience);
	}

	public static void addItemSmelting(Item input, ItemStack stack, float experience)
	{
		addSmeltingRecipe(input.getAsStack(), stack, experience);
	}

	private static void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
	{
		RECIPES_INSTANCE.addSmeltingRecipe(input, stack, experience);
	}

}
