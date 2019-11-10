package net.minecraft.item.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import fr.minecraftpp.variant.Variant;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ShapelessRecipes implements IRecipe
{
	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;
	private final NonNullList<Ingredient> recipeItems;
	private final String group;

	public ShapelessRecipes(String group, ItemStack output, NonNullList<Ingredient> ingredients)
	{
		this.group = group;
		this.recipeOutput = output;
		this.recipeItems = ingredients;
	}

	@Override
	public String getRecipeGroup()
	{
		return this.group;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return this.recipeItems;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		List<Ingredient> list = Lists.newArrayList(this.recipeItems);

		for (int i = 0; i < inv.getHeight(); ++i)
		{
			for (int j = 0; j < inv.getWidth(); ++j)
			{
				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);

				if (!itemstack.isNotValid())
				{
					boolean flag = false;

					for (Ingredient ingredient : list)
					{
						if (ingredient.apply(itemstack))
						{
							flag = true;
							list.remove(ingredient);
							break;
						}
					}

					if (!flag)
					{
						return false;
					}
				}
			}
		}

		return list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return this.recipeOutput.copy();
	}

	public static ShapelessRecipes buildRecipe(JsonObject p_193363_0_)
	{
		String s = JsonUtils.getString(p_193363_0_, "group", "");
		NonNullList<Ingredient> nonnulllist = func_193364_a(JsonUtils.getJsonArray(p_193363_0_, "ingredients"));

		if (nonnulllist.isEmpty())
		{
			throw new JsonParseException("No ingredients for shapeless recipe");
		}
		else if (nonnulllist.size() > 9)
		{
			throw new JsonParseException("Too many ingredients for shapeless recipe");
		}
		else
		{
			ItemStack itemstack = ShapedRecipes.getResult(JsonUtils.getJsonObject(p_193363_0_, "result"), true);
			return new ShapelessRecipes(s, itemstack, nonnulllist);
		}
	}

	private static NonNullList<Ingredient> func_193364_a(JsonArray p_193364_0_)
	{
		NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>getInstance();

		for (int i = 0; i < p_193364_0_.size(); ++i)
		{
			Ingredient ingredient = ShapedRecipes.func_193361_a(p_193364_0_.get(i));

			if (ingredient != Ingredient.INGREDIENT_AIR)
			{
				nonnulllist.add(ingredient);
			}
		}

		return nonnulllist;
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int p_194133_1_, int p_194133_2_)
	{
		return p_194133_1_ * p_194133_2_ >= this.recipeItems.size();
	}
}
