package fr.minecraftpp.item;

import fr.minecraftpp.randomizer.IDynamic;
import net.minecraft.item.Item;

public interface IDynamicItem extends IDynamic
{
	public abstract Item getItem();
}
