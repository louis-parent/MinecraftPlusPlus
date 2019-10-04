package fr.minecraftpp.block;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@Mod("minecraftpp")
public class ModBlocks extends Blocks
{

	public static Block SCENARITE_ORE;
	
	/**
	 * Sets the constants that are usually set in {@link Blocks} 
	 */
	public static void staticSetter()
	{
        SCENARITE_ORE = getRegisteredBlock("scenarite_ore");
	}
}
