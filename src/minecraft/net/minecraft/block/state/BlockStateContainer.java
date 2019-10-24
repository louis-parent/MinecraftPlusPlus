package net.minecraft.block.state;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MapPopulator;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Cartesian;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStateContainer
{
	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
	private static final Function<IProperty<?>, String> GET_NAME_FUNC = new Function<IProperty<?>, String>()
	{
		@Override
		@Nullable
		public String apply(@Nullable IProperty<?> p_apply_1_)
		{
			return p_apply_1_ == null ? "<NULL>" : p_apply_1_.getName();
		}
	};
	private final Block block;
	private final ImmutableSortedMap<String, IProperty<?>> properties;
	private final ImmutableList<IBlockState> validStates;

	public BlockStateContainer(Block blockIn, IProperty<?>... properties)
	{
		this.block = blockIn;
		Map<String, IProperty<?>> map = Maps.<String, IProperty<?>>newHashMap();

		for (IProperty<?> iproperty : properties)
		{
			validateProperty(blockIn, iproperty);
			map.put(iproperty.getName(), iproperty);
		}

		this.properties = ImmutableSortedMap.copyOf(map);
		Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map2 = Maps.<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation>newLinkedHashMap();
		List<BlockStateContainer.StateImplementation> list1 = Lists.<BlockStateContainer.StateImplementation>newArrayList();

		for (List<Comparable<?>> list : Cartesian.cartesianProduct(this.getAllowedValues()))
		{
			Map<IProperty<?>, Comparable<?>> map1 = MapPopulator.<IProperty<?>, Comparable<?>>createMap(this.properties.values(), list);
			BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation = new BlockStateContainer.StateImplementation(blockIn, ImmutableMap.copyOf(map1));
			map2.put(map1, blockstatecontainer$stateimplementation);
			list1.add(blockstatecontainer$stateimplementation);
		}

		for (BlockStateContainer.StateImplementation blockstatecontainer$stateimplementation1 : list1)
		{
			blockstatecontainer$stateimplementation1.buildPropertyValueTable(map2);
		}

		this.validStates = ImmutableList.<IBlockState>copyOf(list1);
	}

	public static <T extends Comparable<T>> String validateProperty(Block block, IProperty<T> property)
	{
		String s = property.getName();

		if (!NAME_PATTERN.matcher(s).matches())
		{
			throw new IllegalArgumentException("Block: " + block.getClass() + " has invalidly named property: " + s);
		}
		else
		{
			for (T t : property.getAllowedValues())
			{
				String s1 = property.getName(t);

				if (!NAME_PATTERN.matcher(s1).matches())
				{
					throw new IllegalArgumentException("Block: " + block.getClass() + " has property: " + s + " with invalidly named value: " + s1);
				}
			}

			return s;
		}
	}

	public ImmutableList<IBlockState> getValidStates()
	{
		return this.validStates;
	}

	private List<Iterable<Comparable<?>>> getAllowedValues()
	{
		List<Iterable<Comparable<?>>> list = Lists.<Iterable<Comparable<?>>>newArrayList();
		ImmutableCollection<IProperty<?>> immutablecollection = this.properties.values();
		UnmodifiableIterator unmodifiableiterator = immutablecollection.iterator();

		while (unmodifiableiterator.hasNext())
		{
			IProperty<?> iproperty = (IProperty) unmodifiableiterator.next();
			list.add(((IProperty) iproperty).getAllowedValues());
		}

		return list;
	}

	public IBlockState getBaseState()
	{
		return this.validStates.get(0);
	}

	public Block getBlock()
	{
		return this.block;
	}

	public Collection<IProperty<?>> getProperties()
	{
		return this.properties.values();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).add("block", Block.REGISTRY.getNameForObject(this.block)).add("properties", Iterables.transform(this.properties.values(), GET_NAME_FUNC)).toString();
	}

	@Nullable
	public IProperty<?> getProperty(String propertyName)
	{
		return this.properties.get(propertyName);
	}

	static class StateImplementation extends BlockStateBase
	{
		private final Block block;
		private final ImmutableMap<IProperty<?>, Comparable<?>> properties;
		private ImmutableTable<IProperty<?>, Comparable<?>, IBlockState> propertyValueTable;

		private StateImplementation(Block blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
		{
			this.block = blockIn;
			this.properties = propertiesIn;
		}

		@Override
		public Collection<IProperty<?>> getPropertyNames()
		{
			return Collections.<IProperty<?>>unmodifiableCollection(this.properties.keySet());
		}

		@Override
		public <T extends Comparable<T>> T getValue(IProperty<T> property)
		{
			Comparable<?> comparable = this.properties.get(property);

			if (comparable == null)
			{
				throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
			}
			else
			{
				return (property.getValueClass().cast(comparable));
			}
		}

		@Override
		public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value)
		{
			Comparable<?> comparable = this.properties.get(property);

			if (comparable == null)
			{
				throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
			}
			else if (comparable == value)
			{
				return this;
			}
			else
			{
				IBlockState iblockstate = this.propertyValueTable.get(property, value);

				if (iblockstate == null)
				{
					throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.REGISTRY.getNameForObject(this.block) + ", it is not an allowed value");
				}
				else
				{
					return iblockstate;
				}
			}
		}

		@Override
		public ImmutableMap<IProperty<?>, Comparable<?>> getProperties()
		{
			return this.properties;
		}

		@Override
		public Block getBlock()
		{
			return this.block;
		}

		@Override
		public boolean equals(Object p_equals_1_)
		{
			return this == p_equals_1_;
		}

		@Override
		public int hashCode()
		{
			return this.properties.hashCode();
		}

		public void buildPropertyValueTable(Map<Map<IProperty<?>, Comparable<?>>, BlockStateContainer.StateImplementation> map)
		{
			if (this.propertyValueTable != null)
			{
				throw new IllegalStateException();
			}
			else
			{
				Table<IProperty<?>, Comparable<?>, IBlockState> table = HashBasedTable.<IProperty<?>, Comparable<?>, IBlockState>create();
				UnmodifiableIterator unmodifiableiterator = this.properties.entrySet().iterator();

				while (unmodifiableiterator.hasNext())
				{
					Entry<IProperty<?>, Comparable<?>> entry = (Entry) unmodifiableiterator.next();
					IProperty<?> iproperty = entry.getKey();

					for (Comparable<?> comparable : iproperty.getAllowedValues())
					{
						if (comparable != entry.getValue())
						{
							table.put(iproperty, comparable, map.get(this.getPropertiesWithValue(iproperty, comparable)));
						}
					}
				}

				this.propertyValueTable = ImmutableTable.copyOf(table);
			}
		}

		private Map<IProperty<?>, Comparable<?>> getPropertiesWithValue(IProperty<?> property, Comparable<?> value)
		{
			Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newHashMap(this.properties);
			map.put(property, value);
			return map;
		}

		@Override
		public Material getMaterial()
		{
			return this.block.getMaterial(this);
		}

		@Override
		public boolean isFullBlock()
		{
			return this.block.isFullBlock(this);
		}

		@Override
		public boolean canEntitySpawn(Entity entityIn)
		{
			return this.block.canEntitySpawn(this, entityIn);
		}

		@Override
		public int getLightOpacity()
		{
			return this.block.getLightOpacity(this);
		}

		@Override
		public int getLightValue()
		{
			return this.block.getLightValue(this);
		}

		@Override
		public boolean isTranslucent()
		{
			return this.block.isTranslucent(this);
		}

		@Override
		public boolean useNeighborBrightness()
		{
			return this.block.getUseNeighborBrightness(this);
		}

		@Override
		public MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_)
		{
			return this.block.getMapColor(this, p_185909_1_, p_185909_2_);
		}

		@Override
		public IBlockState withRotation(Rotation rot)
		{
			return this.block.withRotation(this, rot);
		}

		@Override
		public IBlockState withMirror(Mirror mirrorIn)
		{
			return this.block.withMirror(this, mirrorIn);
		}

		@Override
		public boolean isFullCube()
		{
			return this.block.isFullCube(this);
		}

		@Override
		public boolean func_191057_i()
		{
			return this.block.func_190946_v(this);
		}

		@Override
		public EnumBlockRenderType getRenderType()
		{
			return this.block.getRenderType(this);
		}

		@Override
		public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos)
		{
			return this.block.getPackedLightmapCoords(this, source, pos);
		}

		@Override
		public float getAmbientOcclusionLightValue()
		{
			return this.block.getAmbientOcclusionLightValue(this);
		}

		@Override
		public boolean isBlockNormalCube()
		{
			return this.block.isBlockNormalCube(this);
		}

		@Override
		public boolean isNormalCube()
		{
			return this.block.isNormalCube(this);
		}

		@Override
		public boolean canProvidePower()
		{
			return this.block.canProvidePower(this);
		}

		@Override
		public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
		{
			return this.block.getWeakPower(this, blockAccess, pos, side);
		}

		@Override
		public boolean hasComparatorInputOverride()
		{
			return this.block.hasComparatorInputOverride(this);
		}

		@Override
		public int getComparatorInputOverride(World worldIn, BlockPos pos)
		{
			return this.block.getComparatorInputOverride(this, worldIn, pos);
		}

		@Override
		public float getBlockHardness(World worldIn, BlockPos pos)
		{
			return this.block.getBlockHardness(this, worldIn, pos);
		}

		@Override
		public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos)
		{
			return this.block.getPlayerRelativeBlockHardness(this, player, worldIn, pos);
		}

		@Override
		public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
		{
			return this.block.getStrongPower(this, blockAccess, pos, side);
		}

		@Override
		public EnumPushReaction getMobilityFlag()
		{
			return this.block.getMobilityFlag(this);
		}

		@Override
		public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos)
		{
			return this.block.getActualState(this, blockAccess, pos);
		}

		@Override
		public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
		{
			return this.block.getSelectedBoundingBox(this, worldIn, pos);
		}

		@Override
		public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing)
		{
			return this.block.shouldSideBeRendered(this, blockAccess, pos, facing);
		}

		@Override
		public boolean isOpaqueCube()
		{
			return this.block.isOpaqueCube(this);
		}

		@Override
		@Nullable
		public AxisAlignedBB getCollisionBoundingBox(IBlockAccess worldIn, BlockPos pos)
		{
			return this.block.getCollisionBoundingBox(this, worldIn, pos);
		}

		@Override
		public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_, List<AxisAlignedBB> p_185908_4_, @Nullable Entity p_185908_5_, boolean p_185908_6_)
		{
			this.block.addCollisionBoxToList(this, worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_, p_185908_6_);
		}

		@Override
		public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos)
		{
			return this.block.getBoundingBox(this, blockAccess, pos);
		}

		@Override
		public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end)
		{
			return this.block.collisionRayTrace(this, worldIn, pos, start, end);
		}

		@Override
		public boolean isFullyOpaque()
		{
			return this.block.isFullyOpaque(this);
		}

		@Override
		public Vec3d func_191059_e(IBlockAccess p_191059_1_, BlockPos p_191059_2_)
		{
			return this.block.func_190949_e(this, p_191059_1_, p_191059_2_);
		}

		@Override
		public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
		{
			return this.block.eventReceived(this, worldIn, pos, id, param);
		}

		@Override
		public void neighborChanged(World worldIn, BlockPos pos, Block blockIn, BlockPos p_189546_4_)
		{
			this.block.neighborChanged(this, worldIn, pos, blockIn, p_189546_4_);
		}

		@Override
		public boolean func_191058_s()
		{
			return this.block.causesSuffocation(this);
		}

		@Override
		public BlockFaceShape func_193401_d(IBlockAccess p_193401_1_, BlockPos p_193401_2_, EnumFacing p_193401_3_)
		{
			return this.block.func_193383_a(p_193401_1_, this, p_193401_2_, p_193401_3_);
		}
	}
}
