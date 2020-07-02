package fr.minecraftpp.manager.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.block.BlockOre;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;

@Mod("minecraftpp")
public class OreRegistry
{
	private static List<BlockOre> ORES = new ArrayList<BlockOre>();

	public static void register(BlockOre ore)
	{
		ORES.add(ore);
	}

	public static void unregister(BlockOre ore)
	{
		ORES.remove(ore);
	}

	public static void decorate(BiomeDecorator decorator, World world, Random rand)
	{
		List list = ORES;

		for (BlockOre blockOre : ORES)
		{
			blockOre.decorate(decorator, world, rand);
		}
	}
}
