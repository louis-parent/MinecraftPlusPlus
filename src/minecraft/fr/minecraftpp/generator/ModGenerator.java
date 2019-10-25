package fr.minecraftpp.generator;

import java.util.Random;

import fr.minecraftpp.block.DynamicBlock;
import fr.minecraftpp.block.FlammabilityOf;
import fr.minecraftpp.block.HarvestLevel;
import fr.minecraftpp.block.ore.DynamicMetal;
import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.util.NameGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ModGenerator
{
	public static void generateOre()
	{
		Random r = new Random();
		String name = NameGenerator.generateName();
		
		Item item = new DynamicItem(name, r.nextInt(6) + 1, false, 0, false, false);
		new DynamicBlock(name, r.nextInt(4) + 1, false, 0, false, 100, 0, FlammabilityOf.STONE, 0, Block.BLOCK_SLIPERNESS, 0.5F, 0.5F * 5F);
		new DynamicOre(name, r.nextInt(4) + 1, item, 0, 5, HarvestLevel.WOOD, 0, 15);
		new DynamicMetal(name+"M", r.nextInt(4) + 1, HarvestLevel.WOOD);
	}
}
