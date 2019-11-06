package fr.minecraftpp.init;

import fr.minecraftpp.manager.SetManager;
import fr.minecraftpp.manager.block.ModBlocks;
import fr.minecraftpp.manager.item.ModItems;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ModBootstrap
{

	public static void preBootstrap()
	{
		SetManager.generateOre();
		SetManager.register();
	}

	public static void postBootstrap()
	{
		EntityVillager.setMoney(ModItems.SCENARIUM);
		Item.getItemFromBlock(ModBlocks.SCENARIUM_BLOCK).setRarity(Rarity.EPIC);

		SetManager.setupEffects();
		SetManager.generateRecipes();

		ModItems.addEnchantable();
	}

}
