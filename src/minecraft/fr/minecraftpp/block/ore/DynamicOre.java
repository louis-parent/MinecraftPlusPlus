package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.enumeration.HarvestLevel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class DynamicOre extends BlockOre implements IDynamicBlock
{
	private static final int NUMBER_OF_TEXTURES = 4;

	private final String ID;
	private final int TEXTURE_ID;
	
	private int harvestLevel;
	private Rarity rarity;

	public DynamicOre(String typeName, int textureId, HarvestLevel harvestLevel)
	{
		super();
		
		this.ID = typeName + "Ore";
		this.setUnlocalizedName(this.ID);
		
		this.TEXTURE_ID = textureId;
		
		this.harvestLevel = harvestLevel.getHarvestLevel();
		
		this.rarity = Rarity.COMMON;
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
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
	@Override
	public int getRequiredHarvestLevel()
	{
		return this.harvestLevel;
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
	
	public static int getRandomTextureId(Random rng)
	{
		return rng.nextInt(NUMBER_OF_TEXTURES) + 1;
	}
}
