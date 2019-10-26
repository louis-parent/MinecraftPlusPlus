package net.minecraft.item;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.item.LighterUse;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item
{
	public ItemFlintAndSteel()
	{
		this.maxStackSize = 1;
		this.setMaxDamage(64);
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return LighterUse.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Mod("Minecraftpp")
	@Override
	public boolean canSetFire()
	{
		return true;
	}
}
