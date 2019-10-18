package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.ShapedRecipe;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public abstract class RecipeTool extends ShapedRecipe
{
	protected Item material;
	
	public RecipeTool(Item material, Item result)
	{
		super(result);
		
		this.material = material;
		this.changeBlueprint(this.getBlueprint());
	}

	protected abstract Blueprint getBlueprint();
}
