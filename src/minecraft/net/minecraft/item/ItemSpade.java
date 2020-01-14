package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSpade extends ItemTool
{
	private static Set<Block> EFFECTIVE_ON;

	private static Set<Block> getEffectiveOn()
	{
		if (EFFECTIVE_ON == null)
		{
			EFFECTIVE_ON = Sets.newHashSet(Blocks.getBlock(Blocks.CLAY), Blocks.getBlock(Blocks.DIRT), Blocks.getBlock(Blocks.FARMLAND), Blocks.getBlock(Blocks.GRASS), Blocks.getBlock(Blocks.GRAVEL), Blocks.getBlock(Blocks.MYCELIUM), Blocks.getBlock(Blocks.SAND), Blocks.getBlock(Blocks.SNOW), Blocks.getBlock(Blocks.SNOW_LAYER), Blocks.getBlock(Blocks.SOUL_SAND), Blocks.getBlock(Blocks.GRASS_PATH), Blocks.getBlock(Blocks.CONCRETE_POWDER));
		}

		return EFFECTIVE_ON;

	}

	public ItemSpade(IToolMaterial material)
	{
		super(1.5F, -3.0F, material);
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		Block block = blockIn.getBlock();

		if (block == Blocks.getBlock(Blocks.SNOW_LAYER))
		{
			return true;
		}
		else
		{
			return block == Blocks.getBlock(Blocks.SNOW);
		}
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
	{
		ItemStack itemstack = stack.getHeldItem(pos);

		if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState iblockstate = playerIn.getBlockState(worldIn);
			Block block = iblockstate.getBlock();

			if (hand != EnumFacing.DOWN && playerIn.getBlockState(worldIn.up()).getMaterial() == Material.AIR && block == Blocks.getBlock(Blocks.GRASS))
			{
				IBlockState iblockstate1 = Blocks.getBlock(Blocks.GRASS_PATH).getDefaultState();
				playerIn.playSound(stack, worldIn, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

				if (!playerIn.isRemote)
				{
					playerIn.setBlockState(worldIn, iblockstate1, 11);
					itemstack.damageItem(1, stack);
				}

				return EnumActionResult.SUCCESS;
			}
			else
			{
				return EnumActionResult.PASS;
			}
		}
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		return getEffectiveOn().contains(state.getBlock()) ? this.toolMaterial.getEfficiencyOnProperMaterial() : 1.0F;
	}

	@Override
	public ToolType getToolType()
	{
		return ToolType.SPADE;
	}
}
