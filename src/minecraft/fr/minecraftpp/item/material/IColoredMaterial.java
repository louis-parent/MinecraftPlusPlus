package fr.minecraftpp.item.material;

import fr.minecraftpp.util.Color;

public interface IColoredMaterial
{
	public default boolean hasColor()
	{
		return false;
	}
	
	public default Color getColor()
	{
		return Color.WHITE;
	}
	
	public default int getRgbCondensed()
	{
		return this.getColor().asInt();
	}
}
