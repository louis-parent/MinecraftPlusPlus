package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public abstract class RecipeEquipment extends ShapedRecipe
{
	protected Item material;
	
	public RecipeEquipment(Item material, Item result)
	{
		super(result);
		
		this.material = material;
		this.changeBlueprint(this.getBlueprint());
	}

	protected abstract Blueprint getBlueprint();
}
