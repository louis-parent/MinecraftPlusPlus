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
public class RecipeBlockToItem extends AbstractRecipe
{

	private Block material;

	public RecipeBlockToItem(Block material, Item result)
	{
		super(result);
		this.material = material;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		int counter = 0;
		for (int i = 0; i < inv.getWidth(); i++)
		{
			for (int j = 0; j < inv.getHeight(); j++)
			{
				ItemStack itemstack = inv.getStackInRowAndColumn(i, j);

				if (!itemstack.isNotValid())
				{
					Item item = itemstack.getItem();
					Block block = Block.getBlockFromItem(item);

					if (block == material)
					{
						counter++;
					}
				}
			}
		}
		if (counter == 1)
		{
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixHeight * craftingMatrixHeight == 1;
	}

	@Override
	public int getRecipeOutputQuantity()
	{
		return 9;
	}

	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.<Ingredient>getInstanceFilledWith(1, Ingredient.getIngredientFromItemStack(new ItemStack(material)));
	}
}
