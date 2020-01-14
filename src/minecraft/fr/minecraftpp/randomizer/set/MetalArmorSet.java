package fr.minecraftpp.randomizer.set;

import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class MetalArmorSet extends ArmorSet
{
	private Item nugget;

	public MetalArmorSet(ArmorSet armors, Item nugget)
	{
		super(armors);
		this.nugget = nugget;
	}
	
	@Override
	public void addRecipes()
	{
		super.addRecipes();

		new FurnaceRecipe(this.helmet, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.chestplate, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.leggings, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.boots, this.nugget.getAsStack(), 0);
	}
}
