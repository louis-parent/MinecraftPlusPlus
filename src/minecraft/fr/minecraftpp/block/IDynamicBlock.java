package fr.minecraftpp.block;

import fr.minecraftpp.enumeration.ModelType;
import fr.minecraftpp.generator.IDynamic;
import net.minecraft.block.Block;

public interface IDynamicBlock extends IDynamic
{
	public abstract Block getBlock();
	
	public default ModelType getModelType()
	{
		return ModelType.NORMAL;
	}
}
