package fr.minecraftpp.item;

import fr.minecraftpp.generator.IDynamicItem;
import fr.minecraftpp.generator.item.LighterUse;
import fr.minecraftpp.generator.item.food.IFood;
import fr.minecraftpp.generator.item.food.NotFood;
import fr.minecraftpp.language.ModLanguage;
import fr.minecraftpp.renderer.ModModelManager;
import fr.minecraftpp.renderer.ModRenderItem;
import fr.minecraftpp.util.NameGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamicItem extends Item implements IDynamicItem
{
	private final String ID;
	private final int TEXTURE_ID;
	
	private boolean hasEffect;
	private int fuelAmount;
	private boolean isEnchantCurrency;
	
	private boolean putsFire;
	private IFood food;
	
	public DynamicItem(int textureId, boolean hasEffect, int fuelAmount, boolean isEnchantCurrency, boolean putsFire)
	{
		this(textureId, hasEffect, fuelAmount, isEnchantCurrency, putsFire, new NotFood());
	}

	public DynamicItem(int textureId, boolean hasEffect, int fuelAmount, boolean isEnchantCurrency, boolean putsFire, IFood food)
	{
		super();
				
		this.hasEffect = hasEffect;
		this.fuelAmount = fuelAmount;
		this.isEnchantCurrency = isEnchantCurrency;
		this.putsFire = putsFire;
		this.food = food;
		
		this.setCreativeTab(CreativeTabs.MATERIALS);
		
		String name = NameGenerator.generateName();
		this.setUnlocalizedName(name);
		this.ID = name;
		
		this.TEXTURE_ID = textureId;
		
		// TODO extract
		ModItem.setItemToRegister(this);
		ModLanguage.addTranslation(this);
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
		if(this.putsFire)
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
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return this.food.getMaxItemUseDuration(stack);
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
		return this.getTexturePrefix() + this.TEXTURE_ID;
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
}
