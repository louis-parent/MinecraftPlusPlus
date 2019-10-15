package fr.minecraftpp.crafting;

import java.util.Map;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class SmallRecipe extends AbstractRecipe
{

	public SmallRecipe(Item result)
	{
		super(result);
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		return false;
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixWidth >= 2 && craftingMatrixHeight >= 2;
	}

}
