package fr.minecraftpp.variant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Variant
{
	private static Variant instance;
	
	private Random rand;
	private Map<Item, List<Item>> variants;
	
	private Variant(Random rand)
	{
		this.variants = new HashMap<Item, List<Item>>();
		this.rand = rand;
	}
	
	public void addVariant(Item orignal, Item variant)
	{
		if(!this.variants.containsKey(orignal))
		{
			this.variants.put(orignal, new ArrayList<Item>());
		}
		
		this.variants.get(orignal).add(variant);
	}
	
	public Item getRandomVariantOf(Item original)
	{
		if(this.variants.containsKey(original))
		{
			List<Item> variants = this.variants.get(original);
			return variants.get(this.rand.nextInt(variants.size()));
		}
		else
		{
			return original;
		}
	}
	
	public boolean isVariantOf(Item item, Item original)
	{
		return (this.variants.containsKey(original) && this.variants.get(original).contains(item)) || item == original;
	}
	
	public List<Item> getAllVariantsOf(Item original)
	{
		List<Item> allVariants = new ArrayList<Item>();
		allVariants.add(original);

		if(this.variants.containsKey(original))
		{
			allVariants.addAll(this.variants.get(original));
		}
		
		return allVariants;
	}
	
	public List<ItemStack> getAllStackVariantsOf(Item original)
	{
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for(Item item : this.getAllVariantsOf(original))
		{
			stacks.add(item.getAsStack());
		}
		
		return stacks;
	}
	
	public static void initInstance(Random rand)
	{
		instance = new Variant(rand);
	}
	
	public static Variant getInstance()
	{
		try
		{
			if(instance == null)
			{
				throw new NullPointerException("Instance not initialized");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return instance;
	}
}
