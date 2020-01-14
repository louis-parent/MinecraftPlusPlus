package fr.minecraftpp.block.ore;

import java.util.Random;

import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.color.Color;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.enumeration.ModelType;
import fr.minecraftpp.generation.OreRarity;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.item.material.IColored;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class DynamicOre extends BlockOre implements IDynamicBlock, IColored
{
	private static final int NUMBER_OF_TEXTURES = 4;

	private final String ID;
	private final int TEXTURE_ID;
	protected DynamicItem item;
 
	private OreRarity oreRarity;
	
	private int harvestLevel;
	private Rarity rarity;

	public DynamicOre(String typeName, int textureId, OreRarity rarity, HarvestLevel harvestLevel, DynamicItem item)
	{
		super();

		this.ID = typeName + "Ore";
		this.setUnlocalizedName(this.ID);

		this.TEXTURE_ID = textureId;

		this.item = item;

		this.oreRarity = rarity;
		
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

	public int getAverageQuantityDropped()
	{
		return 1;
	}

	@Override
	public ModelType getModelType()
	{
		return ModelType.OVERLAY;
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
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	public void setHarvestLevel(HarvestLevel harvestLevel)
	{
		this.harvestLevel = harvestLevel.getHarvestLevel();
	}
	
	@Override
	public void decorate(BiomeDecorator decorator, World world, Random rand)
	{
		decorator.uniformOreGeneration(world, rand, this.oreRarity.getVeinAmount(), new WorldGenMinable(this.getDefaultState(), this.oreRarity.getVeinDensity(), BlockMatcher.forBlock(Blocks.getBlock(Blocks.STONE))), 0, this.oreRarity.getMaxHeight());
	}
}
