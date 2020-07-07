package fr.minecraftpp.randomizer.set;

import java.util.Random;

import fr.minecraftpp.color.DynamicColor;
import fr.minecraftpp.crafting.tools.RecipeAxe;
import fr.minecraftpp.crafting.tools.RecipeHoe;
import fr.minecraftpp.crafting.tools.RecipePickaxe;
import fr.minecraftpp.crafting.tools.RecipeShovel;
import fr.minecraftpp.crafting.tools.RecipeSword;
import fr.minecraftpp.item.material.IToolMaterial;
import fr.minecraftpp.item.tool.DynamicAxe;
import fr.minecraftpp.item.tool.DynamicHoe;
import fr.minecraftpp.item.tool.DynamicPickaxe;
import fr.minecraftpp.item.tool.DynamicShovel;
import fr.minecraftpp.item.tool.DynamicSword;
import fr.minecraftpp.manager.ModManager;
import fr.minecraftpp.variant.Variant;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class ToolSet implements ISet
{
	private Random rng;
	private String name;
	private IToolMaterial material;

	protected DynamicSword sword;
	protected DynamicPickaxe pickaxe;
	protected DynamicAxe axe;
	protected DynamicShovel shovel;
	protected DynamicHoe hoe;

	public ToolSet()
	{
	}

	public ToolSet(Random rand, String name, IToolMaterial material)
	{
		this.rng = rand;
		this.name = name;
		this.material = material;

		this.sword = new DynamicSword(this.name, this.material);
		this.pickaxe = new DynamicPickaxe(this.name, this.material);
		this.axe = new DynamicAxe(this.name, this.material);
		this.shovel = new DynamicShovel(this.name, this.material);
		this.hoe = new DynamicHoe(this.name, this.material);
	}

	@Override
	public void register()
	{
		ModManager.registerDynamic(this.sword);
		ModManager.registerDynamic(this.pickaxe);
		ModManager.registerDynamic(this.axe);
		ModManager.registerDynamic(this.shovel);
		ModManager.registerDynamic(this.hoe);
	}

	@Override
	public void addRecipes()
	{
		new RecipeSword(this.material.getRepairItem(), this.sword);
		new RecipePickaxe(this.material.getRepairItem(), this.pickaxe);
		new RecipeAxe(this.material.getRepairItem(), this.axe);
		new RecipeShovel(this.material.getRepairItem(), this.shovel);
		new RecipeHoe(this.material.getRepairItem(), this.hoe);
	}

	@Override
	public void setRarity(Rarity rarity)
	{
		this.sword.setRarity(rarity);
		this.pickaxe.setRarity(rarity);
		this.axe.setRarity(rarity);
		this.shovel.setRarity(rarity);
		this.hoe.setRarity(rarity);
	}

	@Override
	public void registerItemColors(ItemColors itemColors)
	{
		itemColors.registerItemColorHandler(new DynamicColor(this.sword), this.sword);
		itemColors.registerItemColorHandler(new DynamicColor(this.pickaxe), this.pickaxe);
		itemColors.registerItemColorHandler(new DynamicColor(this.axe), this.axe);
		itemColors.registerItemColorHandler(new DynamicColor(this.shovel), this.shovel);
		itemColors.registerItemColorHandler(new DynamicColor(this.hoe), this.hoe);
	}

	@Override
	public String getSetName()
	{
		return this.name;
	}

	@Override
	public void setupEffects()
	{
	}

	@Override
	public void registerBlockColors(BlockColors blockColors)
	{
	}

	public void setVariant(Item swordVariant, Item pickaxeVariant, Item axeVariant, Item shovelVariant, Item hoeVariant)
	{
		Variant variant = Variant.getInstance();

		variant.addVariant(swordVariant, this.sword);
		variant.addVariant(pickaxeVariant, this.pickaxe);
		variant.addVariant(axeVariant, this.axe);
		variant.addVariant(shovelVariant, this.shovel);
		variant.addVariant(hoeVariant, this.hoe);
	}

}
