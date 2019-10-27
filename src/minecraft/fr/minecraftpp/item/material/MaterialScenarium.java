package fr.minecraftpp.item.material;

import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.manager.item.ModItems;
import fr.minecraftpp.util.Color;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;

public class MaterialScenarium implements IToolMaterial, IArmorMaterial
{
	@Override
	public int getDamageReductionAmount(EntityArmorSlot armorType)
	{
		switch (armorType)
		{
			case HEAD:
			case FEET:
				return 3;

			case CHEST:
				return 6;

			case LEGS:
				return 8;

			default:
				return 0;

		}
	}

	@Override
	public SoundEvent getSoundEvent()
	{
		return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
	}

	@Override
	public String getName()
	{
		return "scenarium";
	}

	@Override
	public float getToughness()
	{
		return 3.0F;
	}

	@Override
	public Item getRepairItem()
	{
		return ModItems.SCENARIUM;
	}

	@Override
	public int getMaxUses()
	{
		return 2160;
	}

	@Override
	public float getEfficiencyOnProperMaterial()
	{
		return 12.0F;
	}

	@Override
	public float getDamageVsEntity(ToolType toolType)
	{
		switch (toolType)
		{
			case AXE:
				return 10.0F;
			case HOE:
				return 0.0F;
			default:
				return 5.0F;
		}
	}

	@Override
	public float getAttackSpeed(ToolType toolType)
	{
		return toolType == ToolType.AXE ? -2.8F : 0.0F;
	}

	@Override
	public int getHarvestLevel()
	{
		return 4;
	}

	@Override
	public int getEnchantability()
	{
		return 25;
	}

	@Override
	public int getArmorDurabilityFactor()
	{
		return 35;
	}
}
