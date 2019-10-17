package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class RecipePickaxe extends ModRecipe
{
	public RecipePickaxe(Item material, Item result)
	{
		super(new Blueprint(new Item[][] {{material, material, material}, {Items.EMPTY_ITEM, Items.STICK, Items.EMPTY_ITEM}, {Items.EMPTY_ITEM, Items.STICK, Items.EMPTY_ITEM}}), result);
	}
}
