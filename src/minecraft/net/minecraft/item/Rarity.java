package net.minecraft.item;

import net.minecraft.util.text.TextFormatting;

public enum Rarity
{
	COMMON(TextFormatting.WHITE, "Common"), FAMILIAR(TextFormatting.GREEN, "Familiar"), UNCOMMON(TextFormatting.YELLOW, "Uncommon"), RARE(TextFormatting.AQUA, "Rare"), EPIC(TextFormatting.LIGHT_PURPLE, "Epic"), LEGENDARY(TextFormatting.DARK_RED, "Legendary");

	/**
	 * A decimal representation of the hex color codes of a the color assigned
	 * to this rarity type. (13 becomes d as in \247d which is light purple)
	 */
	public final TextFormatting rarityColor;

	/** Rarity name. */
	public final String rarityName;

	private Rarity(TextFormatting color, String name)
	{
		this.rarityColor = color;
		this.rarityName = name;
	}
	
	public Rarity next()
	{
		if(this == LEGENDARY)
		{
			return LEGENDARY;
		}
		else
		{
			return Rarity.values()[this.ordinal() + 1];			
		}
	}
}
