package fr.minecraftpp.crafting;

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

public class RecipeBlock implements IRecipe {
	
	private Item material;
	private Block recipeResult;
	
	public RecipeBlock(Item material, Block result) {
		this.material = material;
		this.recipeResult = result;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
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
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(recipeResult);
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight) {
		return craftingMatrixHeight >= 3 && craftingMatrixWidth >= 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(recipeResult);
	}

	@Override
    public String getRecipeGroup()
    {
        return "scenarite_ore";
    }
	
	@Override
	public NonNullList<Ingredient> getListOfIngredients() {
		return NonNullList.<Ingredient>getInstanceFilledWith(9, Ingredient.getIngredientFromItemStack(new ItemStack(material)));
	}

}
