package fr.minecraftpp.init;

import fr.minecraftpp.generator.ModGenerator;
import fr.minecraftpp.manager.block.ModBlocks;
import fr.minecraftpp.manager.item.ModItems;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ModBootstrap
{

	public static void preBootstrap()
	{
		ModGenerator.generateOre();
		ModGenerator.register();
	}
	
	public static void postBootstrap()
	{
		EntityVillager.setMoney(ModItems.SCENARIUM);
		Item.getItemFromBlock(ModBlocks.SCENARIUM_BLOCK).setRarity(Rarity.EPIC);

		ModGenerator.setupEffects();
		ModGenerator.generateRecipes();		
	}

}
