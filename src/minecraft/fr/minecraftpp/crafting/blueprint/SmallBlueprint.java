package fr.minecraftpp.crafting.blueprint;

import net.minecraft.item.Item;

public class SmallBlueprint extends AbstractBlueprint
{
	public static final int BLUEPRINT_SIZE = 2;

	public SmallBlueprint()
	{
		super(BLUEPRINT_SIZE);
	}

	@Override
	public int getMatrixSize()
	{
		return BLUEPRINT_SIZE;
	}
}
