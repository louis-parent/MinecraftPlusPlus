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
public class RecipeBlock extends ModRecipe
{

	private Item material;

	public RecipeBlock(Item material, Block result)
	{
		super(null, Item.getItemFromBlock(result));
		this.material = material;
		new RecipeBlockToItem(result, material);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		if (inv.getWidth() == 3 && inv.getHeight() == 3)
		{
			for (int i = 0; i < inv.getWidth(); i++)
			{
				for (int j = 0; j < inv.getHeight(); j++)
				{
					ItemStack itemstack = inv.getStackInRowAndColumn(i, j);

					if (itemstack.isNotValid())
					{
						return false;
					}

					Item item = itemstack.getItem();

					if (item != material)
					{
						return false;
					}
				}
			}

			return true;
		}
		else
		{
			return false;
		}
	}	

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixHeight >= 3 && craftingMatrixWidth >= 3;
	}

	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.<Ingredient>getInstanceFilledWith(9, Ingredient.getIngredientFromItemStack(new ItemStack(material)));
	}
}
