package fr.minecraftpp.block;

import java.util.Random;

import fr.minecraftpp.damageSource.ModDamageSource;
import fr.minecraftpp.generator.IDynamicBlock;
import fr.minecraftpp.language.ModLanguage;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Rarity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DynamicBlock extends Block implements IDynamicBlock, IFalling, IAbsorbing
{
	private static final int NUMBER_OF_TEXTURES = 4;
	private final String ID;
	private final int TEXTURE_ID;
	
	private boolean hasGravity;
	private boolean isAbsorbing;
	private float walkDamage;

	private FlammabilityOf flammability;
	private double accelaration;
	
	private boolean isBeaconBase;
	private int redstonePower;
	
	private int fuelAmount;
	private Rarity rarity;

	public DynamicBlock(String typeName, int textureId)
	{
		super(Material.IRON, MapColor.EMERALD);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		this.ID = typeName + "Block";
		this.setUnlocalizedName(this.ID);
		
		this.TEXTURE_ID = textureId;
		
		this.hasGravity = false;
		this.setLightLevel(0);
		this.isAbsorbing = false;
		this.setLightOpacity(255);
		this.walkDamage = 0;
		this.flammability = FlammabilityOf.STONE;
		this.accelaration = 0;
		this.slipperiness = Block.BLOCK_SLIPERNESS;
		this.setHardness(5.0F);
		this.setResistance(10.0F); 
		
		this.isBeaconBase = false;
		this.redstonePower = 0;
		
		this.fuelAmount = 0;
		this.rarity = Rarity.COMMON;
	}
	
	public void setHasGravity(boolean hasGravity)
	{
		this.hasGravity = hasGravity;
	}
	
	public void setAbsorbing(boolean isAbsorbing)
	{
		this.isAbsorbing = isAbsorbing;
	}
	
	public void setWalkDamage(float walkDamage)
	{
		this.walkDamage = walkDamage;
	}
	
	public void setFlammability(FlammabilityOf flammability)
	{
		this.flammability = flammability;
	}
	
	public void setAccelaration(double accelaration)
	{
		this.accelaration = accelaration;
	}

	@Override
	public String getID()
	{
		return this.ID;
	}

	@Override
	public String getTextureName()
	{
		return this.getTexturePrefix() + this.TEXTURE_ID;
	}

	private String getTexturePrefix()
	{
		return "block_";
	}

	@Override
	public Block getBlock()
	{
		return this;
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		if(this.hasGravity)
		{
			IFalling.super.onBlockAdded(world, pos, state);
		}
		
		if(this.isAbsorbing)
		{
			this.tryAbsorb(world, pos, state);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos)
	{
		if(this.hasGravity)
		{
			IFalling.super.neighborChanged(state, world, pos, block, neighborPos);			
		}
		
		if(this.isAbsorbing)
		{
			this.tryAbsorb(world, pos, state);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(this.hasGravity)
		{
			IFalling.super.updateTick(world, pos, state, rand);			
		}
	}

	@Override
	public int tickRate(World world)
	{
		if(this.hasGravity)
		{
			return IFalling.super.tickRate(world);
		}
		else
		{
			return super.tickRate(world);
		}
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if(this.hasGravity)
		{
			IFalling.super.randomDisplayTick(state, world, pos, rand);
		}
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);
		
		if(this.walkDamage > 0)
		{
			entity.attackEntityFrom(new ModDamageSource(this.ID), this.walkDamage);
		}
	}
	
	@Override
	public FlammabilityOf getFlammability()
	{
		return this.flammability;
	}
	
	@Override
	public double getAcceleration()
	{
		return this.accelaration;
	}
	
	@Override
	public boolean isBaseForBeacon()
	{
		return this.isBeaconBase;
	}

	public void setBeaconBase(boolean isBeaconBase)
	{
		this.isBeaconBase = isBeaconBase;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return this.redstonePower > 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return this.redstonePower;
	}

	public void setRedstonePower(int redstonePower)
	{
		this.redstonePower = redstonePower;
	}

	public static int getRandomTextureId(Random rng)
	{
		return rng.nextInt(NUMBER_OF_TEXTURES) + 1;
	}

	public void setRarity(Rarity rarity)
	{
		this.rarity = rarity;
	}
	
	@Override
	public Rarity getRarity()
	{
		return this.rarity;
	}

	public void setFuelAmount(int fuelAmount)
	{
		this.fuelAmount = fuelAmount;
	}
	
	@Override
	public int getBurnTime()
	{
		return this.fuelAmount;
	}
}
