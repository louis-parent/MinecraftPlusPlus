package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RecipeSword extends RecipeTool
{

	public RecipeSword(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] {{this.material}, {this.material}, {Items.STICK}});
	}

}
