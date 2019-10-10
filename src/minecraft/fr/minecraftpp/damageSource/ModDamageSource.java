package fr.minecraftpp.damageSource;

import net.minecraft.util.DamageSource;

public class ModDamageSource extends DamageSource
{
    public static final DamageSource scenarium = new ModDamageSource("scenarium");
	
	protected ModDamageSource(String damageType) { super(damageType); }
}
