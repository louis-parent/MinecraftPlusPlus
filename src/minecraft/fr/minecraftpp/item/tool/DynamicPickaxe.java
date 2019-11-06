package fr.minecraftpp.item.tool;

import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.item.material.IToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class DynamicPickaxe extends ItemPickaxe implements IDynamicItem
{
	private final String ID;

	public DynamicPickaxe(String name, IToolMaterial material)
	{
		super(material);

		this.ID = name + "Pickaxe";
		this.setUnlocalizedName(this.ID);
	}

	@Override
	public String getID()
	{
		return this.ID;
	}

	@Override
	public String getTextureName()
	{
		return "pickaxe";
	}

	@Override
	public Item getItem()
	{
		return this;
	}

}
