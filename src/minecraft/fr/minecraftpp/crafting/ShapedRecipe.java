package fr.minecraftpp.crafting;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.crafting.blueprint.Blueprint;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

@Mod("Minecraftpp")
public class ShapedRecipe extends ModAbstractRecipe
{
	protected Blueprint blueprint;

	public ShapedRecipe(Item result)
	{
		super(result);
	}

	public ShapedRecipe(Blueprint blueprint, Item result)
	{
		this(result);
		this.blueprint = blueprint;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world)
	{
		return this.blueprint.matches(inv);
	}

	@Override
	public boolean checkIfCraftingMatrixSizeIsCorrect(int craftingMatrixWidth, int craftingMatrixHeight)
	{
		return craftingMatrixWidth >= this.blueprint.getWidth() && craftingMatrixHeight >= this.blueprint.getHeight();
	}

	@Override
	public NonNullList<Ingredient> getListOfIngredients()
	{
		return NonNullList.getInstanceWith(Ingredient.INGREDIENT_AIR, this.blueprint.toIngredients());
	}

	public void changeBlueprint(Blueprint newBlueprint)
	{
		this.blueprint = newBlueprint;
	}
}
