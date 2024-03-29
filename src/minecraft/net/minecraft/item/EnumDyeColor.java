package net.minecraft.item;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public enum EnumDyeColor implements IStringSerializable
{
	WHITE(0, 15, "white", "white", 16383998, TextFormatting.WHITE), ORANGE(1, 14, "orange", "orange", 16351261, TextFormatting.GOLD), MAGENTA(2, 13, "magenta", "magenta", 13061821, TextFormatting.AQUA), LIGHT_BLUE(3, 12, "light_blue", "lightBlue", 3847130, TextFormatting.BLUE), YELLOW(4, 11, "yellow", "yellow", 16701501, TextFormatting.YELLOW), LIME(5, 10, "lime", "lime", 8439583, TextFormatting.GREEN), PINK(6, 9, "pink", "pink", 15961002, TextFormatting.LIGHT_PURPLE), GRAY(7, 8, "gray", "gray", 4673362, TextFormatting.DARK_GRAY), SILVER(8, 7, "silver", "silver", 10329495, TextFormatting.GRAY), CYAN(9, 6, "cyan", "cyan", 1481884, TextFormatting.DARK_AQUA), PURPLE(10, 5, "purple", "purple", 8991416, TextFormatting.DARK_PURPLE), BLUE(11, 4, "blue", "blue", 3949738, TextFormatting.DARK_BLUE), BROWN(12, 3, "brown", "brown", 8606770, TextFormatting.GOLD), GREEN(13, 2, "green", "green", 6192150, TextFormatting.DARK_GREEN), RED(14, 1, "red", "red", 11546150, TextFormatting.DARK_RED), BLACK(15, 0, "black", "black", 1908001, TextFormatting.BLACK);

	private static final EnumDyeColor[] META_LOOKUP = new EnumDyeColor[values().length];
	private static final EnumDyeColor[] DYE_DMG_LOOKUP = new EnumDyeColor[values().length];
	private final int meta;
	private final int dyeDamage;
	private final String name;
	private final String unlocalizedName;
	private final int rgbCondensed;
	private final float[] rgbIntensity;
	private final TextFormatting chatColor;

	private EnumDyeColor(int meta, int damage, String name, String unlocalizedName, int rgbCondensed, TextFormatting chatColor)
	{
		this.meta = meta;
		this.dyeDamage = damage;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		this.rgbCondensed = rgbCondensed;
		this.chatColor = chatColor;
		int i = (rgbCondensed & 16711680) >> 16;
		int j = (rgbCondensed & 65280) >> 8;
		int k = (rgbCondensed & 255) >> 0;
		this.rgbIntensity = new float[] { i / 255.0F, j / 255.0F, k / 255.0F };
	}

	public int getMetadata()
	{
		return this.meta;
	}

	public int getDyeDamage()
	{
		return this.dyeDamage;
	}

	public String getUnlocalizedName()
	{
		return this.unlocalizedName;
	}

	public int asInt()
	{
		return this.rgbCondensed;
	}

	public float[] getRGB()
	{
		return this.rgbIntensity;
	}

	public static EnumDyeColor byDyeDamage(int damage)
	{
		if (damage < 0 || damage >= DYE_DMG_LOOKUP.length)
		{
			damage = 0;
		}

		return DYE_DMG_LOOKUP[damage];
	}

	public static EnumDyeColor byMetadata(int meta)
	{
		if (meta < 0 || meta >= META_LOOKUP.length)
		{
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	@Override
	public String toString()
	{
		return this.unlocalizedName;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	static
	{
		for (EnumDyeColor enumdyecolor : values())
		{
			META_LOOKUP[enumdyecolor.getMetadata()] = enumdyecolor;
			DYE_DMG_LOOKUP[enumdyecolor.getDyeDamage()] = enumdyecolor;
		}
	}
}
