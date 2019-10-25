package fr.minecraftpp.block;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.generator.IDynamicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

@Mod("minecraftpp")
public class ModBlock extends Block
{
	public static List<IDynamicBlock> REGISTRY = new ArrayList<IDynamicBlock>();
	private static final int ID_START = 1002;
	
	public ModBlock(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}

	/**
	 * Registers the {@link Block}s that are usually registered in {@link Block}
	 */
	public static void registerBlocks()
	{
		registerBlock(1000, "scenarite_ore", new BlockScenariteOre());
		registerBlock(1001, "scenarium_block", new BlockScenarium());
		
		for (int i = 0; i < REGISTRY.size(); i++)
		{
			IDynamicBlock block = REGISTRY.get(i);
			registerBlock(ID_START + i, block.getID(), block.getBlock());
		}
	}
	
	public static void setBlockToRegister(IDynamicBlock block)
	{
		REGISTRY.add(block);
	}
}
