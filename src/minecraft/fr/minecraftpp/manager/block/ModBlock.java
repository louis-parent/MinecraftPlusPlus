package fr.minecraftpp.manager.block;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.IDynamicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

@Mod("minecraftpp")
public class ModBlock extends Block
{
	public static List<IDynamicBlock> REGISTRY = new ArrayList<IDynamicBlock>();
	private static final int ID_START = 1000;

	public ModBlock(Material blockMaterial, MapColor blockMapColor)
	{
		super(blockMaterial, blockMapColor);
	}

	/**
	 * Registers the {@link Block}s that are usually registered in {@link Block}
	 */
	public static void registerBlocks()
	{
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

	public static void resetRegistry()
	{
		REGISTRY.clear();
	}
}
