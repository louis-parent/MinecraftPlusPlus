package fr.minecraftpp.init;

import fr.minecraftpp.manager.SetManager;
import fr.minecraftpp.manager.item.ModItem;

public class ModBootstrap
{

	public static void preBootstrap()
	{
		MppConfig config = MppConfig.init();		
		SetManager.generateOre(config.getSeed());
		SetManager.register();
	}

	public static void postBootstrap()
	{

		SetManager.setupEffects();
		SetManager.generateRecipes();

		ModItem.addEnchantable();
	}

}
