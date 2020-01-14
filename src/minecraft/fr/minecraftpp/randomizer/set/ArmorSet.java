package fr.minecraftpp.randomizer.set;

import java.util.Random;

import fr.minecraftpp.color.DynamicColor;
import fr.minecraftpp.crafting.armor.RecipeBoots;
import fr.minecraftpp.crafting.armor.RecipeChestplate;
import fr.minecraftpp.crafting.armor.RecipeHelmet;
import fr.minecraftpp.crafting.armor.RecipeLeggings;
import fr.minecraftpp.item.armor.DynamicBoots;
import fr.minecraftpp.item.armor.DynamicChestplate;
import fr.minecraftpp.item.armor.DynamicHelmet;
import fr.minecraftpp.item.armor.DynamicLeggings;
import fr.minecraftpp.item.material.IArmorMaterial;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.variant.Variant;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Rarity;

public class ArmorSet implements ISet
{
	private Random rng;
	private String name;
	private IArmorMaterial material;

	protected DynamicHelmet helmet;
	protected DynamicChestplate chestplate;
	protected DynamicLeggings leggings;
	protected DynamicBoots boots;
	
	public ArmorSet(ArmorSet set)
	{
		this.rng = set.rng;
		this.name = set.name;
		this.material = set.material;
		
		this.helmet = set.helmet;
		this.chestplate = set.chestplate;
		this.leggings = set.leggings;
		this.boots = set.boots;
	}

	public ArmorSet(Random rng, String name, IArmorMaterial material)
	{
		this.rng = rng;
		this.name = name;
		this.material = material;

		this.helmet = new DynamicHelmet(this.name, this.material);
		this.chestplate = new DynamicChestplate(this.name, this.material);
		this.leggings = new DynamicLeggings(this.name, this.material);
		this.boots = new DynamicBoots(this.name, this.material);
	}

	@Override
	public void register()
	{
		ModManager.registerDynamic(this.helmet);
		ModManager.registerDynamic(this.chestplate);
		ModManager.registerDynamic(this.leggings);
		ModManager.registerDynamic(this.boots);
	}

	@Override
	public void addRecipes()
	{
		new RecipeHelmet(this.material.getRepairItem(), this.helmet);
		new RecipeChestplate(this.material.getRepairItem(), this.chestplate);
		new RecipeLeggings(this.material.getRepairItem(), this.leggings);
		new RecipeBoots(this.material.getRepairItem(), this.boots);
	}

	@Override
	public void setRarity(Rarity rarity)
	{
		this.helmet.setRarity(rarity);
		this.chestplate.setRarity(rarity);
		this.leggings.setRarity(rarity);
		this.boots.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		itemColors.registerItemColorHandler(new DynamicColor(this.helmet), this.helmet);
		itemColors.registerItemColorHandler(new DynamicColor(this.chestplate), this.chestplate);
		itemColors.registerItemColorHandler(new DynamicColor(this.leggings), this.leggings);
		itemColors.registerItemColorHandler(new DynamicColor(this.boots), this.boots);
	}

	@Override
	public String getSetName()
	{
		return this.name;
	}

	@Override
	public void setupEffects()
	{
	}

	@Override
	public void registerBlockColors(BlockColors blockColors)
	{
	}

	public void setVariant(Item helmetVariant, Item chestplateVariant, Item leggingsVariant, Item bootsVariant)
	{
		Variant variant = Variant.getInstance();
		
		variant.addVariant(helmetVariant, this.helmet);
		variant.addVariant(chestplateVariant, this.chestplate);
		variant.addVariant(leggingsVariant, this.leggings);
		variant.addVariant(bootsVariant, this.boots);
	}
}
