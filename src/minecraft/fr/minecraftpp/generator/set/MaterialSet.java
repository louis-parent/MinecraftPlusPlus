package fr.minecraftpp.generator.set;

import java.util.Random;

import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.item.material.DynamicMaterial;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Rarity;

public class MaterialSet extends SimpleSet
{
	private static final float[] BASE_ARMOR_DAMAGE_REDUCTION = { 1.0F, 2.3F, 2.8F, 1.1F };

	protected DynamicMaterial material;

	protected ToolSet tools;
	protected ArmorSet armors;

	public MaterialSet(Random rand)
	{
		super(rand);

		this.material = new DynamicMaterial(DynamicMaterial.getRandomTextureID(this.rng), this.item);
		this.tools = new ToolSet(this.rng, this.name, this.material);
		this.armors = new ArmorSet(this.rng, this.name, this.material);
	}

	@Override
	public void setupEffects()
	{
		super.setupEffects();
		this.setupMaterial();

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

	/*
	 * Material sets cannot be currencies Being able to craft armor and tools
	 * with the currency is not desired
	 */
	@Override
	public void setCurrency()
	{
	}

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
		this.material.setEnchantability(this.rng.nextInt(18) + 8);
	}

	private int[] getRandomArmorDamageReduction()
	{
		float factor = (this.rng.nextInt(25) / 10.0F) + 1;

		int[] result = new int[BASE_ARMOR_DAMAGE_REDUCTION.length];

		for (int i = 0; i < result.length; i++)
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
		return new float[] { gradient, gradient, 8.0F, gradient, 0.0F };
	}

	private float[] getRandomToolAttackSpeed()
	{
		float axeSpeed = (this.rng.nextInt(4) / 10) - 3.2F;
		return new float[] { 0.0F, 0.0F, axeSpeed, 0.0F, 0.0F };
	}
}
