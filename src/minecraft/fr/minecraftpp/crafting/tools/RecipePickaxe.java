package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.RecipeEquipment;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public class RecipePickaxe extends RecipeEquipment
{
	public RecipePickaxe(Item material, Item result)
	{
		super(material, result);
	}

	@Override
	public Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] { { this.material, this.material, this.material }, { Items.getItem(Items.AIR), Items.getItem(Items.STICK), Items.getItem(Items.AIR) }, { Items.getItem(Items.AIR), Items.getItem(Items.STICK), Items.getItem(Items.AIR) } });
	}
}
