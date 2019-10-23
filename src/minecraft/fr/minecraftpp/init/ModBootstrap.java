package fr.minecraftpp.init;

import fr.minecraftpp.generator.ModGenerator;
import fr.minecraftpp.item.ModItems;
import net.minecraft.entity.passive.EntityVillager;

public class ModBootstrap
{

	public static void preBootstrap()
	{
		ModGenerator.generateOre();
	}
	
	public static void postBootstrap()
	{
		EntityVillager.setMoney(ModItems.SCENARIUM);
	}

}
