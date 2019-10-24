package fr.minecraftpp.block;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.damageSource.ModDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class BlockScenarium extends BlockFalling implements IAbsorbing
{
	public static final PropertyBool EXPLODE = PropertyBool.create("explode");
	
	public BlockScenarium()
	{
		super(Material.IRON);
				
		this.setHardness(5.0F);
		this.setResistance(10.0F);

		this.setLightLevel(1.0F);

		this.setSoundType(SoundType.METAL);

		this.setUnlocalizedName("scenariumBlock");

		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public FlammabilityOf getFlammability()
	{
		return FlammabilityOf.LEAVES;
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity)
	{
		super.onEntityWalk(world, pos, entity);

		entity.attackEntityFrom(ModDamageSource.scenarium, 5.0F);
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side)
	{
		return 15;
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess access, BlockPos pos)
	{
		return MapColor.MAGENTA;
	}

	@Override
	public double getAcceleration()
	{
		return 1.2D;
	}

	@Override
	public int getRequiredHarvestLevel()
	{
		return 2;
	}

	@Override
	public boolean isBaseForBeacon()
	{
		return true;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.tryAbsorb(worldIn, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos)
	{
		this.tryAbsorb(world, pos, state);
		super.neighborChanged(state, world, pos, block, neighborPos);
	}
}
