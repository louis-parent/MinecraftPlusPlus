package fr.minecraftpp.block;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class BlockScenarium extends BlockFalling
{	
    public static final PropertyBool EXPLODE = PropertyBool.create("explode");

	public BlockScenarium() 
	{
		super(Material.IRON);
		
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		
		this.setLightLevel(1.0F);
		
		this.setSoundType(SoundType.METAL);
		
		this.setUnlocalizedName("blockScenarium");
		
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
        entity.attackEntityFrom(DamageSource.cactus, 5.0F);
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
    public int getDustColor(IBlockState state)
    {
        return -16777216;
    }
}
