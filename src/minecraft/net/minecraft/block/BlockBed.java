package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBed extends BlockHorizontal implements ITileEntityProvider
{
	public static final PropertyEnum<BlockBed.EnumPartType> PART = PropertyEnum.<BlockBed.EnumPartType>create("part", BlockBed.EnumPartType.class);
	public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
	protected static final AxisAlignedBB BED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);

	public BlockBed()
	{
		super(Material.CLOTH);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(OCCUPIED, Boolean.valueOf(false)));
		this.isBlockContainer = true;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
		{
			TileEntity tileentity = p_180659_2_.getTileEntity(p_180659_3_);

			if (tileentity instanceof TileEntityBed)
			{
				EnumDyeColor enumdyecolor = ((TileEntityBed) tileentity).func_193048_a();
				return MapColor.func_193558_a(enumdyecolor);
			}
		}

		return MapColor.CLOTH;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			if (state.getValue(PART) != BlockBed.EnumPartType.HEAD)
			{
				pos = pos.offset(state.getValue(FACING));
				state = worldIn.getBlockState(pos);

				if (state.getBlock() != this)
				{
					return true;
				}
			}

			if (worldIn.provider.canRespawnHere() && worldIn.getBiome(pos) != Biomes.HELL)
			{
				if (state.getValue(OCCUPIED).booleanValue())
				{
					EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

					if (entityplayer != null)
					{
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.occupied", new Object[0]), true);
						return true;
					}

					state = state.withProperty(OCCUPIED, Boolean.valueOf(false));
					worldIn.setBlockState(pos, state, 4);
				}

				EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);

				if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
				{
					state = state.withProperty(OCCUPIED, Boolean.valueOf(true));
					worldIn.setBlockState(pos, state, 4);
					return true;
				}
				else
				{
					if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
					{
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
					}
					else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE)
					{
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
					}
					else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY)
					{
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
					}

					return true;
				}
			}
			else
			{
				worldIn.setBlockToAir(pos);
				BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

				if (worldIn.getBlockState(blockpos).getBlock() == this)
				{
					worldIn.setBlockToAir(blockpos);
				}

				worldIn.newExplosion((Entity) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
				return true;
			}
		}
	}

	@Nullable
	private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos)
	{
		for (EntityPlayer entityplayer : worldIn.playerEntities)
		{
			if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
			{
				return entityplayer;
			}
		}

		return null;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 0.5F);
	}

	/**
	 * Called when an Entity lands on this Block. This method *must* update
	 * motionY because the entity will not do that on its own
	 */
	@Override
	public void onLanded(World worldIn, Entity entityIn)
	{
		if (entityIn.isSneaking())
		{
			super.onLanded(worldIn, entityIn);
		}
		else if (entityIn.motionY < 0.0D)
		{
			entityIn.motionY = -entityIn.motionY * 0.6600000262260437D;

			if (!(entityIn instanceof EntityLivingBase))
			{
				entityIn.motionY *= 0.8D;
			}
		}
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
		EnumFacing enumfacing = state.getValue(FACING);

		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
		{
			if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this)
			{
				worldIn.setBlockToAir(pos);
			}
		}
		else if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this)
		{
			if (!worldIn.isRemote)
			{
				this.dropBlockAsItem(worldIn, pos, state, 0);
			}

			worldIn.setBlockToAir(pos);
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(PART) == BlockBed.EnumPartType.FOOT ? Items.EMPTY_ITEM : Items.BED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BED_AABB;
	}

	@Override
	public boolean func_190946_v(IBlockState p_190946_1_)
	{
		return true;
	}

	@Nullable

	/**
	 * Returns a safe BlockPos to disembark the bed
	 */
	public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries)
	{
		EnumFacing enumfacing = worldIn.getBlockState(pos).getValue(FACING);
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for (int l = 0; l <= 1; ++l)
		{
			int i1 = i - enumfacing.getFrontOffsetX() * l - 1;
			int j1 = k - enumfacing.getFrontOffsetZ() * l - 1;
			int k1 = i1 + 2;
			int l1 = j1 + 2;

			for (int i2 = i1; i2 <= k1; ++i2)
			{
				for (int j2 = j1; j2 <= l1; ++j2)
				{
					BlockPos blockpos = new BlockPos(i2, j, j2);

					if (hasRoomForPlayer(worldIn, blockpos))
					{
						if (tries <= 0)
						{
							return blockpos;
						}

						--tries;
					}
				}
			}
		}

		return null;
	}

	protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isFullyOpaque() && !worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getMaterial().isSolid();
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed) tileentity).func_193048_a() : EnumDyeColor.RED;
			spawnAsEntity(worldIn, pos, new ItemStack(Items.BED, 1, enumdyecolor.getMetadata()));
		}
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.DESTROY;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static
	 * model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids,
	 * INVISIBLE to skip all rendering
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		BlockPos blockpos = pos;

		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
		{
			blockpos = pos.offset(state.getValue(FACING));
		}

		TileEntity tileentity = worldIn.getTileEntity(blockpos);
		EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed) tileentity).func_193048_a() : EnumDyeColor.RED;
		return new ItemStack(Items.BED, 1, enumdyecolor.getMetadata());
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockBed.EnumPartType.FOOT)
		{
			BlockPos blockpos = pos.offset(state.getValue(FACING));

			if (worldIn.getBlockState(blockpos).getBlock() == this)
			{
				worldIn.setBlockToAir(blockpos);
			}
		}
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && te instanceof TileEntityBed)
		{
			TileEntityBed tileentitybed = (TileEntityBed) te;
			ItemStack itemstack = tileentitybed.func_193049_f();
			spawnAsEntity(worldIn, pos, itemstack);
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
		}
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, Boolean.valueOf((meta & 4) > 0)) : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(state.getValue(FACING)));

			if (iblockstate.getBlock() == this)
			{
				state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
			}
		}

		return state;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed
	 * blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD)
		{
			i |= 8;

			if (state.getValue(OCCUPIED).booleanValue())
			{
				i |= 4;
			}
		}

		return i;
	}

	@Override
	public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, PART, OCCUPIED });
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityBed();
	}

	public static boolean func_193385_b(int p_193385_0_)
	{
		return (p_193385_0_ & 8) != 0;
	}

	public static enum EnumPartType implements IStringSerializable
	{
		HEAD("head"), FOOT("foot");

		private final String name;

		private EnumPartType(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return this.name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}
}
