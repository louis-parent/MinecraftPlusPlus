package fr.minecraftpp.crafting.armor;

import fr.minecraftpp.crafting.RecipeEquipment;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RecipeBoots extends RecipeEquipment
{

	public RecipeBoots(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, Items.EMPTY_ITEM, this.material }, { this.material, Items.EMPTY_ITEM, this.material } });
	}

}