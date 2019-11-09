package fr.minecraftpp.randomizer.set;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Rarity;

public interface ISet
{
	public abstract void register();

	public abstract void setupEffects();

	public abstract void addRecipes();

	public abstract void setRarity(Rarity rarity);

	public abstract void registerItemColors(ItemColors itemColors);

	public abstract void registerBlockColors(BlockColors blockColors);
	
	public abstract String getSetName();
}
