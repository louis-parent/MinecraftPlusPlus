package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeBlock extends ShapedRecipe
{

	private Item material;

	public RecipeBlock(Item material, Block result)
	{
		super(Item.getItemFromBlock(result));

		this.material = material;
		this.changeBlueprint(this.getBlueprint());

		new RecipeItemFromBlock(result, material);
	}

	private Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material, this.material }, { this.material, this.material, this.material }, { this.material, this.material, this.material } });
	}
}
