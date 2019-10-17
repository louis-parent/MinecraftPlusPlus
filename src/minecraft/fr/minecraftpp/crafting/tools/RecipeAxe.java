package fr.minecraftpp.crafting.tools;

import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RecipeAxe extends RecipeTool
{
	private Blueprint symmetryBlueprint;
	
	public RecipeAxe(Item material, Item result)
	{
		super(material, result);
		
		this.symmetryBlueprint = new Blueprint(new Item[][] {{this.material, this.material, this.material}, {Items.EMPTY_ITEM, Items.STICK, this.material}, {Items.EMPTY_ITEM, Items.STICK, Items.EMPTY_ITEM}});
	}

	@Override
	protected Blueprint getBlueprint()
	{
		return new Blueprint(new Item[][] {{this.material, this.material, this.material}, {this.material, Items.STICK, Items.EMPTY_ITEM}, {Items.EMPTY_ITEM, Items.STICK, Items.EMPTY_ITEM}});
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		return super.matches(inv, world) || this.symmetryBlueprint.matches(inv);
	}
}
