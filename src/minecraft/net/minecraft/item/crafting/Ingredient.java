package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import fr.minecraftpp.variant.Variant;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Ingredient implements Predicate<ItemStack>
{
	public static final Ingredient INGREDIENT_AIR = new Ingredient(new ItemStack[0])
	{
		@Override
		public boolean apply(@Nullable ItemStack stack)
		{
			return stack.isNotValid();
		}
	};
	
	private final List<ItemStack> stacks;
	private IntList field_194140_c;

	private Ingredient(ItemStack... stacks)
	{
		this.stacks = new ArrayList<ItemStack>();
		
		for(ItemStack stack : stacks)
		{
			this.stacks.addAll(Variant.getInstance().getAllStackVariantsOf(stack.getItem()));
		}
	}

	public ItemStack[] getStacks()
	{
		return this.stacks.toArray(new ItemStack[this.stacks.size()]);
	}

	@Override
	public boolean apply(@Nullable ItemStack stack)
	{
		if (stack == null)
		{
			return false;
		}
		else
		{
			for (ItemStack itemstack : this.stacks)
			{
				if (stack.getItem() == itemstack.getItem())
				{
					int i = itemstack.getMetadata();

					if (i == 32767 || i == stack.getMetadata())
					{
						return true;
					}
				}
			}

			return false;
		}
	}

	public IntList func_194139_b()
	{
		if (this.field_194140_c == null)
		{
			this.field_194140_c = new IntArrayList(this.stacks.size());

			for (ItemStack itemstack : this.stacks)
			{
				this.field_194140_c.add(RecipeItemHelper.func_194113_b(itemstack));
			}

			this.field_194140_c.sort(IntComparators.NATURAL_COMPARATOR);
		}

		return this.field_194140_c;
	}

	public static Ingredient getIngredientFromFilledMap(Item item)
	{
		return getIngredientFromItemStack(new ItemStack(item, 1, 32767));
	}

	public static Ingredient getIngredientFromItems(Item... items)
	{
		ItemStack[] aitemstack = new ItemStack[items.length];

		for (int i = 0; i < items.length; ++i)
		{
			aitemstack[i] = new ItemStack(items[i]);
		}

		return getIngredientFromItemStack(aitemstack);
	}

	public static Ingredient getIngredientFromItemStack(ItemStack... stacks)
	{
		if (stacks.length > 0)
		{
			for (ItemStack itemstack : stacks)
			{
				if (!itemstack.isNotValid())
				{
					return new Ingredient(stacks);
				}
			}
		}

		return INGREDIENT_AIR;
	}
}
