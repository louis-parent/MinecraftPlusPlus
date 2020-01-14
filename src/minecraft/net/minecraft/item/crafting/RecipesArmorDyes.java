package net.minecraft.item.crafting;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipesArmorDyes implements IRecipe
{
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;
		List<ItemStack> list = Lists.<ItemStack>newArrayList();

		for (int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemstack1 = inv.getStackInSlot(i);

			if (!itemstack1.isNotValid())
			{
				if (itemstack1.getItem() instanceof ItemArmor)
				{
					ItemArmor itemarmor = (ItemArmor) itemstack1.getItem();

					if (itemarmor.getArmorMaterial() != ArmorMaterial.LEATHER || !itemstack.isNotValid())
					{
						return false;
					}

					itemstack = itemstack1;
				}
				else
				{
					if (itemstack1.getItem() != Items.getItem(Items.DYE))
					{
						return false;
					}

					list.add(itemstack1);
				}
			}
		}

		return !itemstack.isNotValid() && !list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack itemstack = ItemStack.EMPTY_ITEM_STACK;
		int[] aint = new int[3];
		int i = 0;
		int j = 0;
		ItemArmor itemarmor = null;

		for (int k = 0; k < inv.getSizeInventory(); ++k)
		{
			ItemStack itemstack1 = inv.getStackInSlot(k);

			if (!itemstack1.isNotValid())
			{
				if (itemstack1.getItem() instanceof ItemArmor)
				{
					itemarmor = (ItemArmor) itemstack1.getItem();

					if (itemarmor.getArmorMaterial() != ArmorMaterial.LEATHER || !itemstack.isNotValid())
					{
						return ItemStack.EMPTY_ITEM_STACK;
					}

					itemstack = itemstack1.copy();
					itemstack.setStackSize(1);

					if (itemarmor.hasColor(itemstack1))
					{
						int l = itemarmor.getColor(itemstack);
						float f = (l >> 16 & 255) / 255.0F;
						float f1 = (l >> 8 & 255) / 255.0F;
						float f2 = (l & 255) / 255.0F;
						i = (int) (i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						aint[0] = (int) (aint[0] + f * 255.0F);
						aint[1] = (int) (aint[1] + f1 * 255.0F);
						aint[2] = (int) (aint[2] + f2 * 255.0F);
						++j;
					}
				}
				else
				{
					if (itemstack1.getItem() != Items.getItem(Items.DYE))
					{
						return ItemStack.EMPTY_ITEM_STACK;
					}

					float[] afloat = EnumDyeColor.byDyeDamage(itemstack1.getMetadata()).getRGB();
					int l1 = (int) (afloat[0] * 255.0F);
					int i2 = (int) (afloat[1] * 255.0F);
					int j2 = (int) (afloat[2] * 255.0F);
					i += Math.max(l1, Math.max(i2, j2));
					aint[0] += l1;
					aint[1] += i2;
					aint[2] += j2;
					++j;
				}
			}
		}

		if (itemarmor == null)
		{
			return ItemStack.EMPTY_ITEM_STACK;
		}
		else
		{
			int i1 = aint[0] / j;
			int j1 = aint[1] / j;
			int k1 = aint[2] / j;
			float f3 = (float) i / (float) j;
			float f4 = Math.max(i1, Math.max(j1, k1));
			i1 = (int) (i1 * f3 / f4);
			j1 = (int) (j1 * f3 / f4);
			k1 = (int) (k1 * f3 / f4);
			int k2 = (i1 << 8) + j1;
			k2 = (k2 << 8) + k1;
			itemarmor.setColor(itemstack, k2);
			return itemstack;
		}
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return ItemStack.EMPTY_ITEM_STACK;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>getInstanceFilledWith(inv.getSizeInventory(), ItemStack.EMPTY_ITEM_STACK);

		for (int i = 0; i < nonnulllist.size(); ++i)
		{
			ItemStack itemstack = inv.getStackInSlot(i);

			if (itemstack.getItem().hasContainerItem())
			{
				nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
			}
		}

		return nonnulllist;
	}

	@Override
	public boolean hideInCraftingTabs()
	{
		return true;
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int p_194133_1_, int p_194133_2_)
	{
		return p_194133_1_ * p_194133_2_ >= 2;
	}
}
