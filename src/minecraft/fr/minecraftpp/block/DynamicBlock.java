package fr.minecraftpp.block;

import java.util.Random;

import fr.minecraftpp.color.Color;
import fr.minecraftpp.damageSource.ModDamageSource;
import fr.minecraftpp.enumeration.FlammabilityOf;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.enumeration.ModelType;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.item.material.IColored;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Rarity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DynamicBlock extends Block implements IDynamicBlock, IFalling, IAbsorbing, IColored
{
	private static final int NUMBER_OF_TEXTURES = 4;
	private final String ID;
	private final int TEXTURE_ID;
	private final DynamicItem item;

	private int harvestLevel;

	private boolean hasGravity;
	private boolean isAbsorbing;
	private float walkDamage;

	private FlammabilityOf flammability;
	private double accelaration;

	private boolean isBeaconBase;
	private int redstonePower;

	private int fuelAmount;
	private Rarity rarity;

	private DamageSource damageSource;

	public DynamicBlock(String typeName, int textureId, DynamicItem item, HarvestLevel harvestLevel)
	{
		super(Material.IRON, MapColor.EMERALD);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

		this.ID = typeName + "Block";
		this.setUnlocalizedName(this.ID);

		this.TEXTURE_ID = textureId;

		this.item = item;

		this.harvestLevel = harvestLevel.getHarvestLevel();

		this.hasGravity = false;
		this.setLightLevel(0);
		this.isAbsorbing = false;
		this.setLightOpacity(255);
		this.walkDamage = 0;
		this.flammability = FlammabilityOf.STONE;
		this.accelaration = 1;
		this.slipperiness = Block.BLOCK_SLIPPERINESS;
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
		this.damageSource = new ModDamageSource(this.ID);
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
		if (this.hasGravity)
		{
			IFalling.super.onBlockAdded(world, pos, state);
		}

		if (this.isAbsorbing)
		{
			this.tryAbsorb(world, pos, state);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos)
	{
		if (this.hasGravity)
		{
			IFalling.super.neighborChanged(state, world, pos, block, neighborPos);
		}

		if (this.isAbsorbing)
		{
			this.tryAbsorb(world, pos, state);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (this.hasGravity)
		{
			IFalling.super.updateTick(world, pos, state, rand);
		}
	}

	@Override
	public int tickRate(World world)
	{
		if (this.hasGravity)
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
		if (this.hasGravity)
		{
			IFalling.super.randomDisplayTick(state, world, pos, rand);
		}
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		if (this.walkDamage > 0)
		{
			entity.attackEntityFrom(this.damageSource, this.walkDamage);
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
	
	public float getWalkDamage() {
		return this.walkDamage;
	}

	public boolean isAbsorbing()
	{
		return this.isAbsorbing;
	}

	public boolean hasGravity()
	{
		return this.hasGravity;
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

	@Override
	public boolean hasColor()
	{
		return this.item.hasColor();
	}

	@Override
	public Color getColor()
	{
		return this.item.getColor();
	}

	@Override
	public ModelType getModelType()
	{
		return ModelType.COLORED;
	}

	@Override
	public int getDustColor(IBlockState state)
	{
		return this.item.getColor().asInt();
	}

	@Override
	public int getRequiredHarvestLevel()
	{
		return this.harvestLevel;
	}

	public void setHarvestLevel(HarvestLevel harvestLevel)
	{
		this.harvestLevel = harvestLevel.getHarvestLevel();
	}
	
	public HarvestLevel getHarvestLevel() {
		return HarvestLevel.values()[this.harvestLevel];
	}
}
