package fr.minecraftpp.crafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class ShapelessRecipe extends ModAbstractRecipe
{
	private List<Item> recipeRequirements;

	public ShapelessRecipe(ItemStack result, List<Item> recipeRequirements)
	{
		super(result);
		this.recipeRequirements = recipeRequirements;
	}

	public ShapelessRecipe(ItemStack result, Item... recipeRequirements)
	{
		this(result, Arrays.asList(recipeRequirements));
	}

	public ShapelessRecipe(Item result, List<Item> recipeRequirements)
	{
		this(result.getAsStack(), recipeRequirements);
	}

	public ShapelessRecipe(Item result, Item... recipeRequirements)
	{
		this(result, Arrays.asList(recipeRequirements));
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		boolean match = true;
		Map<Item, Integer> found = this.getItemMap();

		for (int i = 0; i < inv.getWidth(); i++)
		{
			for (int j = 0; j < inv.getHeight(); j++)
			{
				Item item = inv.getStackInRowAndColumn(i, j).getItem();

				if (this.recipeRequirements.contains(item))
				{
					found.replace(item, found.get(item) - 1);
				}
				else
				{
					match &= item == Items.getItem(Items.AIR);
				}
			}
		}

		return match && this.areAllItemsFound(found);
	}

	private Map<Item, Integer> getItemMap()
	{
		HashMap<Item, Integer> map = new HashMap<Item, Integer>();

		for (Item item : this.recipeRequirements)
		{
			if (map.containsKey(item))
			{
				map.replace(item, map.get(item) + 1);
			}
			else
			{
				map.put(item, 1);
			}
		}

		return map;
	}

	private boolean areAllItemsFound(Map<Item, Integer> found)
	{
		boolean allZero = true;

		for (Map.Entry<Item, Integer> entry : found.entrySet())
		{
			allZero &= entry.getValue() == 0;
		}

		return allZero;
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
