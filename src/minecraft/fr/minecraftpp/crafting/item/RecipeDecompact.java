package fr.minecraftpp.crafting.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.ShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeDecompact extends ShapelessRecipe
{
	public RecipeDecompact(Block material, Item result)
	{
		this(Item.getItemFromBlock(material), result);
	}
	
	public RecipeDecompact(Item material, Item result)
	{
		super(result, material);
	}

	@Override
	public int getRecipeOutputQuantity()
	{
		return 9;
	}
}
