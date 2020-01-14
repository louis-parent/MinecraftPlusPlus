package net.minecraft.entity.item;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging
{
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.<ItemStack>createKey(EntityItemFrame.class, DataSerializers.OPTIONAL_ITEM_STACK);
	private static final DataParameter<Integer> ROTATION = EntityDataManager.<Integer>createKey(EntityItemFrame.class, DataSerializers.VARINT);

	/** Chance for this item frame's item to drop from the frame. */
	private float itemDropChance = 1.0F;

	public EntityItemFrame(World worldIn)
	{
		super(worldIn);
	}

	public EntityItemFrame(World worldIn, BlockPos p_i45852_2_, EnumFacing p_i45852_3_)
	{
		super(worldIn, p_i45852_2_);
		this.updateFacingWithBoundingBox(p_i45852_3_);
	}

	@Override
	protected void entityInit()
	{
		this.getDataManager().register(ITEM, ItemStack.EMPTY_ITEM_STACK);
		this.getDataManager().register(ROTATION, Integer.valueOf(0));
	}

	@Override
	public float getCollisionBorderSize()
	{
		return 0.0F;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable(source))
		{
			return false;
		}
		else if (!source.isExplosion() && !this.getDisplayedItem().isNotValid())
		{
			if (!this.world.isRemote)
			{
				this.dropItemOrSelf(source.getEntity(), false);
				this.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0F, 1.0F);
				this.setDisplayedItem(ItemStack.EMPTY_ITEM_STACK);
			}

			return true;
		}
		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public int getWidthPixels()
	{
		return 12;
	}

	@Override
	public int getHeightPixels()
	{
		return 12;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = 16.0D;
		d0 = d0 * 64.0D * getRenderDistanceWeight();
		return distance < d0 * d0;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	@Override
	public void onBroken(@Nullable Entity brokenEntity)
	{
		this.playSound(SoundEvents.ENTITY_ITEMFRAME_BREAK, 1.0F, 1.0F);
		this.dropItemOrSelf(brokenEntity, true);
	}

	@Override
	public void playPlaceSound()
	{
		this.playSound(SoundEvents.ENTITY_ITEMFRAME_PLACE, 1.0F, 1.0F);
	}

	public void dropItemOrSelf(@Nullable Entity entityIn, boolean p_146065_2_)
	{
		if (this.world.getGameRules().getBoolean("doEntityDrops"))
		{
			ItemStack itemstack = this.getDisplayedItem();

			if (entityIn instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) entityIn;

				if (entityplayer.capabilities.isCreativeMode)
				{
					this.removeFrameFromMap(itemstack);
					return;
				}
			}

			if (p_146065_2_)
			{
				this.entityDropItem(new ItemStack(Items.getItem(Items.ITEM_FRAME)), 0.0F);
			}

			if (!itemstack.isNotValid() && this.rand.nextFloat() < this.itemDropChance)
			{
				itemstack = itemstack.copy();
				this.removeFrameFromMap(itemstack);
				this.entityDropItem(itemstack, 0.0F);
			}
		}
	}

	/**
	 * Removes the dot representing this frame's position from the map when the
	 * item frame is broken.
	 */
	private void removeFrameFromMap(ItemStack stack)
	{
		if (!stack.isNotValid())
		{
			if (stack.getItem() == Items.getItem(Items.FILLED_MAP))
			{
				MapData mapdata = ((ItemMap) stack.getItem()).getMapData(stack, this.world);
				mapdata.mapDecorations.remove("frame-" + this.getEntityId());
			}

			stack.setItemFrame((EntityItemFrame) null);
		}
	}

	public ItemStack getDisplayedItem()
	{
		return this.getDataManager().get(ITEM);
	}

	public void setDisplayedItem(ItemStack stack)
	{
		this.setDisplayedItemWithUpdate(stack, true);
	}

	private void setDisplayedItemWithUpdate(ItemStack stack, boolean p_174864_2_)
	{
		if (!stack.isNotValid())
		{
			stack = stack.copy();
			stack.setStackSize(1);
			stack.setItemFrame(this);
		}

		this.getDataManager().set(ITEM, stack);
		this.getDataManager().setDirty(ITEM);

		if (!stack.isNotValid())
		{
			this.playSound(SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, 1.0F, 1.0F);
		}

		if (p_174864_2_ && this.hangingPosition != null)
		{
			this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.getBlock(Blocks.AIR));
		}
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (key.equals(ITEM))
		{
			ItemStack itemstack = this.getDisplayedItem();

			if (!itemstack.isNotValid() && itemstack.getItemFrame() != this)
			{
				itemstack.setItemFrame(this);
			}
		}
	}

	/**
	 * Return the rotation of the item currently on this frame.
	 */
	public int getRotation()
	{
		return this.getDataManager().get(ROTATION).intValue();
	}

	public void setItemRotation(int rotationIn)
	{
		this.setRotation(rotationIn, true);
	}

	private void setRotation(int rotationIn, boolean p_174865_2_)
	{
		this.getDataManager().set(ROTATION, Integer.valueOf(rotationIn % 8));

		if (p_174865_2_ && this.hangingPosition != null)
		{
			this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.getBlock(Blocks.AIR));
		}
	}

	public static void registerFixesItemFrame(DataFixer fixer)
	{
		fixer.registerWalker(FixTypes.ENTITY, new ItemStackData(EntityItemFrame.class, new String[] { "Item" }));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		if (!this.getDisplayedItem().isNotValid())
		{
			compound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
			compound.setByte("ItemRotation", (byte) this.getRotation());
			compound.setFloat("ItemDropChance", this.itemDropChance);
		}

		super.writeEntityToNBT(compound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");

		if (nbttagcompound != null && !nbttagcompound.hasNoTags())
		{
			this.setDisplayedItemWithUpdate(new ItemStack(nbttagcompound), false);
			this.setRotation(compound.getByte("ItemRotation"), false);

			if (compound.hasKey("ItemDropChance", 99))
			{
				this.itemDropChance = compound.getFloat("ItemDropChance");
			}
		}

		super.readEntityFromNBT(compound);
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand stack)
	{
		ItemStack itemstack = player.getHeldItem(stack);

		if (!this.world.isRemote)
		{
			if (this.getDisplayedItem().isNotValid())
			{
				if (!itemstack.isNotValid())
				{
					this.setDisplayedItem(itemstack);

					if (!player.capabilities.isCreativeMode)
					{
						itemstack.decreaseStackSize(1);
					}
				}
			}
			else
			{
				this.playSound(SoundEvents.ENTITY_ITEMFRAME_ROTATE_ITEM, 1.0F, 1.0F);
				this.setItemRotation(this.getRotation() + 1);
			}
		}

		return true;
	}

	public int getAnalogOutput()
	{
		return this.getDisplayedItem().isNotValid() ? 0 : this.getRotation() % 8 + 1;
	}
}
