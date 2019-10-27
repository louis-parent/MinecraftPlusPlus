package fr.minecraftpp.item.material;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.util.Color;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public interface IToolMaterial extends IColoredMaterial
{
	public abstract int getMaxUses();

	public abstract float getEfficiencyOnProperMaterial();

	public abstract float getDamageVsEntity(ToolType toolType);

	public abstract float getAttackSpeed(ToolType toolType);

	public abstract int getHarvestLevel();

	public abstract int getEnchantability();

	public abstract Item getRepairItem();
}
