package fr.minecraftpp.randomizer.set;

import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class MetalToolSet extends ToolSet
{
	private Item nugget;

	public MetalToolSet(ToolSet tools, Item nugget)
	{
		super(tools);
		this.nugget = nugget;
	}

	@Override
	public void addRecipes()
	{
		super.addRecipes();

		new FurnaceRecipe(this.sword, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.pickaxe, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.axe, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.shovel, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.hoe, this.nugget.getAsStack(), 0);
	}
}
