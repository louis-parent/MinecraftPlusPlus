package fr.minecraftpp.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemScenarium extends ItemFood
{

	public ItemScenarium()
	{
		super(10, 1.2F, true);
		
		this.setUnlocalizedName("scenarium");
		this.setCreativeTab(CreativeTabs.MATERIALS);
		
		TileEntityBeacon.paymentItems.add(this);
		
		//EntityVillager.setMoney(this);
	}
	
	@Override
	public int getBurnTime()
	{
		return 1600;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        pos = pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(pos, facing, itemstack))
        {
            if (world.getBlockState(pos).getMaterial() == Material.AIR)
            {
                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
                
                if(!player.isCreative())
                {
                    itemstack.decreaseStackSize(1);
                }
                
                if (player instanceof EntityPlayerMP)
                {
                    CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)player, pos, itemstack);
                }
                
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }

        } 
        else 
        {
            return EnumActionResult.FAIL;
        }
    }
	
	@Override
	public boolean canSetFire()
	{
		return true;
	}
	
	@Override
	public boolean allowEnchanting()
	{
		return true;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}
}
