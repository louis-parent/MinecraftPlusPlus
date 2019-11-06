package net.minecraft.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ItemPickaxe extends ItemTool
{
	@Mod("Minecraftpp")
	private static Set<Material> EFFECTIVE_ON;

	@Mod("Minecraftpp")
	private static Map<Block, Integer> HARVEST_LEVEL;

	private static Set<Material> getEffectiveOn()
	{
		if (EFFECTIVE_ON == null)
		{
			EFFECTIVE_ON = Sets.newHashSet(Material.ROCK, Material.IRON, Material.ANVIL, Material.ICE, Material.PACKED_ICE, Material.RAIL);
		}

		return EFFECTIVE_ON;

	}

	private static Map<Block, Integer> getHarvestLevel()
	{
		if (HARVEST_LEVEL == null)
		{
			HARVEST_LEVEL = new HashMap<Block, Integer>()
			{
				{
					put(Blocks.OBSIDIAN, 3);
					put(Blocks.DIAMOND_BLOCK, 2);
					put(Blocks.DIAMOND_ORE, 2);
					put(Blocks.EMERALD_ORE, 2);
					put(Blocks.EMERALD_BLOCK, 2);
					put(Blocks.GOLD_BLOCK, 2);
					put(Blocks.GOLD_ORE, 2);
					put(Blocks.IRON_BLOCK, 1);
					put(Blocks.IRON_ORE, 1);
					put(Blocks.LAPIS_BLOCK, 1);
					put(Blocks.LAPIS_ORE, 1);
					put(Blocks.REDSTONE_ORE, 2);
					put(Blocks.LIT_REDSTONE_ORE, 2);
				}
			};
		}

		return HARVEST_LEVEL;
	}

	public ItemPickaxe(IToolMaterial material)
	{
		super(1.0F, -2.8F, material);
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean canHarvestBlock(IBlockState state)
	{
		return getEffectiveOn().contains(state.getMaterial()) && this.getToolMaterial().getHarvestLevel() >= (getHarvestLevel().containsKey(state.getBlock()) ? getHarvestLevel().get(state.getBlock()) : state.getBlock().getRequiredHarvestLevel());
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		return getEffectiveOn().contains(state.getMaterial()) ? this.efficiencyOnProperMaterial : 1.0F;
	}

	@Override
	public ToolType getToolType()
	{
		return ToolType.PICKAXE;
	}
}
