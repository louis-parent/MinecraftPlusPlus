package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RecipeShovel extends RecipeTool 
{

	public RecipeShovel(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] {{this.material}, {Items.STICK}, {Items.STICK}});
	}
}
