package fr.minecraftpp.block;

import java.lang.reflect.Field;

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

		doNames();
	}

	private static void doNames()
	{
		for (Field field : ModBlocks.class.getDeclaredFields())
		{
			try
			{
				if (field.get(null) instanceof Block)
				{
					Block block = (Block) field.get(null);
					ModLanguage.addTranslation(block);

				}
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
