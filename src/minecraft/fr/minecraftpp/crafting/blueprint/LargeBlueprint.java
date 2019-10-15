package fr.minecraftpp.crafting.blueprint;

import net.minecraft.item.Item;

public class LargeBlueprint extends AbstractBlueprint
{
	public static final int BLUEPRINT_SIZE = 3;

	public LargeBlueprint()
	{
		super(BLUEPRINT_SIZE);
	}

	@Override
	public int getMatrixSize()
	{
		return BLUEPRINT_SIZE;
	}
}
