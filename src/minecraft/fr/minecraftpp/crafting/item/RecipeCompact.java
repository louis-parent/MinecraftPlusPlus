package fr.minecraftpp.crafting.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.ShapedRecipe;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeCompact extends ShapedRecipe
{

	private Item material;
	
	public RecipeCompact(Item material, Block result)
	{
		this(material, Item.getItemFromBlock(result));
	}

	public RecipeCompact(Item material, Item result)
	{
		super(result);

		this.material = material;
		this.changeBlueprint(this.getBlueprint());
	}

	private Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material, this.material }, { this.material, this.material, this.material }, { this.material, this.material, this.material } });
	}
}
