package net.minecraft.item;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import fr.minecraftpp.inventory.EntityArmorSlot;
import fr.minecraftpp.item.material.IArmorMaterial;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemArmor extends Item
{
	public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem()
	{
		@Override
		protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
		{
			ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);
			return itemstack.isNotValid() ? super.dispenseStack(source, stack) : itemstack;
		}
	};

	public final EntityArmorSlot armorType;

	/** The EnumIArmorMaterial used for this ItemArmor */
	private final IArmorMaterial material;

	public static ItemStack dispenseArmor(IBlockSource blockSource, ItemStack stack)
	{
		BlockPos blockpos = blockSource.getBlockPos().offset(blockSource.getBlockState().getValue(BlockDispenser.FACING));
		List<EntityLivingBase> list = blockSource.getWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.and(EntitySelectors.NOT_SPECTATING, new EntitySelectors.ArmoredMob(stack)));

		if (list.isEmpty())
		{
			return ItemStack.EMPTY_ITEM_STACK;
		}
		else
		{
			EntityLivingBase entitylivingbase = list.get(0);
			EntityArmorSlot entityequipmentslot = (EntityArmorSlot) EntityLiving.getSlotForItemStack(stack);
			ItemStack itemstack = stack.splitStack(1);
			entitylivingbase.setItemStackToSlot(entityequipmentslot, itemstack);

			if (entitylivingbase instanceof EntityLiving)
			{
				((EntityLiving) entitylivingbase).setDropChance(entityequipmentslot, 2.0F);
			}

			return stack;
		}
	}

	public ItemArmor(IArmorMaterial material, EntityArmorSlot equipmentSlot)
	{
		this.material = material;
		this.armorType = equipmentSlot;
		this.setMaxDamage(material.getDurability(equipmentSlot));
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.COMBAT);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
	}

	/**
	 * Gets the equipment slot of this armor piece (formerly known as armor
	 * type)
	 */
	public EntityArmorSlot getEquipmentSlot()
	{
		return this.armorType;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return this.material.getEnchantability();
	}

	/**
	 * Return the armor material for this armor item.
	 */
	public IArmorMaterial getArmorMaterial()
	{
		return this.material;
	}

	/**
	 * Return whether the specified armor ItemStack has a color.
	 */
	public boolean hasColor(ItemStack stack)
	{
		if (this.material != ArmorMaterial.LEATHER)
		{
			return false;
		}
		else
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();
			return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
		}
	}

	/**
	 * Return the color for the specified armor ItemStack.
	 */
	public int getColor(ItemStack stack)
	{
		if (this.material != ArmorMaterial.LEATHER)
		{
			return 16777215;
		}
		else
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound != null)
			{
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

				if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
				{
					return nbttagcompound1.getInteger("color");
				}
			}

			return 10511680;
		}
	}

	/**
	 * Remove the color from the specified armor ItemStack.
	 */
	public void removeColor(ItemStack stack)
	{
		if (this.material == ArmorMaterial.LEATHER)
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound != null)
			{
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

				if (nbttagcompound1.hasKey("color"))
				{
					nbttagcompound1.removeTag("color");
				}
			}
		}
	}

	/**
	 * Sets the color of the specified armor ItemStack
	 */
	public void setColor(ItemStack stack, int color)
	{
		if (this.material != ArmorMaterial.LEATHER)
		{
			throw new UnsupportedOperationException("Can't dye non-leather!");
		}
		else
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound == null)
			{
				nbttagcompound = new NBTTagCompound();
				stack.setTagCompound(nbttagcompound);
			}

			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (!nbttagcompound.hasKey("display", 10))
			{
				nbttagcompound.setTag("display", nbttagcompound1);
			}

			nbttagcompound1.setInteger("color", color);
		}
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return this.material.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn)
	{
		ItemStack itemstack = worldIn.getHeldItem(playerIn);
		EntityArmorSlot entityequipmentslot = (EntityArmorSlot) EntityLiving.getSlotForItemStack(itemstack);
		ItemStack itemstack1 = worldIn.getItemStackFromSlot(entityequipmentslot);

		if (itemstack1.isNotValid())
		{
			worldIn.setItemStackToSlot(entityequipmentslot, itemstack.copy());
			itemstack.setStackSize(0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityArmorSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == this.armorType)
		{
			multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(equipmentSlot.getModifierUUID(), "Armor modifier", this.getDamageReduceAmount(), 0));
			multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getAttributeUnlocalizedName(), new AttributeModifier(equipmentSlot.getModifierUUID(), "Armor toughness", this.getToughness(), 0));
		}

		return multimap;
	}

	public int getDamageReduceAmount()
	{
		return this.material.getDamageReductionAmount(this.armorType);
	}

	public float getToughness()
	{
		return this.material.getToughness();
	}
}
