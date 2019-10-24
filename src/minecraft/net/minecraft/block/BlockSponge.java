package net.minecraft.block;

import java.util.Random;

import fr.minecraftpp.block.IAbsorbing;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockSponge extends Block implements IAbsorbing
{
	public static final PropertyBool WET = PropertyBool.create("wet");

	protected BlockSponge()
	{
		super(Material.SPONGE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	/**
	 * Gets the localized name of this block. Used for the statistics page.
	 */
	@Override
	public String getLocalizedName()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".dry.name");
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called
	 * when the block gets destroyed. It returns the metadata of the dropped
	 * item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(WET).booleanValue() ? 1 : 0;
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile
	 * Entity is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.tryAbsorb(worldIn, pos, state);
	}

	/**
	 * Called when a neighboring block was changed and marks that this state
	 * should perform any checks during a neighbor change. Cases may include
	 * when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_)
	{
		this.tryAbsorb(worldIn, pos, state);
		super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab)
	{
		tab.add(new ItemStack(this, 1, 0));
		tab.add(new ItemStack(this, 1, 1));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(WET, Boolean.valueOf((meta & 1) == 1));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(WET).booleanValue() ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { WET });
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (stateIn.getValue(WET).booleanValue())
		{
			EnumFacing enumfacing = EnumFacing.random(rand);

			if (enumfacing != EnumFacing.UP && !worldIn.getBlockState(pos.offset(enumfacing)).isFullyOpaque())
			{
				double d0 = pos.getX();
				double d1 = pos.getY();
				double d2 = pos.getZ();

				if (enumfacing == EnumFacing.DOWN)
				{
					d1 = d1 - 0.05D;
					d0 += rand.nextDouble();
					d2 += rand.nextDouble();
				}
				else
				{
					d1 = d1 + rand.nextDouble() * 0.8D;

					if (enumfacing.getAxis() == EnumFacing.Axis.X)
					{
						d2 += rand.nextDouble();

						if (enumfacing == EnumFacing.EAST)
						{
							++d0;
						}
						else
						{
							d0 += 0.05D;
						}
					}
					else
					{
						d0 += rand.nextDouble();

						if (enumfacing == EnumFacing.SOUTH)
						{
							++d2;
						}
						else
						{
							d2 += 0.05D;
						}
					}
				}

				worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean tryAbsorb(World world, BlockPos pos, IBlockState state)
	{
		if (!state.getValue(WET).booleanValue() && IAbsorbing.super.tryAbsorb(world, pos, state))
		{
			world.setBlockState(pos, state.withProperty(WET, Boolean.valueOf(true)), 2);
			return true;
		}
		else
		{
			return false;
		}
	}
}
