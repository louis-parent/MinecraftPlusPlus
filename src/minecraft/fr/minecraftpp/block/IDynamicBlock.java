package fr.minecraftpp.block;

import fr.minecraftpp.generator.IDynamic;
import net.minecraft.block.Block;

public interface IDynamicBlock extends IDynamic
{
	public abstract Block getBlock();

}
