package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
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
