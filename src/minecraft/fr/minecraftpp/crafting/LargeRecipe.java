package fr.minecraftpp.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LargeRecipe extends AbstractRecipe
{

	public LargeRecipe(Item result)
	{
		super(result);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		return false;
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixHeight >= 3 && craftingMatrixWidth >= 3;
	}
}
