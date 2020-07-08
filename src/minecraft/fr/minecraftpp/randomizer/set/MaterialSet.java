package fr.minecraftpp.randomizer.set;

import java.util.Random;

import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.block.ore.DynamicOreGem;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.generation.OreRarity;
import fr.minecraftpp.item.material.DynamicMaterial;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.Rarity;

public class MaterialSet extends SimpleSet
{
	private static final float[] BASE_ARMOR_DAMAGE_REDUCTION = { 1.0F, 2.3F, 2.8F, 1.1F };

	protected DynamicMaterial material;

	protected ToolSet tools;
	protected ArmorSet armors;

	protected int tier;

	public MaterialSet(Random rand, OreRarity oreRarity)
	{
		super(rand, oreRarity);

		this.material = new DynamicMaterial(DynamicMaterial.getRandomTextureID(this.rng), this.item);
		this.setupTier();
		this.setupMaterial();

		this.tools = new ToolSet(this.rng, this.name, this.material);
		this.armors = new ArmorSet(this.rng, this.name, this.material);
	}

	@Override
	public void setupEffects()
	{
		super.setupEffects();

		this.tools.setupEffects();
		this.armors.setupEffects();
	}

	@Override
	public void register()
	{
		super.register();
		this.tools.register();
		this.armors.register();
	}

	@Override
	public void addRecipes()
	{
		super.addRecipes();
		this.tools.addRecipes();
		this.armors.addRecipes();
	}

	@Override
	public void setRarity(Rarity rarity)
	{
		super.setRarity(rarity);
		this.tools.setRarity(rarity);
		this.armors.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		super.registerItemColors(itemColors);
		this.tools.registerItemColors(itemColors);
		this.armors.registerItemColors(itemColors);
	}

	@Override
	protected void setupIron()
	{
		super.setupIron();
		this.material.setHarvestLevel(HarvestLevel.IRON);
		this.tools.setVariant(Items.IRON_SWORD, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL, Items.IRON_HOE);
		this.armors.setVariant(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
	}

	@Override
	protected void setupGold()
	{
		super.setupGold();
		this.material.setHarvestLevel(HarvestLevel.IRON);
		this.tools.setVariant(Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_HOE);
		this.armors.setVariant(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
	}

	@Override
	protected void setupDiamond()
	{
		super.setupDiamond();
		this.material.setHarvestLevel(HarvestLevel.DIAMOND);
		this.tools.setVariant(Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_HOE);
		this.armors.setVariant(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
	}

	/*
	 * Material sets cannot be currencies Being able to craft armor and tools
	 * with the currency is not desired
	 */
	@Override
	public void setCurrency()
	{
	}

	private void setupTier()
	{
		if (this.isGold || this.isIron)
		{
			this.tier = 1;
		}
		else if (this.isDiamond)
		{
			this.tier = 2;
		}
		else
		{
			this.tier = this.rng.nextInt(3);
		}
	}

	@Override
	protected void generateOre(OreRarity oreRarity, HarvestLevel randomHarvestLevel)
	{
		this.ore = new DynamicOreGem(this.name, DynamicOre.getRandomTextureId(this.rng), oreRarity, this.item, HarvestLevel.values()[this.tier + 1], this.rng.nextInt(2), this.rng.nextInt(3) + 2);
	}

	private void setupMaterial()
	{
		this.material.setArmorDurabilityFactor(this.getGeneratedArmorDurabilityFactor());
		this.material.setArmorDamageReduction(this.getGeneratedArmorDamageReduction());
		this.material.setToughness(this.getGeneratedToughness());
		this.material.setToolMaxUse(this.getGeneratedToolDurability());
		this.material.setEfficiencyOnProperMaterial(this.getGeneratedEfficiency());
		this.material.setToolAttackDamage(this.getGeneratedToolAttackDamage());
		this.material.setToolAttackSpeed(this.getGeneratedToolAttackSpeed());
		this.material.setHarvestLevel(this.getGeneratedHarvestLevel());
		this.material.setEnchantability(this.getGeneratedEnchantability());
	}

	private int getGeneratedArmorDurabilityFactor()
	{
		return (this.tier + 1) * (this.rng.nextInt(9) + 5);
	}

	private int[] getGeneratedArmorDamageReduction()
	{
		float factor = (((8 * this.tier) + this.rng.nextInt(10)) / 10.0F) + 1;

		int[] result = new int[BASE_ARMOR_DAMAGE_REDUCTION.length];

		for (int i = 0; i < result.length; i++)
		{
			result[i] = Math.round(BASE_ARMOR_DAMAGE_REDUCTION[i] * factor);
		}

		return result;
	}

	private float getGeneratedToughness()
	{
		return this.rng.nextFloat() * this.tier;
	}

	private int getGeneratedToolDurability()
	{
		double power = (this.rng.nextDouble() * this.rng.nextInt((this.tier * 3) + 2)) + 4;
		return (int) Math.round(Math.pow(2, power));
	}

	private float getGeneratedEfficiency()
	{
		return (this.rng.nextFloat() * this.rng.nextInt((this.tier * 5) + 2)) + 4;
	}

	private float[] getGeneratedToolAttackDamage()
	{
		float gradient = this.rng.nextInt(3) + this.tier;
		return new float[] { gradient, gradient, 6.0F + this.tier, gradient, 0.0F };
	}

	private float[] getGeneratedToolAttackSpeed()
	{
		float axeSpeed = (this.rng.nextInt(this.tier + 3) / 10) - 3.2F;
		return new float[] { 0.0F, 0.0F, axeSpeed, 0.0F, 0.0F };
	}

	private HarvestLevel getGeneratedHarvestLevel()
	{
		return HarvestLevel.values()[this.tier + 1];
	}

	private int getGeneratedEnchantability()
	{
		return this.rng.nextInt(18) + 8;
	}
}
