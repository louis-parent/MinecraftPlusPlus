package fr.minecraftpp.generator;

import java.util.Random;

import fr.minecraftpp.block.DynamicBlock;
import fr.minecraftpp.block.FlammabilityOf;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.util.NameGenerator;
import net.minecraft.block.Block;

public class ModGenerator
{
	public static void generateOre()
	{
		Random r = new Random();
		String name = NameGenerator.generateName();
		
		new DynamicItem(name, r.nextInt(6) + 1, false, 0, false, false);
		new DynamicBlock(name, r.nextInt(4) + 1, false, 0, false, 100, 0, FlammabilityOf.STONE, 0, Block.BLOCK_SLIPERNESS, 0.5F, 0.5F * 5F);
	}
}
