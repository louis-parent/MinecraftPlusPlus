package fr.minecraftpp.generator.set;

import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class MetalArmorSet extends ArmorSet
{
	private ArmorSet armors;
	private Item nugget;
	
	public MetalArmorSet(ArmorSet armors, Item nugget)
	{
		this.armors = armors;
		this.nugget = nugget;
	}
	
	@Override
	public void register()
	{
		this.armors.register();
	}
	
	@Override
	public void addRecipes()
	{
		this.armors.addRecipes();
		
		new FurnaceRecipe(this.armors.helmet, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.armors.chestplate, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.armors.leggings, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.armors.boots, this.nugget.getAsStack(), 0);
	}
	
	@Override
	public void setRarity(Rarity rarity)
	{
		this.armors.setRarity(rarity);
	}
	
	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		this.armors.registerItemColors(itemColors);
	}
	
	@Override
	public void setupEffects()
	{
		this.armors.setupEffects();
	}
	
	@Override
	public void registerBlockColors(BlockColors blockColors)
	{
		this.armors.registerBlockColors(blockColors);
	}
}
