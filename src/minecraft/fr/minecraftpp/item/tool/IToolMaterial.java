package fr.minecraftpp.item.tool;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.item.Item;

@Mod("Minecraftpp")
public interface IToolMaterial
{
	public abstract int getMaxUses();

    public abstract float getEfficiencyOnProperMaterial();

    public abstract float getDamageVsEntity(ToolType toolType);
    
    public abstract float getAttackSpeed(ToolType toolType);

    public abstract int getHarvestLevel();

    public abstract int getEnchantability();

    public abstract Item getRepairItem();
    
    @Mod("Minecraftpp")
    public static enum ToolType
    {
    	SWORD,
    	PICKAXE,
    	AXE,
    	SPADE,
    	HOE;
    }
}