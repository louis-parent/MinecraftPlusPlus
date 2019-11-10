package fr.minecraftpp.manager;

import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.language.ModLanguage;
import fr.minecraftpp.manager.block.ModBlock;
import fr.minecraftpp.manager.item.ModItem;

public class ModManager
{
	public static final boolean IS_VANILLA_ENABLED;
	
	static
	{
		IS_VANILLA_ENABLED = false;
	}
	
	public static void registerDynamic(IDynamicItem item)
	{
		ModItem.setItemToRegister(item);
		ModLanguage.addTranslation(item.getItem());
	}

	public static void registerDynamic(IDynamicBlock block)
	{
		ModBlock.setBlockToRegister(block);
		ModLanguage.addTranslation(block.getBlock());
	}
}
