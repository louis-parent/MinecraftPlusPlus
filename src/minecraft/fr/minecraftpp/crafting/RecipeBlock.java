package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import fr.minecraftpp.item.tool.IToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class RecipeBlock extends ShapedRecipe
{

	private Item material;

	public RecipeBlock(Item material, Block result)
	{
		super(Item.getItemFromBlock(result));
		
		this.material = material;
		this.changeBlueprint(this.getBlueprint());
		
		new RecipeItemFromBlock(result, material);
	}

	private Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] {{this.material, this.material, this.material}, {this.material, this.material, this.material}, {this.material, this.material, this.material}});
	}
}
