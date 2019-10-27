package fr.minecraftpp.generator.set;

import java.util.Random;

import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import fr.minecraftpp.crafting.item.RecipeCompact;
import fr.minecraftpp.crafting.item.RecipeDecompact;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.item.DynamicColor;
import fr.minecraftpp.item.DynamicNugget;
import fr.minecraftpp.manager.ModManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Rarity;

public class MetalSet extends MaterialSet
{
	protected DynamicNugget nugget;
	
	public MetalSet(Random rand)
	{
		super(rand);
		
		this.ore = new DynamicOre(this.name, DynamicOre.getRandomTextureId(this.rng), HarvestLevel.getRandomHarvestLevel(this.rng));
		this.nugget = new DynamicNugget(this.name, this.item);
	}
	
	@Override
	public void setupEffects()
	{
		super.setupEffects();
		
		this.item.setTextureAsMetal();
	}
	
	@Override
	public void register()
	{
		super.register();
		
		ModManager.registerDynamic(this.nugget);
	}

	@Override
	public void addRecipes()
	{
		super.addRecipes();
		
		new RecipeCompact(this.nugget, this.item);
		new RecipeDecompact(this.item, this.nugget);
		
		new FurnaceRecipe(this.sword, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.pickaxe, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.axe, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.shovel, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.hoe, this.nugget.getAsStack(), 0);
		
		new FurnaceRecipe(this.helmet, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.chestplate, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.leggings, this.nugget.getAsStack(), 0);
		new FurnaceRecipe(this.boots, this.nugget.getAsStack(), 0);
	}
	
	@Override
	public void setRarity(Rarity rarity)
	{
		super.setRarity(rarity);
		
		this.nugget.setRarity(rarity);
	}
	
	@Override
	public void registerColors(ItemColors itemColors)
	{
		super.registerColors(itemColors);
		
		itemColors.registerItemColorHandler(new DynamicColor(this.nugget), this.nugget);
	}
	
	/*
	 * Metal sets cannot be blue dyes
	 * Crafting blue dye and nuggets is incompatible
	 */
	@Override
	public void setBlueDye() {}
	
	/*
	 * Metal sets cannot be redstone
	 * Crafting redstone and nuggets is incompatible
	 */
	@Override
	public void setRedstoneSet() {}
}
