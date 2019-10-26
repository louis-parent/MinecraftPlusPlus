package fr.minecraftpp.manager.block;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.BlockScenarium;
import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.block.ore.BlockScenariteOre;
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
		
		BlockScenarium block_ = new BlockScenarium();
		registerBlock(1001, "scenarium_block", block_);
		
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
