package fr.minecraftpp.randomizer.set;

import java.util.Random;

import fr.minecraftpp.block.DynamicBlock;
import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.block.ore.DynamicOreGem;
import fr.minecraftpp.color.Color;
import fr.minecraftpp.color.DynamicColor;
import fr.minecraftpp.crafting.ShapelessRecipe;
import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import fr.minecraftpp.crafting.item.RecipeCompact;
import fr.minecraftpp.crafting.item.RecipeDecompact;
import fr.minecraftpp.enumeration.FlammabilityOf;
import fr.minecraftpp.enumeration.HarvestLevel;
import fr.minecraftpp.generation.OreRarity;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.item.food.Food;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.util.nameGenerator.Word;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class SimpleSet implements ISet
{
	private static final int REDSTONE_BLOCK_POWER = 15;
	private static final int BASE_AMOUNT_OF_BLUE_DYE = 6;
	private static final int BASE_AMOUNT_OF_REDSTONE = 4;
	private static final int QUANTITY_DROPPED_MAX = 5;

	protected Random rng;

	protected String name;

	protected DynamicOre ore;
	protected DynamicBlock block;
	protected DynamicItem item;

	private boolean isBlueDye;
	private boolean isRedstone;
	private boolean isCurrency;
	private boolean isFuel;
	private boolean isBeacon;
	private boolean isEnchantCurrency;

	private DynamicColor blockColor;
	private DynamicColor oreColor;
	
	private Rarity rarity;

	public SimpleSet(Random rand, OreRarity oreRarity)
	{
		this.rng = rand;
		this.name = Word.getWord(rand);

		this.item = new DynamicItem(this.name, DynamicItem.getRandomTextureId(this.rng), Color.getRandomColorImproved(this.rng));
		HarvestLevel randomHarvestLevel = HarvestLevel.getRandomHarvestLevel(this.rng);
		this.block = new DynamicBlock(this.name, DynamicBlock.getRandomTextureId(this.rng), this.item, randomHarvestLevel);
		this.ore = new DynamicOreGem(this.name, DynamicOre.getRandomTextureId(this.rng), oreRarity, this.item, randomHarvestLevel, this.rng.nextInt(2), this.rng.nextInt(3) + 2);

		this.isBlueDye = false;
		this.isRedstone = false;
		this.isCurrency = false;
		this.isFuel = false;
		this.isBeacon = false;
		this.isEnchantCurrency = false;

		this.blockColor = new DynamicColor(this.block);
		this.oreColor = new DynamicColor(this.ore);
		
		this.rarity = Rarity.COMMON;
	}

	@Override
	public void setupEffects()
	{
		this.setupEffect();
		this.setupLighter();
		this.setupFood();

		this.setupGravity();
		this.setupLightLevel();
		this.setupAbsorbing();
		this.setupOpacity();
		this.setupSlipperiness();
		this.setupAcceleration();
		this.setupWalkDamage();
		this.setupFireable();

		this.setupRedstone();
		this.setupCurrency();
		this.setupFuel();
		this.setupBeacon();
		this.setupEnchantCurrency();
		
		this.setRarity(this.rarity);
	}

	@Override
	public void register()
	{
		ModManager.registerDynamic(this.item);

		ModManager.registerDynamic(this.block);
		ModManager.registerDynamic(this.ore);
	}

	@Override
	public void addRecipes()
	{
		new RecipeCompact(this.item, this.block);
		new RecipeDecompact(this.block, this.item);
		new FurnaceRecipe(this.ore, this.item.getAsStack(), (this.rng.nextInt(12) + 1) / 10);

		if (this.isBlueDye)
		{
			int blueDyeQuantity = BASE_AMOUNT_OF_BLUE_DYE / this.ore.getAverageQuantityDropped();
			new ShapelessRecipe(new ItemStack(Items.DYE, blueDyeQuantity, EnumDyeColor.BLUE.getDyeDamage()), this.item);
		}

		if (this.isRedstone)
		{
			int redstoneQuantity = BASE_AMOUNT_OF_REDSTONE / this.ore.getAverageQuantityDropped();
			new ShapelessRecipe(new ItemStack(Items.REDSTONE, redstoneQuantity), this.item);
		}
	}

	public void setBlueDye()
	{
		this.isBlueDye = true;
	}

	public void setRedstone()
	{
		this.isRedstone = true;
		this.rarity = this.rarity.next();
	}

	public void setCurrency()
	{
		this.isCurrency = true;
		this.rarity = this.rarity.next();
	}

	public void setFuel()
	{
		this.isFuel = true;
	}

	public void setBeacon()
	{
		this.isBeacon = true;
	}

	public void setEnchantCurrency()
	{
		this.isEnchantCurrency = true;
		this.rarity = this.rarity.next();
	}
	
	public void setCoal() 
	{
		this.setHarvestLevel(HarvestLevel.WOOD);
	}
	
	public void setIron() 
	{
		this.setHarvestLevel(HarvestLevel.STONE);
		this.rarity = this.rarity.next();
	}
	
	public void setDiamond() 
	{
		this.setHarvestLevel(HarvestLevel.IRON);
		this.rarity = this.rarity.next().next();
	}

	private void setHarvestLevel(HarvestLevel harvestLevel)
	{
		this.ore.setHarvestLevel(harvestLevel);
		this.block.setHarvestLevel(harvestLevel);
	}	

	@Override
	public void setRarity(Rarity rarity)
	{
		this.item.setRarity(rarity);
		this.block.setRarity(rarity);
		this.ore.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		itemColors.registerItemColorHandler(new DynamicColor(this.item), this.item);
		itemColors.registerItemColorHandler(this.blockColor, this.block);
		itemColors.registerItemColorHandler(this.oreColor, this.ore);
	}

	@Override
	public void registerBlockColors(BlockColors blockColors)
	{
		blockColors.registerBlockColorHandler(this.blockColor, this.block);
		blockColors.registerBlockColorHandler(this.oreColor, this.ore);
	}
	
	@Override
	public String getSetName()
	{
		return this.name;
	}

	private void setupFuel()
	{
		if (this.isFuel)
		{
			int fuelAmount = 200 * (this.rng.nextInt(10) + 1);
			this.item.setFuelAmount(fuelAmount);
			this.block.setFuelAmount(fuelAmount * 10);
		}
	}

	private void setupEffect()
	{
		if (this.rng.nextInt(20) == 0)
		{
			this.item.setHasEffect(true);
		}
	}

	private void setupLighter()
	{
		if (this.rng.nextInt(10) == 0)
		{
			this.item.setPutsFire(true);
		}
	}

	private void setupEnchantCurrency()
	{
		if (this.isEnchantCurrency)
		{
			if (this.ore instanceof DynamicOreGem)
			{
				((DynamicOreGem) this.ore).increaseDrop();
			}
			
			this.item.setEnchantCurrency(true);
		}
	}

	private void setupFood()
	{
		if (this.rng.nextInt(7) == 0)
		{
			int n = this.rng.nextInt(20) + 1;

			float amountFrac = 0.5F + (1 / (this.rng.nextInt(10) + 1));
			float saturationFrac = 0.5F + (1 / (this.rng.nextInt(10) + 1));

			this.item.setFood(new Food((int) (n * amountFrac), n * saturationFrac, this.rng.nextInt(5) == 0));
		}
	}

	private void setupGravity()
	{
		if (this.rng.nextInt(15) == 0)
		{
			this.block.setHasGravity(true);
		}
	}

	private void setupLightLevel()
	{
		if (this.rng.nextInt(7) == 0)
		{
			this.block.setLightLevel(1 / (this.rng.nextInt(2) + 1));
		}
	}

	private void setupAbsorbing()
	{
		if (this.rng.nextInt(50) == 0)
		{
			this.block.setAbsorbing(true);
		}
	}

	private void setupOpacity()
	{
		if (this.rng.nextInt(15) == 0)
		{
			this.block.setLightOpacity(this.rng.nextInt(255));
		}
	}

	private void setupSlipperiness()
	{
		if (this.rng.nextInt(15) == 0)
		{
			this.block.slipperiness = Block.BLOCK_SLIPERNESS + (this.rng.nextInt(5) / 10) - 0.2F;
		}
	}

	private void setupAcceleration()
	{
		if (this.rng.nextInt(17) == 0)
		{
			this.block.setAccelaration(1 + (this.rng.nextInt(11) / 10) - 0.5F);
		}
	}

	private void setupWalkDamage()
	{
		if (this.rng.nextInt(33) == 0)
		{
			this.block.setWalkDamage(this.rng.nextInt(3) + 1);
		}
	}

	private void setupFireable()
	{
		if (this.rng.nextInt(33) == 0)
		{
			this.block.setFlammability(FlammabilityOf.getRandomFlammability(this.rng));
		}
	}

	private void setupBeacon()
	{
		if (this.isBeacon)
		{
			this.item.setBeaconCurrency(true);
			this.block.setBeaconBase(true);
		}
	}

	private void setupCurrency()
	{
		if (this.isCurrency)
		{			
			EntityVillager.setMoney(this.item);
		}
	}

	private void setupRedstone()
	{
		if (this.isRedstone)
		{
			if (this.ore instanceof DynamicOreGem)
			{
				((DynamicOreGem) this.ore).setPoweredOre(true);
			}
			
			this.block.setRedstonePower(REDSTONE_BLOCK_POWER);
		}
	}
}
