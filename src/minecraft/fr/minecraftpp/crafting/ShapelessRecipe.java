package fr.minecraftpp.crafting;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShapelessRecipe extends ModAbstractRecipe
{
	private List<Item> recipeRequirements;
	
	public ShapelessRecipe(Item result, List<Item> recipeRequirements)
	{
		super(result);
		this.recipeRequirements = recipeRequirements;
	}
	
	public ShapelessRecipe(Item result, Item... recipeRequirements)
	{
		this(result, Arrays.asList(recipeRequirements));
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		boolean match = true;
		int requirementFound = 0;
		
		for(int i = 0; i < inv.getWidth(); i++)
		{
			for(int j = 0; j < inv.getHeight(); j++)
			{
				Item item = inv.getStackInRowAndColumn(i, j).getItem();
				
				if(this.recipeRequirements.contains(item))
				{
					requirementFound++;
				}
				else
				{
					match &= item == Items.EMPTY_ITEM;
				}
			}
		}
		
		return match && requirementFound == this.recipeRequirements.size();
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return (craftingMatrixWidth * craftingMatrixHeight) >= this.recipeRequirements.size();
	}
	
	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.getInstanceWith(Ingredient.INGREDIENT_AIR, this.getIngredientsArray());
	}

	private Ingredient[] getIngredientsArray()
	{
		Ingredient[] ingredients = new Ingredient[this.recipeRequirements.size()];
		
		for (int i = 0; i < this.recipeRequirements.size(); i++)
		{
			ingredients[i] = Ingredient.getIngredientFromItemStack(this.recipeRequirements.get(i).getAsStack());
		}
		
		return ingredients;
	}

}
