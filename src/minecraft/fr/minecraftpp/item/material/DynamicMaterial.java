package fr.minecraftpp.item.material;

import java.util.Random;

import fr.minecraftpp.color.Color;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.item.DynamicItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

public class DynamicMaterial implements IToolMaterial, IArmorMaterial
{
	private static final int NUMBER_OF_TEXTURES = 2;
	
	private int textureId;
	private DynamicItem repairItem;
	
	private int armorDurabilityFactor;
	private int[] armorDamageReduction;
	private float toughness;
	private int toolMaxUse;
	private float efficiencyOnProperMaterial;
	private float[] toolAttackDamage;
	private float[] toolAttackSpeed;
	private int harvestLevel;
	private int enchantability;

	public DynamicMaterial(int textureId, DynamicItem repairItem)
	{
		this.textureId = textureId;
		this.repairItem = repairItem;
		
		this.armorDurabilityFactor = 0;
		this.armorDamageReduction = new int[]{0, 0, 0, 0};
		this.toughness = 0;
		this.toolMaxUse = 0;
		this.efficiencyOnProperMaterial = 0;
		this.toolAttackDamage = new float[] {0, 0, 0, 0, 0};
		this.toolAttackSpeed = new float[] {0, 0, 0, 0, 0};
		this.harvestLevel = 0;
		this.enchantability = 0;
	}

	@Override
	public int getArmorDurabilityFactor()
	{
		return this.armorDurabilityFactor;
	}

	@Override
	public int getDamageReductionAmount(EntityArmorSlot armorType)
	{
		return this.armorDamageReduction[armorType.ordinal()];
	}

	@Override
	public SoundEvent getSoundEvent()
	{
		return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
	}

	@Override
	public String getName()
	{
		return "generic_" + this.textureId;
	}

	@Override
	public float getToughness()
	{
		return this.toughness;
	}

	@Override
	public int getMaxUses()
	{
		return this.toolMaxUse;
	}

	@Override
	public float getEfficiencyOnProperMaterial()
	{
		return this.efficiencyOnProperMaterial;
	}

	@Override
	public float getDamageVsEntity(ToolType toolType)
	{
		return this.toolAttackDamage[toolType.ordinal()];
	}

	@Override
	public float getAttackSpeed(ToolType toolType)
	{
		return this.toolAttackSpeed[toolType.ordinal()];
	}

	@Override
	public int getHarvestLevel()
	{
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability()
	{
		return this.enchantability;
	}

	@Override
	public Item getRepairItem()
	{
		return this.repairItem;
	}

	public void setArmorDurabilityFactor(int armorDurabilityFactor)
	{
		this.armorDurabilityFactor = armorDurabilityFactor;
	}

	public void setArmorDamageReduction(int[] armorDamageReduction)
	{
		this.armorDamageReduction = armorDamageReduction;
	}

	public void setToughness(float toughness)
	{
		this.toughness = toughness;
	}

	public void setToolMaxUse(int toolMaxUse)
	{
		this.toolMaxUse = toolMaxUse;
	}

	public void setEfficiencyOnProperMaterial(float efficiencyOnProperMaterial)
	{
		this.efficiencyOnProperMaterial = efficiencyOnProperMaterial;
	}

	public void setToolAttackDamage(float[] toolAttackDamage)
	{
		this.toolAttackDamage = toolAttackDamage;
	}

	public void setToolAttackSpeed(float[] toolAttackSpeed)
	{
		this.toolAttackSpeed = toolAttackSpeed;
	}

	public void setHarvestLevel(int harvestLevel)
	{
		this.harvestLevel = harvestLevel;
	}
	
	public void setHarvestLevel(HarvestLevel harvestLevel)
	{
		this.harvestLevel = harvestLevel.getHarvestLevel();
	}

	public void setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
	}
	
	public static int getRandomTextureID(Random rng)
	{
		return rng.nextInt(NUMBER_OF_TEXTURES) + 1;
	}

	@Override
	public boolean hasColor()
	{
		return this.repairItem.hasColor();
	}

	@Override
	public Color getColor()
	{
		return this.repairItem.getColor();
	}
}
