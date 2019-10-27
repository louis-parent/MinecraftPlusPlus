package fr.minecraftpp.manager.block;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.language.ModLanguage;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@Mod("minecraftpp")
public class ModBlocks extends Blocks
{
	public static Block SCENARITE_ORE;
	public static Block SCENARIUM_BLOCK;

	/**
	 * Sets the constants that are usually set in {@link Blocks}
	 */
	public static void staticSetter()
	{
		SCENARITE_ORE = getRegisteredBlock("scenarite_ore");
		SCENARIUM_BLOCK = getRegisteredBlock("scenarium_block");

		ModLanguage.addTranslation(SCENARITE_ORE);
		ModLanguage.addTranslation(SCENARIUM_BLOCK);
	}
}