package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.block.HarvestLevel;
import fr.minecraftpp.block.ModBlock;
import fr.minecraftpp.generator.IDynamicBlock;
import fr.minecraftpp.language.ModLanguage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DynamicOre extends BlockOre implements IDynamicBlock
{
	
	private final String ID;
	private final int TEXTURE_ID;
	
	private Item itemDropped;
	private int minDropped;
	private int maxDropped;
	
	private int harvestLevel;
	private int minXpDrop;
	private int maxXpDrop;
	
	public DynamicOre(String typeName, int textureId, Item itemDropped, int quantityDropped, HarvestLevel harvestLevel, int minXpDrop, int maxXpDrop) 
	{
		this(typeName, textureId, itemDropped, quantityDropped, quantityDropped, harvestLevel, minXpDrop, maxXpDrop);
	}


	public DynamicOre(String typeName, int textureId, Item itemDropped, int minDropped, int maxDropped, HarvestLevel harvestLevel, int minXpDrop, int maxXpDrop)
	{
		super(); 
		
		this.ID = typeName + "Ore";
		this.setUnlocalizedName(this.ID);
		
		this.TEXTURE_ID = textureId;
		
		this.itemDropped = itemDropped;
		this.minDropped = minDropped;
		this.maxDropped = maxDropped;
		
		this.harvestLevel = harvestLevel.getHarvestLevel();
		
		this.minXpDrop = minXpDrop;
		this.maxXpDrop = maxXpDrop;
		
		// TODO Extract
		ModBlock.setBlockToRegister(this);
		ModLanguage.addTranslation(this);
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
		return "ore_";
	}

	@Override
	public Block getBlock()
	{
		return this;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.itemDropped;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return minDropped + random.nextInt((maxDropped + 1) - minDropped);
	}
	
	@Override
	public int getRequiredHarvestLevel()
	{
		return this.harvestLevel;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
		
		this.dropXpOnBlockBreak(world, pos,  MathHelper.getInt(world.rand, this.minXpDrop, this.maxXpDrop));
	}
}
