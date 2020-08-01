package fr.minecraftpp.item;

import java.util.Random;

import fr.minecraftpp.color.Color;
import fr.minecraftpp.item.food.IFood;
import fr.minecraftpp.item.food.NotFood;
import fr.minecraftpp.item.material.IColored;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamicItem extends Item implements IDynamicItem, IColored
{
	private static final int NUMBER_OF_TEXTURES = 6;
	private final String ID;
	private int textureId;
	private final Color color;

	private boolean hasEffect;
	private int fuelAmount;
	private boolean isEnchantCurrency;

	private boolean putsFire;
	private IFood food;

	private boolean isBeaconCurrency;

	public DynamicItem(String typeName, int textureId, Color color)
	{
		super();

		this.hasEffect = false;
		this.fuelAmount = 0;
		this.isEnchantCurrency = false;
		this.putsFire = false;
		this.food = new NotFood();

		this.setCreativeTab(CreativeTabs.MATERIALS);

		this.setUnlocalizedName(typeName);
		this.ID = typeName;
		this.textureId = textureId;

		this.color = color;

		this.isBeaconCurrency = false;
	}

	public void setHasEffect(boolean hasEffect)
	{
		this.hasEffect = hasEffect;
	}

	public void setFuelAmount(int fuelAmount)
	{
		this.fuelAmount = fuelAmount;
	}

	public void setEnchantCurrency(boolean isEnchantCurrency)
	{
		this.isEnchantCurrency = isEnchantCurrency;
	}

	public void setPutsFire(boolean putsFire)
	{
		this.putsFire = putsFire;
	}

	public void setFood(IFood food)
	{
		this.food = food;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return this.hasEffect;
	}

	@Override
	public boolean canSetFire()
	{
		return this.putsFire;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer stack, World player, BlockPos world, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY)
	{
		if (this.putsFire)
		{
			return LighterUse.onItemUse(stack, player, world, pos, hand, facing, hitX, hitY);
		}
		else
		{
			return EnumActionResult.PASS;
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		return this.food.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public int getItemUseDuration(ItemStack stack)
	{
		return this.food.getItemUseDuration(stack);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return this.food.getItemUseAction(stack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		return this.food.onItemRightClick(world, player, hand);
	}

	@Override
	public boolean isFood()
	{
		return this.food.isFood();
	}

	public IFood getFood()
	{
		return this.food;
	}

	@Override
	public int getBurnTime()
	{
		return this.fuelAmount;
	}

	@Override
	public boolean allowEnchanting()
	{
		return this.isEnchantCurrency;
	}

	@Override
	public String getTextureName()
	{
		return this.getTexturePrefix() + this.textureId;
	}

	private String getTexturePrefix()
	{
		return "item_";
	}

	@Override
	public String getID()
	{
		return this.ID;
	}

	@Override
	public Item getItem()
	{
		return this;
	}

	public void setBeaconCurrency(boolean isBeaconCurrency)
	{
		this.isBeaconCurrency = isBeaconCurrency;

		if (isBeaconCurrency)
		{
			TileEntityBeacon.paymentItems.add(this);
		}
		else
		{
			TileEntityBeacon.paymentItems.remove(this);
		}
	}

	public static int getRandomTextureId(Random rng)
	{
		return rng.nextInt(NUMBER_OF_TEXTURES - 1) + 1;
	}

	public void setTextureAsMetal()
	{
		this.textureId = NUMBER_OF_TEXTURES;
	}

	@Override
	public boolean hasColor()
	{
		return true;
	}

	@Override
	public Color getColor()
	{
		return this.color;
	}
}
