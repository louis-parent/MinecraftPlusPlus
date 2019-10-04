package fr.minecraftpp.block;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

@Mod("minecraftpp")
public class ModBlock extends Block
{
	public ModBlock(Material blockMaterialIn, MapColor blockMapColorIn) { super(blockMaterialIn, blockMapColorIn); }
	
	/**
	 * Registers the {@link Block}s that are usually registered in {@link Block} 
	 */
	public static void registerBlocks()
	
	{
		registerBlock(1000, "scenarite_ore", new BlockScenariteOre());
	}
}
