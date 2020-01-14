package fr.minecraftpp.crafting.armor;

import fr.minecraftpp.crafting.RecipeEquipment;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RecipeLeggings extends RecipeEquipment
{

	public RecipeLeggings(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material, this.material }, { this.material, Items.getItem(Items.AIR), this.material }, { this.material, Items.getItem(Items.AIR), this.material } });
	}

}