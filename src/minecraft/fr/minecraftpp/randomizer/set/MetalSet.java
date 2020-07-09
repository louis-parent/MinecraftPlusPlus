package fr.minecraftpp.randomizer.set;

import java.util.Random;

import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.block.ore.DynamicOreGem;
import fr.minecraftpp.color.DynamicColor;
import fr.minecraftpp.crafting.item.RecipeCompact;
import fr.minecraftpp.crafting.item.RecipeDecompact;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.generation.OreRarity;
import fr.minecraftpp.item.DynamicNugget;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.variant.Variant;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.Rarity;

public class MetalSet extends MaterialSet
{
	protected DynamicNugget nugget;

	public MetalSet(Random rand, OreRarity oreRarity)
	{
		super(rand, oreRarity);

		this.item.setUnlocalizedName(this.name + "Ingot");

		this.nugget = new DynamicNugget(this.name, this.item);

		this.tools = new MetalToolSet(this.tools, this.nugget);
		this.armors = new MetalArmorSet(this.armors, this.nugget);
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
	}

	@Override
	public void setRarity(Rarity rarity)
	{
		super.setRarity(rarity);
		this.nugget.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		super.registerItemColors(itemColors);
		itemColors.registerItemColorHandler(new DynamicColor(this.nugget), this.nugget);
	}

	@Override
	protected void setupIron()
	{
		super.setupIron();
		Variant.getInstance().addVariant(Items.IRON_NUGGET, this.nugget);
	}

	@Override
	protected void generateOre(OreRarity oreRarity, HarvestLevel randomHarvestLevel)
	{
		this.ore = new DynamicOre(this.name, DynamicOre.getRandomTextureId(this.rng), oreRarity, HarvestLevel.values()[this.tier + 1], this.item);
	}

	@Override
	protected void setupGold()
	{
		super.setupGold();
		Variant.getInstance().addVariant(Items.GOLD_NUGGET, this.nugget);
	}

	/*
	 * Metal sets cannot be blue dyes Crafting blue dye and nuggets is
	 * incompatible
	 */
	@Override
	public void setBlueDye()
	{
	}

	/*
	 * Metal sets cannot be redstone Crafting redstone and nuggets is
	 * incompatible
	 */
	@Override
	public void setRedstone()
	{
	}
	
	@Override
	protected String getSetTypeInfo()
	{
		return "Metal";
	}
}
