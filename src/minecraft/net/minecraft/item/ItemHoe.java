package net.minecraft.item;

import com.google.common.collect.Multimap;

import fr.minecraftpp.inventory.EntityEquipmentSlot;
import fr.minecraftpp.inventory.EntityHandSlot;
import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHoe extends ItemTool
{
	public ItemHoe(IToolMaterial material)
	{
		super(material);
	}

	@Override
	@SuppressWarnings("incomplete-switch")

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
	{
		ItemStack itemstack = stack.getHeldItem(pos);

		if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState iblockstate = playerIn.getBlockState(worldIn);
			Block block = iblockstate.getBlock();

			if (hand != EnumFacing.DOWN && playerIn.getBlockState(worldIn.up()).getMaterial() == Material.AIR)
			{
				if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
				{
					this.setBlock(itemstack, stack, playerIn, worldIn, Blocks.FARMLAND.getDefaultState());
					return EnumActionResult.SUCCESS;
				}

				if (block == Blocks.DIRT)
				{
					switch (iblockstate.getValue(BlockDirt.VARIANT))
					{
						case DIRT:
							this.setBlock(itemstack, stack, playerIn, worldIn, Blocks.FARMLAND.getDefaultState());
							return EnumActionResult.SUCCESS;

						case COARSE_DIRT:
							this.setBlock(itemstack, stack, playerIn, worldIn, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
							return EnumActionResult.SUCCESS;
					}
				}
			}

			return EnumActionResult.PASS;
		}
	}

	/**
	 * Current implementations of this method in child classes do not use the
	 * entry argument beside ev. They just raise the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		return true;
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
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
	 * Returns the name of the material this tool is made from as it is declared
	 * in EnumToolMaterial (meaning diamond would return "EMERALD")
	 */
	public String getMaterialName()
	{
		return this.toolMaterial.toString();
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityHandSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.0D, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed - 4.0F, 0));
		}

		return multimap;
	}

	@Override
	public ToolType getToolType()
	{
		return ToolType.HOE;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		return 1.0F;
	}
}
