package fr.minecraftpp.block;

import java.util.Random;

import fr.minecraftpp.damageSource.ModDamageSource;
import fr.minecraftpp.generator.IDynamicBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DynamicBlock extends Block implements IDynamicBlock, IFalling, IAbsorbing
{
	private final String ID;
	private final int TEXTURE_ID;
	
	private boolean hasGravity;
	private boolean isAbsorbing;
	private float walkDamage;
	private FlammabilityOf flammability;
	private double accelaration;

	public DynamicBlock(String typeName, int textureId, boolean hasGravity, int lightPercentage, boolean isAbsorbing, int opacityPercentage, float walkDamage, FlammabilityOf flammability, double accelaration, float slipperiness, float hardness, float resistance)
	{
		super(Material.IRON, MapColor.EMERALD);
		
		this.setUnlocalizedName(typeName + "Block");
		this.ID = typeName + "Block";
		
		this.TEXTURE_ID = textureId;
		
		this.hasGravity = hasGravity;
		this.setLightLevel(lightPercentage / 100);
		this.isAbsorbing = isAbsorbing;
		this.setLightOpacity((opacityPercentage / 100) * 255);
		this.walkDamage = walkDamage;
		this.flammability = flammability;
		this.accelaration = accelaration;
		this.slipperiness = slipperiness;
		this.setHardness(hardness);
		this.setResistance(resistance);
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
}
