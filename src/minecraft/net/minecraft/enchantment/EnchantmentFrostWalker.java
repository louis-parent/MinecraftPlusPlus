package net.minecraft.enchantment;

import fr.minecraftpp.inventory.EntityEquipmentSlot;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment
{
	public EnchantmentFrostWalker(Enchantment.EnchantmentRarity rarityIn, EntityEquipmentSlot... slots)
	{
		super(rarityIn, EnumEnchantmentType.ARMOR_FEET, slots);
		this.setName("frostWalker");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
		return enchantmentLevel * 10;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
		return this.getMinEnchantability(enchantmentLevel) + 15;
	}

	@Override
	public boolean isTreasureEnchantment()
	{
		return true;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel()
	{
		return 2;
	}

	public static void freezeNearby(EntityLivingBase living, World worldIn, BlockPos pos, int level)
	{
		if (living.onGround)
		{
			float f = Math.min(16, 2 + level);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

			for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((-f), -1.0D, (-f)), pos.add(f, -1.0D, f)))
			{
				if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= f * f)
				{
					blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
					IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);

					if (iblockstate.getMaterial() == Material.AIR)
					{
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);

						if (iblockstate1.getMaterial() == Material.WATER && iblockstate1.getValue(BlockLiquid.LEVEL).intValue() == 0 && worldIn.func_190527_a(Blocks.getBlock(Blocks.FROSTED_ICE), blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity) null))
						{
							worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.getBlock(Blocks.FROSTED_ICE).getDefaultState());
							worldIn.scheduleUpdate(blockpos$mutableblockpos1.toImmutable(), Blocks.getBlock(Blocks.FROSTED_ICE), MathHelper.getInt(living.getRNG(), 60, 120));
						}
					}
				}
			}
		}
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(Enchantment ench)
	{
		return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER;
	}
}
