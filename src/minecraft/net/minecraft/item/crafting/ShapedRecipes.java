package net.minecraft.item.crafting;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import fr.minecraftpp.crafting.item.IronNuggetRecipe;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShapedRecipes implements IRecipe
{
	/** How many horizontal slots this recipe is wide. */
	protected final int recipeWidth;

	/** How many vertical slots this recipe uses. */
	protected final int recipeHeight;
	private final NonNullList<Ingredient> recipeItems;

	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;
	private final String group;

	public ShapedRecipes(String group, int recipeWidth, int recipeHeight, NonNullList<Ingredient> recipeItems, ItemStack recipeOutput)
	{
		this.group = group;
		this.recipeWidth = recipeWidth;
		this.recipeHeight = recipeHeight;
		this.recipeItems = recipeItems;
		this.recipeOutput = recipeOutput;
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

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int p_194133_1_, int p_194133_2_)
	{
		return p_194133_1_ >= this.recipeWidth && p_194133_2_ >= this.recipeHeight;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		for (int i = 0; i <= 3 - this.recipeWidth; ++i)
		{
			for (int j = 0; j <= 3 - this.recipeHeight; ++j)
			{
				if (this.checkMatch(inv, i, j, true))
				{
					return true;
				}

				if (this.checkMatch(inv, i, j, false))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	protected boolean checkMatch(InventoryCrafting inventory, int width, int height, boolean checkSymetry)
	{
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				int k = i - width;
				int l = j - height;
				Ingredient ingredient = Ingredient.INGREDIENT_AIR;

				if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
				{
					if (checkSymetry)
					{
						ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
					}
					else
					{
						ingredient = this.recipeItems.get(k + l * this.recipeWidth);
					}
				}

				if (!ingredient.apply(inventory.getStackInRowAndColumn(i, j)))
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return this.getRecipeOutput().copy();
	}

	public int func_192403_f()
	{
		return this.recipeWidth;
	}

	public int func_192404_g()
	{
		return this.recipeHeight;
	}

	public static ShapedRecipes buildRecipe(JsonObject json)
	{
		String group = JsonUtils.getString(json, "group", "");
		Map<String, Ingredient> map = getKeyMap(JsonUtils.getJsonObject(json, "key"));
		String[] pattern = func_194134_a(func_192407_a(JsonUtils.getJsonArray(json, "pattern")));
		int width = pattern[0].length();
		int height = pattern.length;
		NonNullList<Ingredient> nonnulllist = buildPatternWithItems(pattern, map, width, height);
		ItemStack result = getResult(JsonUtils.getJsonObject(json, "result"), true);

		ShapedRecipes recipe;
		
		if (result.getItem().equals(Items.IRON_NUGGET))
		{
			recipe = new IronNuggetRecipe(group, width, height, nonnulllist, result);
		}
		else
		{
			recipe = new ShapedRecipes(group, width, height, nonnulllist, result);
		}
		
		return recipe;
	}

	private static NonNullList<Ingredient> buildPatternWithItems(String[] pattern, Map<String, Ingredient> keyMap, int width, int height)
	{
		NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>getInstanceFilledWith(width * height, Ingredient.INGREDIENT_AIR);
		Set<String> set = Sets.newHashSet(keyMap.keySet());
		set.remove(" ");

		for (int i = 0; i < pattern.length; ++i)
		{
			for (int j = 0; j < pattern[i].length(); ++j)
			{
				String s = pattern[i].substring(j, j + 1);
				Ingredient ingredient = keyMap.get(s);

				if (ingredient == null)
				{
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}

				set.remove(s);
				nonnulllist.set(j + width * i, ingredient);
			}
		}

		if (!set.isEmpty())
		{
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		}
		else
		{
			return nonnulllist;
		}
	}

	@VisibleForTesting
	static String[] func_194134_a(String... p_194134_0_)
	{
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for (int i1 = 0; i1 < p_194134_0_.length; ++i1)
		{
			String s = p_194134_0_[i1];
			i = Math.min(i, func_194135_a(s));
			int j1 = func_194136_b(s);
			j = Math.max(j, j1);

			if (j1 < 0)
			{
				if (k == i1)
				{
					++k;
				}

				++l;
			}
			else
			{
				l = 0;
			}
		}

		if (p_194134_0_.length == l)
		{
			return new String[0];
		}
		else
		{
			String[] astring = new String[p_194134_0_.length - l - k];

			for (int k1 = 0; k1 < astring.length; ++k1)
			{
				astring[k1] = p_194134_0_[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int func_194135_a(String p_194135_0_)
	{
		int i;

		for (i = 0; i < p_194135_0_.length() && p_194135_0_.charAt(i) == ' '; ++i)
		{
			;
		}

		return i;
	}

	private static int func_194136_b(String p_194136_0_)
	{
		int i;

		for (i = p_194136_0_.length() - 1; i >= 0 && p_194136_0_.charAt(i) == ' '; --i)
		{
			;
		}

		return i;
	}

	private static String[] func_192407_a(JsonArray p_192407_0_)
	{
		String[] astring = new String[p_192407_0_.size()];

		if (astring.length > 3)
		{
			throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		}
		else if (astring.length == 0)
		{
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		}
		else
		{
			for (int i = 0; i < astring.length; ++i)
			{
				String s = JsonUtils.getString(p_192407_0_.get(i), "pattern[" + i + "]");

				if (s.length() > 3)
				{
					throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
				}

				if (i > 0 && astring[0].length() != s.length())
				{
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				astring[i] = s;
			}

			return astring;
		}
	}

	private static Map<String, Ingredient> getKeyMap(JsonObject p_192408_0_)
	{
		Map<String, Ingredient> map = Maps.<String, Ingredient>newHashMap();

		for (Entry<String, JsonElement> entry : p_192408_0_.entrySet())
		{
			if (entry.getKey().length() != 1)
			{
				throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry.getKey()))
			{
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(entry.getKey(), func_193361_a(entry.getValue()));
		}

		map.put(" ", Ingredient.INGREDIENT_AIR);
		return map;
	}

	public static Ingredient func_193361_a(@Nullable JsonElement p_193361_0_)
	{
		if (p_193361_0_ != null && !p_193361_0_.isJsonNull())
		{
			if (p_193361_0_.isJsonObject())
			{
				return Ingredient.getIngredientFromItemStack(getResult(p_193361_0_.getAsJsonObject(), false));
			}
			else if (!p_193361_0_.isJsonArray())
			{
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			}
			else
			{
				JsonArray jsonarray = p_193361_0_.getAsJsonArray();

				if (jsonarray.size() == 0)
				{
					throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
				}
				else
				{
					ItemStack[] aitemstack = new ItemStack[jsonarray.size()];

					for (int i = 0; i < jsonarray.size(); ++i)
					{
						aitemstack[i] = getResult(JsonUtils.getJsonObject(jsonarray.get(i), "item"), false);
					}

					return Ingredient.getIngredientFromItemStack(aitemstack);
				}
			}
		}
		else
		{
			throw new JsonSyntaxException("Item cannot be null");
		}
	}

	public static ItemStack getResult(JsonObject p_192405_0_, boolean p_192405_1_)
	{
		String s = JsonUtils.getString(p_192405_0_, "item");
		Item item = Item.REGISTRY.getObject(new ResourceLocation(s));

		if (item == null)
		{
			throw new JsonSyntaxException("Unknown item '" + s + "'");
		}
		else if (item.getHasSubtypes() && !p_192405_0_.has("data"))
		{
			throw new JsonParseException("Missing data for item '" + s + "'");
		}
		else
		{
			int i = JsonUtils.getInt(p_192405_0_, "data", 0);
			int j = p_192405_1_ ? JsonUtils.getInt(p_192405_0_, "count", 1) : 1;
			return new ItemStack(item, j, i);
		}
	}
}
