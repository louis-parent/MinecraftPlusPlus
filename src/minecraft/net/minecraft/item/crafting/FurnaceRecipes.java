package net.minecraft.item.crafting;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import fr.minecraftpp.manager.crafting.ModFurnaceRecipes;
import fr.minecraftpp.variant.Variant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes
{
	private static FurnaceRecipes SMELTING_BASE = null;
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	/**
	 * Returns an instance of FurnaceRecipes.
	 */
	public static FurnaceRecipes instance()
	{
		if (SMELTING_BASE == null)
		{
			SMELTING_BASE = new FurnaceRecipes();
		}

		return SMELTING_BASE;
	}

	private FurnaceRecipes()
	{
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.IRON_ORE), new ItemStack(Items.getItem(Items.OLD_IRON_INGOT)), 0.7F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.GOLD_ORE), new ItemStack(Items.getItem(Items.OLD_GOLD_INGOT)), 1.0F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.DIAMOND_ORE), new ItemStack(Items.getItem(Items.OLD_DIAMOND)), 1.0F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.SAND), new ItemStack(Blocks.getBlock(Blocks.GLASS)), 0.1F);
		this.addSmelting(Items.getItem(Items.PORKCHOP), new ItemStack(Items.getItem(Items.COOKED_PORKCHOP)), 0.35F);
		this.addSmelting(Items.getItem(Items.BEEF), new ItemStack(Items.getItem(Items.COOKED_BEEF)), 0.35F);
		this.addSmelting(Items.getItem(Items.CHICKEN), new ItemStack(Items.getItem(Items.COOKED_CHICKEN)), 0.35F);
		this.addSmelting(Items.getItem(Items.RABBIT), new ItemStack(Items.getItem(Items.COOKED_RABBIT)), 0.35F);
		this.addSmelting(Items.getItem(Items.MUTTON), new ItemStack(Items.getItem(Items.COOKED_MUTTON)), 0.35F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.COBBLESTONE), new ItemStack(Blocks.getBlock(Blocks.STONE)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STONEBRICK), 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.getBlock(Blocks.STONEBRICK), 1, BlockStoneBrick.CRACKED_META), 0.1F);
		this.addSmelting(Items.getItem(Items.CLAY_BALL), new ItemStack(Items.getItem(Items.BRICK)), 0.3F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.CLAY), new ItemStack(Blocks.getBlock(Blocks.HARDENED_CLAY)), 0.35F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.CACTUS), new ItemStack(Items.getItem(Items.DYE), 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.LOG), new ItemStack(Variant.getInstance().getRandomVariantOf(Items.getItem(Items.OLD_COAL)), 1, 1), 0.15F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.LOG2), new ItemStack(Variant.getInstance().getRandomVariantOf(Items.getItem(Items.OLD_COAL)), 1, 1), 0.15F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.EMERALD_ORE), new ItemStack(Items.getItem(Items.EMERALD)), 1.0F);
		this.addSmelting(Items.getItem(Items.POTATO), new ItemStack(Items.getItem(Items.BAKED_POTATO)), 0.35F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.NETHERRACK), new ItemStack(Items.getItem(Items.NETHERBRICK)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.SPONGE), 1, 1), new ItemStack(Blocks.getBlock(Blocks.SPONGE), 1, 0), 0.15F);
		this.addSmelting(Items.getItem(Items.CHORUS_FRUIT), new ItemStack(Items.getItem(Items.CHORUS_FRUIT_POPPED)), 0.1F);

		for (ItemFishFood.FishType itemfishfood$fishtype : ItemFishFood.FishType.values())
		{
			if (itemfishfood$fishtype.canCook())
			{
				this.addSmeltingRecipe(new ItemStack(Items.getItem(Items.FISH), 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.getItem(Items.COOKED_FISH), 1, itemfishfood$fishtype.getMetadata()), 0.35F);
			}
		}

		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.COAL_ORE), new ItemStack(Items.getItem(Items.OLD_COAL)), 0.1F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.REDSTONE_ORE), new ItemStack(Items.getItem(Items.REDSTONE)), 0.7F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.LAPIS_ORE), new ItemStack(Items.getItem(Items.DYE), 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
		this.addSmeltingRecipeForBlock(Blocks.getBlock(Blocks.QUARTZ_ORE), new ItemStack(Items.getItem(Items.QUARTZ)), 0.2F);
		this.addSmelting(Items.getItem(Items.CHAINMAIL_HELMET), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.CHAINMAIL_CHESTPLATE), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.CHAINMAIL_LEGGINGS), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.CHAINMAIL_BOOTS), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_PICKAXE), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_SHOVEL), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_AXE), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_HOE), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_SWORD), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_HELMET), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_CHESTPLATE), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_LEGGINGS), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_BOOTS), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.IRON_HORSE_ARMOR), new ItemStack(Items.getItem(Items.IRON_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_PICKAXE), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_SHOVEL), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_AXE), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_HOE), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_SWORD), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_HELMET), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_CHESTPLATE), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_LEGGINGS), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_BOOTS), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmelting(Items.getItem(Items.GOLDEN_HORSE_ARMOR), new ItemStack(Items.getItem(Items.GOLD_NUGGET)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.WHITE.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192427_dB)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.ORANGE.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192428_dC)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.MAGENTA.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192429_dD)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.LIGHT_BLUE.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192430_dE)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.YELLOW.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192431_dF)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.LIME.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192432_dG)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.PINK.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192433_dH)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.GRAY.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192434_dI)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.SILVER.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192435_dJ)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.CYAN.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192436_dK)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.PURPLE.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192437_dL)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.BLUE.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192438_dM)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.BROWN.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192439_dN)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.GREEN.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192440_dO)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.RED.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192441_dP)), 0.1F);
		this.addSmeltingRecipe(new ItemStack(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), 1, EnumDyeColor.BLACK.getMetadata()), new ItemStack(Blocks.getBlock(Blocks.field_192442_dQ)), 0.1F);

		/**
		 * MOD FURNACE RECIPES
		 */
		ModFurnaceRecipes.furnaceRecipes(this);
	}

	/**
	 * Adds a smelting recipe, where the input item is an instance of Block.
	 */
	public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	}

	/**
	 * Adds a smelting recipe using an Item as the input item.
	 */
	public void addSmelting(Item input, ItemStack stack, float experience)
	{
		this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}

	/**
	 * Adds a smelting recipe using an ItemStack as the input for the recipe.
	 */
	public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
	{
		this.smeltingList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	/**
	 * Returns the smelting result of an item.
	 */
	public ItemStack getSmeltingResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return entry.getValue();
			}
		}

		return ItemStack.EMPTY_ITEM_STACK;
	}

	/**
	 * Compares two itemstacks to ensure that they are the same. This checks
	 * both the item and the metadata of the item.
	 */
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getSmeltingList()
	{
		return this.smeltingList;
	}

	public float getSmeltingExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return entry.getValue().floatValue();
			}
		}

		return 0.0F;
	}
}
