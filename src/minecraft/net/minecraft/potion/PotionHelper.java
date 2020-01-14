package net.minecraft.potion;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PotionHelper
{
	private static List<PotionHelper.MixPredicate<PotionType>> POTION_TYPE_CONVERSIONS = Lists.<PotionHelper.MixPredicate<PotionType>>newArrayList();
	private static List<PotionHelper.MixPredicate<Item>> POTION_ITEM_CONVERSIONS = Lists.<PotionHelper.MixPredicate<Item>>newArrayList();
	private static List<Ingredient> POTION_ITEMS = Lists.<Ingredient>newArrayList();
	private static final Predicate<ItemStack> IS_POTION_ITEM = new Predicate<ItemStack>()
	{
		@Override
		public boolean apply(ItemStack p_apply_1_)
		{
			for (Ingredient ingredient : PotionHelper.POTION_ITEMS)
			{
				if (ingredient.apply(p_apply_1_))
				{
					return true;
				}
			}

			return false;
		}
	};

	public static boolean isReagent(ItemStack stack)
	{
		return isItemConversionReagent(stack) || isTypeConversionReagent(stack);
	}

	protected static boolean isItemConversionReagent(ItemStack stack)
	{
		int i = 0;

		for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
		{
			if ((POTION_ITEM_CONVERSIONS.get(i)).reagent.apply(stack))
			{
				return true;
			}
		}

		return false;
	}

	protected static boolean isTypeConversionReagent(ItemStack stack)
	{
		int i = 0;

		for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i)
		{
			if ((POTION_TYPE_CONVERSIONS.get(i)).reagent.apply(stack))
			{
				return true;
			}
		}

		return false;
	}

	public static boolean hasConversions(ItemStack input, ItemStack reagent)
	{
		if (!IS_POTION_ITEM.apply(input))
		{
			return false;
		}
		else
		{
			return hasItemConversions(input, reagent) || hasTypeConversions(input, reagent);
		}
	}

	protected static boolean hasItemConversions(ItemStack p_185206_0_, ItemStack p_185206_1_)
	{
		Item item = p_185206_0_.getItem();
		int i = 0;

		for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
		{
			PotionHelper.MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);

			if (mixpredicate.input == item && mixpredicate.reagent.apply(p_185206_1_))
			{
				return true;
			}
		}

		return false;
	}

	protected static boolean hasTypeConversions(ItemStack p_185209_0_, ItemStack p_185209_1_)
	{
		PotionType potiontype = PotionUtils.getPotionFromItem(p_185209_0_);
		int i = 0;

		for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i)
		{
			PotionHelper.MixPredicate<PotionType> mixpredicate = POTION_TYPE_CONVERSIONS.get(i);

			if (mixpredicate.input == potiontype && mixpredicate.reagent.apply(p_185209_1_))
			{
				return true;
			}
		}

		return false;
	}

	public static ItemStack doReaction(ItemStack reagent, ItemStack potionIn)
	{
		if (!potionIn.isNotValid())
		{
			PotionType potiontype = PotionUtils.getPotionFromItem(potionIn);
			Item item = potionIn.getItem();
			int i = 0;

			for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i)
			{
				PotionHelper.MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);

				if (mixpredicate.input == item && mixpredicate.reagent.apply(reagent))
				{
					return PotionUtils.addPotionToItemStack(new ItemStack(mixpredicate.output), potiontype);
				}
			}

			i = 0;

			for (int k = POTION_TYPE_CONVERSIONS.size(); i < k; ++i)
			{
				PotionHelper.MixPredicate<PotionType> mixpredicate1 = POTION_TYPE_CONVERSIONS.get(i);

				if (mixpredicate1.input == potiontype && mixpredicate1.reagent.apply(reagent))
				{
					return PotionUtils.addPotionToItemStack(new ItemStack(item), mixpredicate1.output);
				}
			}
		}

		return potionIn;
	}

	public static void init()
	{
		POTION_TYPE_CONVERSIONS = Lists.<PotionHelper.MixPredicate<PotionType>>newArrayList();
		POTION_ITEM_CONVERSIONS = Lists.<PotionHelper.MixPredicate<Item>>newArrayList();
		POTION_ITEMS = Lists.<Ingredient>newArrayList();
		
		func_193354_a((ItemPotion) Items.getItem(Items.POTIONITEM));
		func_193354_a((ItemPotion) Items.getItem(Items.SPLASH_POTION));
		func_193354_a((ItemPotion) Items.getItem(Items.LINGERING_POTION));
		func_193355_a((ItemPotion) Items.getItem(Items.POTIONITEM), Items.getItem(Items.GUNPOWDER), (ItemPotion) Items.getItem(Items.SPLASH_POTION));
		func_193355_a((ItemPotion) Items.getItem(Items.SPLASH_POTION), Items.getItem(Items.DRAGON_BREATH), (ItemPotion) Items.getItem(Items.LINGERING_POTION));
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.SPECKLED_MELON), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.GHAST_TEAR), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.RABBIT_FOOT), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.BLAZE_POWDER), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.SPIDER_EYE), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.SUGAR), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.MAGMA_CREAM), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.THICK);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.REDSTONE), PotionTypes.MUNDANE);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.NETHER_WART), PotionTypes.AWKWARD);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.GOLDEN_CARROT), PotionTypes.NIGHT_VISION);
		func_193357_a(PotionTypes.NIGHT_VISION, Items.getItem(Items.REDSTONE), PotionTypes.LONG_NIGHT_VISION);
		func_193357_a(PotionTypes.NIGHT_VISION, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.INVISIBILITY);
		func_193357_a(PotionTypes.LONG_NIGHT_VISION, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.LONG_INVISIBILITY);
		func_193357_a(PotionTypes.INVISIBILITY, Items.getItem(Items.REDSTONE), PotionTypes.LONG_INVISIBILITY);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.MAGMA_CREAM), PotionTypes.FIRE_RESISTANCE);
		func_193357_a(PotionTypes.FIRE_RESISTANCE, Items.getItem(Items.REDSTONE), PotionTypes.LONG_FIRE_RESISTANCE);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.RABBIT_FOOT), PotionTypes.LEAPING);
		func_193357_a(PotionTypes.LEAPING, Items.getItem(Items.REDSTONE), PotionTypes.LONG_LEAPING);
		func_193357_a(PotionTypes.LEAPING, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_LEAPING);
		func_193357_a(PotionTypes.LEAPING, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.SLOWNESS);
		func_193357_a(PotionTypes.LONG_LEAPING, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.LONG_SLOWNESS);
		func_193357_a(PotionTypes.SLOWNESS, Items.getItem(Items.REDSTONE), PotionTypes.LONG_SLOWNESS);
		func_193357_a(PotionTypes.SWIFTNESS, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.SLOWNESS);
		func_193357_a(PotionTypes.LONG_SWIFTNESS, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.LONG_SLOWNESS);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.SUGAR), PotionTypes.SWIFTNESS);
		func_193357_a(PotionTypes.SWIFTNESS, Items.getItem(Items.REDSTONE), PotionTypes.LONG_SWIFTNESS);
		func_193357_a(PotionTypes.SWIFTNESS, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_SWIFTNESS);
		func_193356_a(PotionTypes.AWKWARD, Ingredient.getIngredientFromItemStack(new ItemStack(Items.getItem(Items.FISH), 1, ItemFishFood.FishType.PUFFERFISH.getMetadata())), PotionTypes.WATER_BREATHING);
		func_193357_a(PotionTypes.WATER_BREATHING, Items.getItem(Items.REDSTONE), PotionTypes.LONG_WATER_BREATHING);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.SPECKLED_MELON), PotionTypes.HEALING);
		func_193357_a(PotionTypes.HEALING, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_HEALING);
		func_193357_a(PotionTypes.HEALING, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.HARMING);
		func_193357_a(PotionTypes.STRONG_HEALING, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.STRONG_HARMING);
		func_193357_a(PotionTypes.HARMING, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_HARMING);
		func_193357_a(PotionTypes.POISON, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.HARMING);
		func_193357_a(PotionTypes.LONG_POISON, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.HARMING);
		func_193357_a(PotionTypes.STRONG_POISON, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.STRONG_HARMING);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.SPIDER_EYE), PotionTypes.POISON);
		func_193357_a(PotionTypes.POISON, Items.getItem(Items.REDSTONE), PotionTypes.LONG_POISON);
		func_193357_a(PotionTypes.POISON, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_POISON);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.GHAST_TEAR), PotionTypes.REGENERATION);
		func_193357_a(PotionTypes.REGENERATION, Items.getItem(Items.REDSTONE), PotionTypes.LONG_REGENERATION);
		func_193357_a(PotionTypes.REGENERATION, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_REGENERATION);
		func_193357_a(PotionTypes.AWKWARD, Items.getItem(Items.BLAZE_POWDER), PotionTypes.STRENGTH);
		func_193357_a(PotionTypes.STRENGTH, Items.getItem(Items.REDSTONE), PotionTypes.LONG_STRENGTH);
		func_193357_a(PotionTypes.STRENGTH, Items.getItem(Items.GLOWSTONE_DUST), PotionTypes.STRONG_STRENGTH);
		func_193357_a(PotionTypes.WATER, Items.getItem(Items.FERMENTED_SPIDER_EYE), PotionTypes.WEAKNESS);
		func_193357_a(PotionTypes.WEAKNESS, Items.getItem(Items.REDSTONE), PotionTypes.LONG_WEAKNESS);
	}

	private static void func_193355_a(ItemPotion inputPotion, Item modifier, ItemPotion outputPotion)
	{
		POTION_ITEM_CONVERSIONS.add(new PotionHelper.MixPredicate(inputPotion, Ingredient.getIngredientFromItems(modifier), outputPotion));
	}

	private static void func_193354_a(ItemPotion p_193354_0_)
	{
		POTION_ITEMS.add(Ingredient.getIngredientFromItems(p_193354_0_));
	}

	private static void func_193357_a(PotionType p_193357_0_, Item p_193357_1_, PotionType p_193357_2_)
	{
		func_193356_a(p_193357_0_, Ingredient.getIngredientFromItems(p_193357_1_), p_193357_2_);
	}

	private static void func_193356_a(PotionType p_193356_0_, Ingredient p_193356_1_, PotionType p_193356_2_)
	{
		POTION_TYPE_CONVERSIONS.add(new PotionHelper.MixPredicate(p_193356_0_, p_193356_1_, p_193356_2_));
	}

	static class MixPredicate<T>
	{
		final T input;
		final Ingredient reagent;
		final T output;

		public MixPredicate(T p_i47570_1_, Ingredient p_i47570_2_, T p_i47570_3_)
		{
			this.input = p_i47570_1_;
			this.reagent = p_i47570_2_;
			this.output = p_i47570_3_;
		}
	}
}
