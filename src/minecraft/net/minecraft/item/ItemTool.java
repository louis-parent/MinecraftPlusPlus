package net.minecraft.item;

import com.google.common.collect.Multimap;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.enumeration.ToolType;
import fr.minecraftpp.inventory.EntityEquipmentSlot;
import fr.minecraftpp.inventory.EntityHandSlot;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemTool extends Item
{
	protected float efficiencyOnProperMaterial;

	/** Damage versus entities. */
	protected float damageVsEntity;
	protected float attackSpeed;

	/** The material this tool is made from. */
	protected IToolMaterial toolMaterial;

	protected ItemTool(float attackDamage, float attackSpeed, IToolMaterial material)
	{
		this.efficiencyOnProperMaterial = 4.0F;
		this.toolMaterial = material;
		this.maxStackSize = 1;
		this.setMaxDamage(material.getMaxUses());
		this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
		this.damageVsEntity = attackDamage + material.getDamageVsEntity(this.getToolType());
		this.attackSpeed = attackSpeed + material.getAttackSpeed(this.getToolType());
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

	protected ItemTool(IToolMaterial materialIn)
	{
		this(0.0F, 0.0F, materialIn);
	}

	@Mod("Minecraftpp")
	public abstract ToolType getToolType();

	@Override
	@Mod("Minecraftpp")
	public abstract float getStrVsBlock(ItemStack stack, IBlockState state);

	/**
	 * Current implementations of this method in child classes do not use the
	 * entry argument beside ev. They just raise the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(2, attacker);
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger
	 * the "Use Item" statistic.
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D)
		{
			stack.damageItem(1, entityLiving);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	public boolean isFull3D()
	{
		return true;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return this.getToolMaterial().getEnchantability();
	}

	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName()
	{
		return this.getToolMaterial().toString();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return this.getToolMaterial().getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityHandSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.damageVsEntity, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
		}

		return multimap;
	}

	public IToolMaterial getToolMaterial()
	{
		return toolMaterial;
	}
}
