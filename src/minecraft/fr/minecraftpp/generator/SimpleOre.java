package fr.minecraftpp.generator;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;

public class SimpleOre
{
	protected Random rng;
	
	protected BlockOre ore;
	protected Block block;
	protected Item item;
	
	public SimpleOre()
	{
		rng = new Random();
	}
}
