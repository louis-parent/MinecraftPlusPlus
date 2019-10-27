package fr.minecraftpp.generator.set;

import java.util.Random;

import fr.minecraftpp.crafting.armor.RecipeBoots;
import fr.minecraftpp.crafting.armor.RecipeChestplate;
import fr.minecraftpp.crafting.armor.RecipeHelmet;
import fr.minecraftpp.crafting.armor.RecipeLeggings;
import fr.minecraftpp.crafting.tools.RecipeAxe;
import fr.minecraftpp.crafting.tools.RecipeHoe;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.crafting.tools.RecipeSword;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.item.DynamicColor;
import fr.minecraftpp.item.armor.DynamicBoots;
import fr.minecraftpp.item.armor.DynamicChestplate;
import fr.minecraftpp.item.armor.DynamicHelmet;
import fr.minecraftpp.item.armor.DynamicLeggings;
import fr.minecraftpp.item.material.DynamicMaterial;
import fr.minecraftpp.item.tool.DynamicAxe;
import fr.minecraftpp.item.tool.DynamicHoe;
import fr.minecraftpp.item.tool.DynamicPickaxe;
import fr.minecraftpp.item.tool.DynamicShovel;
import fr.minecraftpp.item.tool.DynamicSword;
import fr.minecraftpp.language.ModLanguage;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.manager.item.ModItem;
import fr.minecraftpp.util.Color;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Rarity;

public class MaterialSet extends SimpleSet 
{
	private static final float[] BASE_ARMOR_DAMAGE_REDUCTION = {1.0F, 2.3F, 2.8F, 1.1F};

	protected DynamicMaterial material;
	
	protected DynamicSword sword;
	protected DynamicPickaxe pickaxe;
	protected DynamicAxe axe;
	protected DynamicShovel shovel;
	protected DynamicHoe hoe;
	
	protected DynamicHelmet helmet;
	protected DynamicChestplate chestplate;
	protected DynamicLeggings leggings;
	protected DynamicBoots boots;
	
	public MaterialSet(Random rand)
	{
		super(rand);
		
		this.material = new DynamicMaterial(DynamicMaterial.getRandomTextureID(this.rng), this.item);
		
		this.sword = new DynamicSword(this.name, this.material);
		this.pickaxe = new DynamicPickaxe(this.name, this.material);
		this.axe = new DynamicAxe(this.name, this.material);
		this.shovel = new DynamicShovel(this.name, this.material);
		this.hoe = new DynamicHoe(this.name, this.material);

		this.helmet = new DynamicHelmet(this.name, this.material);
		this.chestplate = new DynamicChestplate(this.name, this.material);
		this.leggings = new DynamicLeggings(this.name, this.material);
		this.boots = new DynamicBoots(this.name, this.material);
	}
	
	@Override
	public void setupEffects()
	{
		super.setupEffects();
		this.setupMaterial();
	}
	
	@Override
	public void register()
	{
		super.register();
		
		ModManager.registerDynamic(this.sword);
		ModManager.registerDynamic(this.pickaxe);
		ModManager.registerDynamic(this.axe);
		ModManager.registerDynamic(this.shovel);
		ModManager.registerDynamic(this.hoe);
		
		ModManager.registerDynamic(this.helmet);
		ModManager.registerDynamic(this.chestplate);
		ModManager.registerDynamic(this.leggings);
		ModManager.registerDynamic(this.boots);
	}
	
	@Override
	public void addRecipes()
	{
		super.addRecipes();
		
		new RecipeSword(this.item, this.sword);
		new RecipePickaxe(this.item, this.pickaxe);
		new RecipeAxe(this.item, this.axe);
		new RecipeShovel(this.item, this.shovel);
		new RecipeHoe(this.item, this.hoe);

		new RecipeHelmet(this.item, this.helmet);
		new RecipeChestplate(this.item, this.chestplate);
		new RecipeLeggings(this.item, this.leggings);
		new RecipeBoots(this.item, this.boots);
	}
	
	@Override
	public void setRarity(Rarity rarity)
	{
		super.setRarity(rarity);
		
		this.sword.setRarity(rarity);
		this.pickaxe.setRarity(rarity);
		this.axe.setRarity(rarity);
		this.shovel.setRarity(rarity);
		this.hoe.setRarity(rarity);

		this.helmet.setRarity(rarity);
		this.chestplate.setRarity(rarity);
		this.leggings.setRarity(rarity);
		this.boots.setRarity(rarity);
	}
	
	@Override
	public void registerColors(ItemColors itemColors)
	{
		super.registerColors(itemColors);
		
		itemColors.registerItemColorHandler(new DynamicColor(this.sword), this.sword);
		itemColors.registerItemColorHandler(new DynamicColor(this.pickaxe), this.pickaxe);
		itemColors.registerItemColorHandler(new DynamicColor(this.axe), this.axe);
		itemColors.registerItemColorHandler(new DynamicColor(this.shovel), this.shovel);
		itemColors.registerItemColorHandler(new DynamicColor(this.hoe), this.hoe);

		itemColors.registerItemColorHandler(new DynamicColor(this.helmet), this.helmet);
		itemColors.registerItemColorHandler(new DynamicColor(this.chestplate), this.chestplate);
		itemColors.registerItemColorHandler(new DynamicColor(this.leggings), this.leggings);
		itemColors.registerItemColorHandler(new DynamicColor(this.boots), this.boots);
	}
	
	/*
	 * Material sets cannot be currencies
	 * Being able to craft armor and tools with the currency is not desired
	 */
	@Override
	public void setCurrency() {}
	
	private void setupMaterial()
	{
		this.material.setArmorDurabilityFactor(this.rng.nextInt(35) + 5);
		this.material.setArmorDamageReduction(this.getRandomArmorDamageReduction());
		this.material.setToughness(this.rng.nextFloat() * 2);
		this.material.setToolMaxUse(this.getRandomToolDurability());
		this.material.setEfficiencyOnProperMaterial((this.rng.nextFloat() * 10) + 4);
		this.material.setToolAttackDamage(this.getRandomToolAttackDamage());
		this.material.setToolAttackSpeed(this.getRandomToolAttackSpeed());
		this.material.setHarvestLevel(HarvestLevel.getRandomHarvestLevel(this.rng));
		this.material.setEnchantability(this.rng.nextInt() + 8);
	}

	private int[] getRandomArmorDamageReduction()
	{
		float factor = (this.rng.nextInt(25) / 10.0F) + 1;
		
		int[] result = new int[BASE_ARMOR_DAMAGE_REDUCTION.length];
		
		for(int i = 0; i < result.length; i++)
		{
			result[i] = Math.round(BASE_ARMOR_DAMAGE_REDUCTION[i] * factor);
		}
		
		return result;
	}
	
	private int getRandomToolDurability()
	{
		double power = (this.rng.nextDouble() * 7) + 4;
		return (int) Math.round(Math.pow(2, power));
	}
	
	private float[] getRandomToolAttackDamage()
	{
		float gradient = this.rng.nextInt(3) + 1;
		return new float[] {gradient, gradient, 8.0F, gradient, 0.0F};
	}
	
	private float[] getRandomToolAttackSpeed()
	{
		float axeSpeed = (this.rng.nextInt(4) / 10) - 3.2F;
		return new float[] { 0.0F, 0.0F, axeSpeed, 0.0F, 0.0F };
	}
}
