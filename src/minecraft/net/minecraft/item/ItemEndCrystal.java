package net.minecraft.item;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;

public class ItemEndCrystal extends Item
{
	public ItemEndCrystal()
	{
		this.setUnlocalizedName("end_crystal");
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
	{
		IBlockState iblockstate = playerIn.getBlockState(worldIn);

		if (iblockstate.getBlock() != Blocks.OBSIDIAN && iblockstate.getBlock() != Blocks.BEDROCK)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			BlockPos blockpos = worldIn.up();
			ItemStack itemstack = stack.getHeldItem(pos);

			if (!stack.canPlayerEdit(blockpos, hand, itemstack))
			{
				return EnumActionResult.FAIL;
			}
			else
			{
				BlockPos blockpos1 = blockpos.up();
				boolean flag = !playerIn.isAirBlock(blockpos) && !playerIn.getBlockState(blockpos).getBlock().isReplaceable(playerIn, blockpos);
				flag = flag | (!playerIn.isAirBlock(blockpos1) && !playerIn.getBlockState(blockpos1).getBlock().isReplaceable(playerIn, blockpos1));

				if (flag)
				{
					return EnumActionResult.FAIL;
				}
				else
				{
					double d0 = blockpos.getX();
					double d1 = blockpos.getY();
					double d2 = blockpos.getZ();
					List<Entity> list = playerIn.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));

					if (!list.isEmpty())
					{
						return EnumActionResult.FAIL;
					}
					else
					{
						if (!playerIn.isRemote)
						{
							EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(playerIn, worldIn.getX() + 0.5F, worldIn.getY() + 1, worldIn.getZ() + 0.5F);
							entityendercrystal.setShowBottom(false);
							playerIn.spawnEntityInWorld(entityendercrystal);

							if (playerIn.provider instanceof WorldProviderEnd)
							{
								DragonFightManager dragonfightmanager = ((WorldProviderEnd) playerIn.provider).getDragonFightManager();
								dragonfightmanager.respawnDragon();
							}
						}

						itemstack.decreaseStackSize(1);
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
