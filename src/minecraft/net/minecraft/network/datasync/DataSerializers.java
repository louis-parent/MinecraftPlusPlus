package net.minecraft.network.datasync;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers
{
	private static final IntIdentityHashBiMap<DataSerializer<?>> REGISTRY = new IntIdentityHashBiMap<DataSerializer<?>>(16);
	public static final DataSerializer<Byte> BYTE = new DataSerializer<Byte>()
	{
		@Override
		public void write(PacketBuffer buf, Byte value)
		{
			buf.writeByte(value.byteValue());
		}

		@Override
		public Byte read(PacketBuffer buf) throws IOException
		{
			return buf.readByte();
		}

		@Override
		public DataParameter<Byte> createKey(int id)
		{
			return new DataParameter<Byte>(id, this);
		}

		@Override
		public Byte func_192717_a(Byte p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Integer> VARINT = new DataSerializer<Integer>()
	{
		@Override
		public void write(PacketBuffer buf, Integer value)
		{
			buf.writeVarIntToBuffer(value.intValue());
		}

		@Override
		public Integer read(PacketBuffer buf) throws IOException
		{
			return buf.readVarIntFromBuffer();
		}

		@Override
		public DataParameter<Integer> createKey(int id)
		{
			return new DataParameter<Integer>(id, this);
		}

		@Override
		public Integer func_192717_a(Integer p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Float> FLOAT = new DataSerializer<Float>()
	{
		@Override
		public void write(PacketBuffer buf, Float value)
		{
			buf.writeFloat(value.floatValue());
		}

		@Override
		public Float read(PacketBuffer buf) throws IOException
		{
			return buf.readFloat();
		}

		@Override
		public DataParameter<Float> createKey(int id)
		{
			return new DataParameter<Float>(id, this);
		}

		@Override
		public Float func_192717_a(Float p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<String> STRING = new DataSerializer<String>()
	{
		@Override
		public void write(PacketBuffer buf, String value)
		{
			buf.writeString(value);
		}

		@Override
		public String read(PacketBuffer buf) throws IOException
		{
			return buf.readStringFromBuffer(32767);
		}

		@Override
		public DataParameter<String> createKey(int id)
		{
			return new DataParameter<String>(id, this);
		}

		@Override
		public String func_192717_a(String p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<ITextComponent> TEXT_COMPONENT = new DataSerializer<ITextComponent>()
	{
		@Override
		public void write(PacketBuffer buf, ITextComponent value)
		{
			buf.writeTextComponent(value);
		}

		@Override
		public ITextComponent read(PacketBuffer buf) throws IOException
		{
			return buf.readTextComponent();
		}

		@Override
		public DataParameter<ITextComponent> createKey(int id)
		{
			return new DataParameter<ITextComponent>(id, this);
		}

		@Override
		public ITextComponent func_192717_a(ITextComponent p_192717_1_)
		{
			return p_192717_1_.createCopy();
		}
	};
	public static final DataSerializer<ItemStack> OPTIONAL_ITEM_STACK = new DataSerializer<ItemStack>()
	{
		@Override
		public void write(PacketBuffer buf, ItemStack value)
		{
			buf.writeItemStackToBuffer(value);
		}

		@Override
		public ItemStack read(PacketBuffer buf) throws IOException
		{
			return buf.readItemStackFromBuffer();
		}

		@Override
		public DataParameter<ItemStack> createKey(int id)
		{
			return new DataParameter<ItemStack>(id, this);
		}

		@Override
		public ItemStack func_192717_a(ItemStack p_192717_1_)
		{
			return p_192717_1_.copy();
		}
	};
	public static final DataSerializer<Optional<IBlockState>> OPTIONAL_BLOCK_STATE = new DataSerializer<Optional<IBlockState>>()
	{
		@Override
		public void write(PacketBuffer buf, Optional<IBlockState> value)
		{
			if (value.isPresent())
			{
				buf.writeVarIntToBuffer(Block.getStateId(value.get()));
			}
			else
			{
				buf.writeVarIntToBuffer(0);
			}
		}

		@Override
		public Optional<IBlockState> read(PacketBuffer buf) throws IOException
		{
			int i = buf.readVarIntFromBuffer();
			return i == 0 ? Optional.absent() : Optional.of(Block.getStateById(i));
		}

		@Override
		public DataParameter<Optional<IBlockState>> createKey(int id)
		{
			return new DataParameter<Optional<IBlockState>>(id, this);
		}

		@Override
		public Optional<IBlockState> func_192717_a(Optional<IBlockState> p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Boolean> BOOLEAN = new DataSerializer<Boolean>()
	{
		@Override
		public void write(PacketBuffer buf, Boolean value)
		{
			buf.writeBoolean(value.booleanValue());
		}

		@Override
		public Boolean read(PacketBuffer buf) throws IOException
		{
			return buf.readBoolean();
		}

		@Override
		public DataParameter<Boolean> createKey(int id)
		{
			return new DataParameter<Boolean>(id, this);
		}

		@Override
		public Boolean func_192717_a(Boolean p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Rotations> ROTATIONS = new DataSerializer<Rotations>()
	{
		@Override
		public void write(PacketBuffer buf, Rotations value)
		{
			buf.writeFloat(value.getX());
			buf.writeFloat(value.getY());
			buf.writeFloat(value.getZ());
		}

		@Override
		public Rotations read(PacketBuffer buf) throws IOException
		{
			return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
		}

		@Override
		public DataParameter<Rotations> createKey(int id)
		{
			return new DataParameter<Rotations>(id, this);
		}

		@Override
		public Rotations func_192717_a(Rotations p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<BlockPos> BLOCK_POS = new DataSerializer<BlockPos>()
	{
		@Override
		public void write(PacketBuffer buf, BlockPos value)
		{
			buf.writeBlockPos(value);
		}

		@Override
		public BlockPos read(PacketBuffer buf) throws IOException
		{
			return buf.readBlockPos();
		}

		@Override
		public DataParameter<BlockPos> createKey(int id)
		{
			return new DataParameter<BlockPos>(id, this);
		}

		@Override
		public BlockPos func_192717_a(BlockPos p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new DataSerializer<Optional<BlockPos>>()
	{
		@Override
		public void write(PacketBuffer buf, Optional<BlockPos> value)
		{
			buf.writeBoolean(value.isPresent());

			if (value.isPresent())
			{
				buf.writeBlockPos(value.get());
			}
		}

		@Override
		public Optional<BlockPos> read(PacketBuffer buf) throws IOException
		{
			return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readBlockPos());
		}

		@Override
		public DataParameter<Optional<BlockPos>> createKey(int id)
		{
			return new DataParameter<Optional<BlockPos>>(id, this);
		}

		@Override
		public Optional<BlockPos> func_192717_a(Optional<BlockPos> p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<EnumFacing> FACING = new DataSerializer<EnumFacing>()
	{
		@Override
		public void write(PacketBuffer buf, EnumFacing value)
		{
			buf.writeEnumValue(value);
		}

		@Override
		public EnumFacing read(PacketBuffer buf) throws IOException
		{
			return buf.readEnumValue(EnumFacing.class);
		}

		@Override
		public DataParameter<EnumFacing> createKey(int id)
		{
			return new DataParameter<EnumFacing>(id, this);
		}

		@Override
		public EnumFacing func_192717_a(EnumFacing p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<Optional<UUID>> OPTIONAL_UNIQUE_ID = new DataSerializer<Optional<UUID>>()
	{
		@Override
		public void write(PacketBuffer buf, Optional<UUID> value)
		{
			buf.writeBoolean(value.isPresent());

			if (value.isPresent())
			{
				buf.writeUuid(value.get());
			}
		}

		@Override
		public Optional<UUID> read(PacketBuffer buf) throws IOException
		{
			return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readUuid());
		}

		@Override
		public DataParameter<Optional<UUID>> createKey(int id)
		{
			return new DataParameter<Optional<UUID>>(id, this);
		}

		@Override
		public Optional<UUID> func_192717_a(Optional<UUID> p_192717_1_)
		{
			return p_192717_1_;
		}
	};
	public static final DataSerializer<NBTTagCompound> field_192734_n = new DataSerializer<NBTTagCompound>()
	{
		@Override
		public void write(PacketBuffer buf, NBTTagCompound value)
		{
			buf.writeNBTTagCompoundToBuffer(value);
		}

		@Override
		public NBTTagCompound read(PacketBuffer buf) throws IOException
		{
			return buf.readNBTTagCompoundFromBuffer();
		}

		@Override
		public DataParameter<NBTTagCompound> createKey(int id)
		{
			return new DataParameter<NBTTagCompound>(id, this);
		}

		@Override
		public NBTTagCompound func_192717_a(NBTTagCompound p_192717_1_)
		{
			return p_192717_1_.copy();
		}
	};

	public static void registerSerializer(DataSerializer<?> serializer)
	{
		REGISTRY.add(serializer);
	}

	@Nullable
	public static DataSerializer<?> getSerializer(int id)
	{
		return REGISTRY.get(id);
	}

	public static int getSerializerId(DataSerializer<?> serializer)
	{
		return REGISTRY.getId(serializer);
	}

	static
	{
		registerSerializer(BYTE);
		registerSerializer(VARINT);
		registerSerializer(FLOAT);
		registerSerializer(STRING);
		registerSerializer(TEXT_COMPONENT);
		registerSerializer(OPTIONAL_ITEM_STACK);
		registerSerializer(BOOLEAN);
		registerSerializer(ROTATIONS);
		registerSerializer(BLOCK_POS);
		registerSerializer(OPTIONAL_BLOCK_POS);
		registerSerializer(FACING);
		registerSerializer(OPTIONAL_UNIQUE_ID);
		registerSerializer(OPTIONAL_BLOCK_STATE);
		registerSerializer(field_192734_n);
	}
}
