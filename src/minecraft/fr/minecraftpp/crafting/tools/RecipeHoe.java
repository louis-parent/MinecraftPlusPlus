package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.RecipeEquipment;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeHoe extends RecipeEquipment
{
	public RecipeHoe(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material }, { Items.EMPTY_ITEM, Items.STICK }, { Items.EMPTY_ITEM, Items.STICK } });
	}
}
