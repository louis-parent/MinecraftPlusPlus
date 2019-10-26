package fr.minecraftpp.generator;

import java.util.Random;

import fr.minecraftpp.block.DynamicBlock;
import fr.minecraftpp.block.FlammabilityOf;
import fr.minecraftpp.block.HarvestLevel;
import fr.minecraftpp.block.ModBlock;
import fr.minecraftpp.block.ore.DynamicOre;
import fr.minecraftpp.block.ore.DynamicOreGem;
import fr.minecraftpp.crafting.RecipeBlock;
import fr.minecraftpp.crafting.RecipeItemFromBlock;
import fr.minecraftpp.crafting.furnace.FurnaceRecipe;
import fr.minecraftpp.generator.item.food.Food;
import fr.minecraftpp.item.DynamicItem;
import fr.minecraftpp.item.ModItem;
import fr.minecraftpp.language.ModLanguage;
import fr.minecraftpp.util.NameGenerator;
import net.minecraft.block.Block;

public class SimpleSet implements ISet
{
	private static final int QUANTITY_DROPPED_MAX = 5;

	protected Random rng;
	
	protected String name;
	
	protected DynamicOre ore;
	protected DynamicBlock block;
	protected DynamicItem item;
	
	public SimpleSet(Random rand)
	{
		this.rng = rand;
		this.name = NameGenerator.generateName(this.rng);
		
		this.item = new DynamicItem(this.name, DynamicItem.getRandomTextureId(this.rng));
		this.block = new DynamicBlock(this.name, DynamicBlock.getRandomTextureId(this.rng));
		this.ore = new DynamicOreGem(name, DynamicOre.getRandomTextureId(this.rng), item, this.rng.nextInt(QUANTITY_DROPPED_MAX) + 1, HarvestLevel.getRandomHarvestLevel(this.rng), this.rng.nextInt(2), this.rng.nextInt(3) + 2);
	
		this.register();
	}
	
	public void setupEffects()
	{
		this.setupFuel();
		this.setupEffect();
		this.setupLighter();
		this.setupEnchantCurrency();
		this.setupFood();
		
		this.setupGravity();
		this.setupLightLevel();
		this.setupAbsorbing();
		this.setupOpacity();
		this.setupSlipperiness();
		this.setupAcceleration();
		this.setupWalkDamage();
		this.setupFireable();
		
		this.setupBeacon();
	}
	
	public void addRecipes()
	{
		new RecipeBlock(this.item, this.block);
		new RecipeItemFromBlock(this.block, this.item);
		new FurnaceRecipe(this.ore, this.item.getAsStack(), (this.rng.nextInt(12) + 1) / 10);
	}

	private void setupFuel()
	{
		if(this.rng.nextInt(7) == 0)
		{
			int fuelAmount = 200 * (this.rng.nextInt(10) + 1);
			this.item.setFuelAmount(fuelAmount);
			this.block.setFuelAmount(fuelAmount * 10);
		}
	}
	
	private void setupEffect()
	{
		if(this.rng.nextInt(20) == 0)
		{
			this.item.setHasEffect(true);
		}
	}
	
	private void setupLighter()
	{
		if(this.rng.nextInt(10) == 0)
		{
			this.item.setPutsFire(true);
		}
	}
	
	private void setupEnchantCurrency()
	{
		if(this.rng.nextInt(8) == 0)
		{
			this.item.setEnchantCurrency(true);
		}
	}
	
	private void setupFood()
	{
		if(this.rng.nextInt(7) == 0)
		{
			int n = this.rng.nextInt(20) + 1;
			
			float amountFrac = 0.5F + (1 / (this.rng.nextInt(10) + 1));
			float saturationFrac = 0.5F + (1 / (this.rng.nextInt(10) + 1));

			this.item.setFood(new Food((int) (n * amountFrac), n * saturationFrac, this.rng.nextInt(5) == 0));
		}
	}
	
	private void setupGravity()
	{
		if(this.rng.nextInt(15) == 0)
		{
			this.block.setHasGravity(true);
		}
	}
	
	private void setupLightLevel()
	{
		if(this.rng.nextInt(7) == 0)
		{
			this.block.setLightLevel(1 / (this.rng.nextInt(2) + 1));
		}
	}
	
	private void setupAbsorbing()
	{
		if(this.rng.nextInt(50) == 0)
		{
			this.block.setAbsorbing(true);
		}
	}
	
	private void setupOpacity()
	{
		if(this.rng.nextInt(15) == 0)
		{
			this.block.setLightOpacity(this.rng.nextInt(255));
		}
	}
	
	private void setupSlipperiness()
	{
		if(this.rng.nextInt(15) == 0)
		{
			this.block.slipperiness = Block.BLOCK_SLIPERNESS + (this.rng.nextInt(5) / 10) - 0.2F;
		}
	}
	
	private void setupAcceleration()
	{
		if(this.rng.nextInt(17) == 0)
		{
			this.block.setAccelaration(1 + (this.rng.nextInt(11) / 10) - 0.5F);
		}
	}
	
	private void setupWalkDamage()
	{
		if(this.rng.nextInt(33) == 0)
		{
			this.block.setWalkDamage(this.rng.nextInt(3) + 1);
		}
	}
	
	private void setupFireable()
	{
		if(this.rng.nextInt(33) == 0)
		{
			this.block.setFlammability(FlammabilityOf.getRandomFlammability(this.rng));
		}
	}
	
	private void setupBeacon()
	{
		if(this.rng.nextInt(2) == 0)
		{
			this.item.setBeaconCurrency(true);
			this.block.setBeaconBase(true);
		}
	}
	
	private void register()
	{
		ModItem.setItemToRegister(this.item);
		ModLanguage.addTranslation(this.item);
		
		ModBlock.setBlockToRegister(this.block);
		ModLanguage.addTranslation(this.block);
		
		ModBlock.setBlockToRegister(this.ore);
		ModLanguage.addTranslation(this.ore);
	}
}