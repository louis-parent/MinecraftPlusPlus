package fr.minecraftpp.damageSource;

import fr.minecraftpp.language.ModLanguage;
import net.minecraft.util.DamageSource;

public class ModDamageSource extends DamageSource
{
	public static final DamageSource scenarium = new DamageSource("scenarium");

	public ModDamageSource(String damageType)
	{
		super(damageType);
		ModLanguage.addTranslation(this);
	}
}
