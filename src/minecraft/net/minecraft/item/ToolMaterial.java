package net.minecraft.item;

import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.ToolType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public enum ToolMaterial implements IToolMaterial
{
	WOOD(0, 59, 2.0F, new float[] { 0.0F, 0.0F, 6.0F, 0.0F, 0.0F }, new float[] { 0.0F, 0.0F, -3.2F, 0.0F, 0.0F }, 15), STONE(1, 131, 4.0F, new float[] { 1.0F, 1.0F, 8.0F, 1.0F, 0.0F }, new float[] { 0.0F, 0.0F, -3.2F, 0.0F, 0.0F }, 5), IRON(2, 250, 6.0F, new float[] { 2.0F, 2.0F, 8.0F, 2.0F, 0.0F }, new float[] { 0.0F, 0.0F, -3.1F, 0.0F, 0.0F }, 14), DIAMOND(3, 1561, 8.0F, new float[] { 3.0F, 3.0F, 8.0F, 3.0F, 0.0F }, new float[] { 0.0F, 0.0F, -3.0F, 0.0F, 0.0F }, 10), GOLD(0, 32, 12.0F, new float[] { 0.0F, 0.0F, 6.0F, 0.0F, 0.0F }, new float[] { 0.0F, 0.0F, -3.0F, 0.0F, 0.0F }, 22);

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final float damageVsEntity[];
	private final int enchantability;
	private final float attackSpeed[];

	private ToolMaterial(int harvestLevel, int maxUses, float efficiency, float[] damageVsEntity, float[] attackSpeed, int enchantability)
	{
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiencyOnProperMaterial = efficiency;
		this.damageVsEntity = damageVsEntity;
		this.attackSpeed = attackSpeed;
		this.enchantability = enchantability;
	}

	public int getMaxUses()
	{
		return this.maxUses;
	}

	public float getEfficiencyOnProperMaterial()
	{
		return this.efficiencyOnProperMaterial;
	}

	public int getHarvestLevel()
	{
		return this.harvestLevel;
	}

	public int getEnchantability()
	{
		return this.enchantability;
	}

	@Override
	public float getDamageVsEntity(ToolType toolType)
	{
		return this.damageVsEntity[toolType.ordinal()];
	}

	@Override
	public float getAttackSpeed(ToolType toolType)
	{
		return this.attackSpeed[toolType.ordinal()];
	}

	@Override
	public Item getRepairItem()
	{
		if (this == WOOD)
		{
			return Item.getItemFromBlock(Blocks.PLANKS);
		}
		else if (this == STONE)
		{
			return Item.getItemFromBlock(Blocks.COBBLESTONE);
		}
		else if (this == GOLD)
		{
			return Items.GOLD_INGOT;
		}
		else if (this == IRON)
		{
			return Items.IRON_INGOT;
		}
		else
		{
			return this == DIAMOND ? Items.DIAMOND : null;
		}
	}
}