package fr.minecraftpp.crafting.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.ShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeItemFromBlock extends ShapelessRecipe
{
	public RecipeItemFromBlock(Block material, Item result)
	{
		super(result, Item.getItemFromBlock(material));
	}

	@Override
	public int getRecipeOutputQuantity()
	{
		return 9;
	}
}
