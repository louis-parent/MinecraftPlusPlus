package fr.minecraftpp.crafting;

import fr.minecraftpp.block.ModBlocks;
import fr.minecraftpp.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ModFurnaceRecipes
{
	private static FurnaceRecipes RECIPES_INSTANCE;

	public static void furnaceRecipes(FurnaceRecipes instance)
	{
		RECIPES_INSTANCE = instance;

		addSmeltingRecipeForBlock(ModBlocks.SCENARITE_ORE, new ItemStack(ModItems.SCENARIUM, 1), 1.2F);
	}

	private static void addSmelting(Item input, ItemStack stack, float experience)
	{
		RECIPES_INSTANCE.addSmelting(input, stack, experience);
	}

	private static void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
	{
		RECIPES_INSTANCE.addSmeltingRecipe(input, stack, experience);
	}

	private static void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	{
		RECIPES_INSTANCE.addSmeltingRecipeForBlock(input, stack, experience);
	}
}
