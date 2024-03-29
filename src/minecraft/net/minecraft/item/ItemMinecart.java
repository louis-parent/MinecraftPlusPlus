package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMinecart extends Item
{
	private static final IBehaviorDispenseItem MINECART_DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem()
	{
		private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();

		@Override
		public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
		{
			EnumFacing enumfacing = source.getBlockState().getValue(BlockDispenser.FACING);
			World world = source.getWorld();
			double d0 = source.getX() + enumfacing.getFrontOffsetX() * 1.125D;
			double d1 = Math.floor(source.getY()) + enumfacing.getFrontOffsetY();
			double d2 = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125D;
			BlockPos blockpos = source.getBlockPos().offset(enumfacing);
			IBlockState iblockstate = world.getBlockState(blockpos);
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection) iblockstate.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			double d3;

			if (BlockRailBase.isRailBlock(iblockstate))
			{
				if (blockrailbase$enumraildirection.isAscending())
				{
					d3 = 0.6D;
				}
				else
				{
					d3 = 0.1D;
				}
			}
			else
			{
				if (iblockstate.getMaterial() != Material.AIR || !BlockRailBase.isRailBlock(world.getBlockState(blockpos.down())))
				{
					return this.behaviourDefaultDispenseItem.dispense(source, stack);
				}

				IBlockState iblockstate1 = world.getBlockState(blockpos.down());
				BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = iblockstate1.getBlock() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection) iblockstate1.getValue(((BlockRailBase) iblockstate1.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;

				if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.isAscending())
				{
					d3 = -0.4D;
				}
				else
				{
					d3 = -0.9D;
				}
			}

			EntityMinecart entityminecart = EntityMinecart.create(world, d0, d1 + d3, d2, ((ItemMinecart) stack.getItem()).minecartType);

			if (stack.hasDisplayName())
			{
				entityminecart.setCustomNameTag(stack.getDisplayName());
			}

			world.spawnEntityInWorld(entityminecart);
			stack.decreaseStackSize(1);
			return stack;
		}

		@Override
		protected void playDispenseSound(IBlockSource source)
		{
			source.getWorld().playEvent(1000, source.getBlockPos(), 0);
		}
	};
	private final EntityMinecart.Type minecartType;

	public ItemMinecart(EntityMinecart.Type typeIn)
	{
		this.maxStackSize = 1;
		this.minecartType = typeIn;
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, MINECART_DISPENSER_BEHAVIOR);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
	{
		IBlockState iblockstate = playerIn.getBlockState(worldIn);

		if (!BlockRailBase.isRailBlock(iblockstate))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			ItemStack itemstack = stack.getHeldItem(pos);

			if (!playerIn.isRemote)
			{
				BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getBlock() instanceof BlockRailBase ? (BlockRailBase.EnumRailDirection) iblockstate.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
				double d0 = 0.0D;

				if (blockrailbase$enumraildirection.isAscending())
				{
					d0 = 0.5D;
				}

				EntityMinecart entityminecart = EntityMinecart.create(playerIn, worldIn.getX() + 0.5D, worldIn.getY() + 0.0625D + d0, worldIn.getZ() + 0.5D, this.minecartType);

				if (itemstack.hasDisplayName())
				{
					entityminecart.setCustomNameTag(itemstack.getDisplayName());
				}

				playerIn.spawnEntityInWorld(entityminecart);
			}

			itemstack.decreaseStackSize(1);
			return EnumActionResult.SUCCESS;
		}
	}
}
