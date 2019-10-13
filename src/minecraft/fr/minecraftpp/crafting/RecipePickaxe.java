package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.item.tool.IToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class RecipePickaxe extends GenericRecipe
{

	private Item material;
	private Item recipeResult;

	public RecipePickaxe(Item material, Item result)
	{
		super();
		this.material = material;
		this.recipeResult = result;
	}

	@Override
	public boolean matches(InventoryCrafting craftMatrix, World worldIn)
	{
		System.out.println();
		if (craftMatrix.getWidth() == 3 && craftMatrix.getHeight() == 3)
		{
			boolean isCraftMatrixCorrect = true;
			for (int i = 0; i < craftMatrix.getWidth(); i++)
			{
				for (int j = 0; j < craftMatrix.getHeight(); j++)
				{
					ItemStack itemstack = craftMatrix.getStackInRowAndColumn(i, j);

					System.out.println("-----" + i + "---------" + j + "--------" + itemstack.getDisplayName());
					if (!itemstack.isNotValid() || (j != 0 && i != 1))
					{
						Item item = itemstack.getItem();
						System.out.println((!(itemstack.isNotValid())) + " && " + ((i == 0 && item != material) + " || " + (j == 1 && item != Items.STICK)));

						if (!(itemstack.isNotValid()))
						{
							if ((i == 0 && item != material) || (j == 1 && item != Items.STICK))
							{
								isCraftMatrixCorrect &= false;
							}
						}
					}
					else
					{
						isCraftMatrixCorrect &= false;
					}
					System.out.println((!itemstack.isNotValid()) + " || (" + (j != 0) + " && " + (i != 1) + ")");
				}
			}
			return isCraftMatrixCorrect;
		}
		else
		{
			return false;
		}
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		return new ItemStack(recipeResult);
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixHeight >= 3 && craftingMatrixWidth >= 3;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return new ItemStack(recipeResult);
	}

	@Override
	public String getRecipeGroup()
	{
		return recipeResult.getItemStackDisplayName(new ItemStack(recipeResult));
	}

	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.<Ingredient>getInstanceFilledWith(1, Ingredient.getIngredientFromItemStack(new ItemStack(material)));
	}

	@Override
	public String getRecipePath()
	{
		return "from" + material.getItemStackDisplayName(new ItemStack(material)) + "To" + recipeResult.getItemStackDisplayName(new ItemStack(recipeResult));
	}

}
