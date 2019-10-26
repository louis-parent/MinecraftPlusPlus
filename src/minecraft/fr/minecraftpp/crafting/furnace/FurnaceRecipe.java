package fr.minecraftpp.crafting.furnace;

import fr.minecraftpp.manager.crafting.ModFurnaceRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FurnaceRecipe
{
	private ItemStack input;
	private ItemStack output;
	private float experience;
	
	public FurnaceRecipe(Block input, ItemStack output, float experience)
	{
		this(Item.getItemFromBlock(input), output, experience);
	}
	
	public FurnaceRecipe(Item input, ItemStack output, float experience)
	{
		this(input.getAsStack(), output, experience);
	}
	
	public FurnaceRecipe(ItemStack input, ItemStack output, float experience)
	{
		this.input = input;
		this.output = output;
		this.experience = experience;
		
		ModFurnaceRecipes.addSmelting(this);
	}

	public ItemStack getInput()
	{
		return input;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public float getExperience()
	{
		return experience;
	}
	
	
}
