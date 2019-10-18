package fr.minecraftpp.init;

import fr.minecraftpp.item.ModItems;
import net.minecraft.entity.passive.EntityVillager;

public class ModBootstrap
{

	public static void launchMeBaby()
	{
		EntityVillager.setMoney(ModItems.SCENARIUM);
	}

}
