package fr.minecraftpp.randomizer.set;

import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class MetalToolSet extends ToolSet
{
	private ToolSet tools;
	private Item nugget;

	public MetalToolSet(ToolSet tools, Item nugget)
	{
		this.tools = tools;
		this.nugget = nugget;
	}

	@Override
	public void register()
	{
		this.tools.register();
	}

	@Override
	public void addRecipes()
	{
		this.tools.addRecipes();

		new FurnaceRecipe(this.tools.sword, this.nugget.getAsStack(), 0.1F);
		new FurnaceRecipe(this.tools.pickaxe, this.nugget.getAsStack(), 0.1F);
		new FurnaceRecipe(this.tools.axe, this.nugget.getAsStack(), 0.1F);
		new FurnaceRecipe(this.tools.shovel, this.nugget.getAsStack(), 0.1F);
		new FurnaceRecipe(this.tools.hoe, this.nugget.getAsStack(), 0.1F);
	}

	@Override
	public void setRarity(Rarity rarity)
	{
		this.tools.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		this.tools.registerItemColors(itemColors);
	}

	@Override
	public void setupEffects()
	{
		this.tools.setupEffects();
	}

	@Override
	public void registerBlockColors(BlockColors blockColors)
	{
		this.tools.registerBlockColors(blockColors);
	}
}
