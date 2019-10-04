package fr.minecraftpp.block;

import java.util.Random;

import fr.minecraftpp.item.ModItems;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockScenariteOre extends BlockOre
{
	public BlockScenariteOre()
	{
		super();
		
		this.setUnlocalizedName("scenariteOre");
		this.setHardness(20.0F);
		this.setResistance(420.0F);
		this.setSoundType(SoundType.ANVIL);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{		
		return rand.nextInt(4) == 0 ? ModItems.SCENARIUM : Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(Random random)
    {
		return 1;
    }
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) 
	{
		if (!worldIn.isRemote)
        {
            int i = this.quantityDroppedWithBonus(fortune, worldIn.rand);

            for (int j = 0; j < i; ++j)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    Item item = this.getItemDropped(state, worldIn.rand, fortune);

                    if (item != Items.EMPTY_ITEM)
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(item, 1, this.damageDropped(state)));
                    }
                    
                    if (item != Item.getItemFromBlock(this))
                    {
                        this.dropXpOnBlockBreak(worldIn, pos, MathHelper.getInt(worldIn.rand, 4, 8));
                    }
                }
            }
        }
	}
	
}
