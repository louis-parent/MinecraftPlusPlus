package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class RecipesMapExtending extends ShapedRecipes
{
	public RecipesMapExtending()
	{
		super("", 3, 3, NonNullList.getInstanceWith(Ingredient.INGREDIENT_AIR, Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromFilledMap(Items.FILLED_MAP), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER), Ingredient.getIngredientFromItems(Items.PAPER)), new ItemStack(Items.MAP));
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		if (!super.matches(inv, worldIn))
		{
			return false;
		}
		else
		{
			ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;

			for (int i = 0; i < inv.getSizeInventory() && itemstack.isNotValid(); ++i)
			{
				ItemStack itemstack1 = inv.getStackInSlot(i);

				if (itemstack1.getItem() == Items.FILLED_MAP)
				{
					itemstack = itemstack1;
				}
			}

			if (itemstack.isNotValid())
			{
				return false;
			}
			else
			{
				MapData mapdata = Items.FILLED_MAP.getMapData(itemstack, worldIn);

				if (mapdata == null)
				{
					return false;
				}
				else if (this.func_190934_a(mapdata))
				{
					return false;
				}
				else
				{
					return mapdata.scale < 4;
				}
			}
		}
	}

	private boolean func_190934_a(MapData p_190934_1_)
	{
		if (p_190934_1_.mapDecorations != null)
		{
			for (MapDecoration mapdecoration : p_190934_1_.mapDecorations.values())
			{
				if (mapdecoration.func_191179_b() == MapDecoration.Type.MANSION || mapdecoration.func_191179_b() == MapDecoration.Type.MONUMENT)
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;

		for (int i = 0; i < inv.getSizeInventory() && itemstack.isNotValid(); ++i)
		{
			ItemStack itemstack1 = inv.getStackInSlot(i);

			if (itemstack1.getItem() == Items.FILLED_MAP)
			{
				itemstack = itemstack1;
			}
		}

		itemstack = itemstack.copy();
		itemstack.setStackSize(1);

		if (itemstack.getTagCompound() == null)
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}

		itemstack.getTagCompound().setInteger("map_scale_direction", 1);
		return itemstack;
	}

	@Override
	public boolean hideInCraftingTabs()
	{
		return true;
	}
}
