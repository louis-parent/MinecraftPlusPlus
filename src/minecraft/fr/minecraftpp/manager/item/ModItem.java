package fr.minecraftpp.manager.item;

import java.util.ArrayList;
import java.util.List;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.manager.block.ModBlock;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod("minecraftpp")
public class ModItem extends Item
{
	public static List<IDynamicItem> REGISTRY = new ArrayList<IDynamicItem>();

	public static ArrayList<ItemStack> enchantable;

	private static final int ID_START = 1256;

	public static void registerBlockItems()
	{
		for (IDynamicBlock block : ModBlock.REGISTRY)
		{
			registerItemBlock(block.getBlock());
		}
	}

	public static void registerItems()
	{
		for (int i = 0; i < REGISTRY.size(); i++)
		{
			IDynamicItem item = REGISTRY.get(i);
			registerItem(ID_START + i, item.getID(), item.getItem());
		}
	}

	public static void setItemToRegister(IDynamicItem item)
	{
		REGISTRY.add(item);
	}

	public static void addEnchantable()
	{
		enchantable = new ArrayList<ItemStack>();

		for (Item item : Item.REGISTRY)
		{
			if (item.allowEnchanting())
			{
				enchantable.add(new ItemStack(item));
			}
		}

		if (ModManager.IS_VANILLA_ENABLED)
		{
			enchantable.add(new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()));
		}
	}
}
