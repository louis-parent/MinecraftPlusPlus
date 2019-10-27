package fr.minecraftpp.generator.set;

import net.minecraft.client.renderer.color.ItemColors;

public interface ISet
{
	public abstract void register();
	public abstract void setupEffects();
	public abstract void addRecipes();
	public abstract void registerColors(ItemColors itemColors);
}
