package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.RecipeEquipment;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipeAxe extends RecipeEquipment
{
	public RecipeAxe(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material, Items.getItem(Items.AIR) }, { this.material, Items.getItem(Items.STICK), Items.getItem(Items.AIR) }, { Items.getItem(Items.AIR), Items.getItem(Items.STICK), Items.getItem(Items.AIR) } });
	}
}
