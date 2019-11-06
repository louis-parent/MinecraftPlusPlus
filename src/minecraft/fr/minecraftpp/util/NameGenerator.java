package fr.minecraftpp.util;

import java.util.Random;

public class NameGenerator
{
	private static Random rand = new Random();

	public static String generateName()
	{
		return generateName(NameGenerator.rand);
	}

	public static String generateName(Random rand)
	{
		int nbChars = 3 + rand.nextInt(4);

		return startRecursiveGeneration(rand, nbChars);
	}

	private static String startRecursiveGeneration(Random rand, int nbChars)
	{
		int type = rand.nextInt(2);
		return generateRecursively(rand, nbChars, type);
	}

	private static String generateRecursively(Random rand, int nbChars, int typeOfLastChar)
	{
		if (nbChars > 0)
		{
			int newType = (typeOfLastChar + 1) % 2;
			return selectChar(rand, newType) + generateRecursively(rand, nbChars - 1, newType);
		}
		else
		{
			return "";
		}
	}

	private static String selectChar(Random rand, int typeOfChar)
	{
		if (typeOfChar == 0)
		{
			String letters = getConsonant(rand);
			if (rand.nextInt(2) == 0)
			{
				letters += getConsonant(rand);
			}
			return letters;
		}
		else
		{
			return getVowel(rand);
		}
	}

	private static String getConsonant(Random rand)
	{
		int choix = rand.nextInt(20);
		switch (choix)
		{
			case 0:
				return "b";
			case 1:
				return "c";
			case 2:
				return "d";
			case 3:
				return "f";
			case 4:
				return "g";
			case 5:
				return "h";
			case 6:
				return "j";
			case 7:
				return "k";
			case 8:
				return "l";
			case 9:
				return "m";
			case 10:
				return "n";
			case 11:
				return "p";
			case 12:
				return "q";
			case 13:
				return "r";
			case 14:
				return "s";
			case 15:
				return "t";
			case 16:
				return "v";
			case 17:
				return "w";
			case 18:
				return "x";
			case 19:
				return "z";
			default:
				return "";
		}
	}

	private static String getVowel(Random rand)
	{
		int choix = rand.nextInt(14);
		switch (choix)
		{
			case 0:
				return "a";
			case 1:
				return "e";
			case 2:
				return "i";
			case 3:
				return "o";
			case 4:
				return "u";
			case 13:
				return "y";
			case 5:
				return "au";
			case 6:
				return "eu";
			case 7:
				return "ou";
			case 8:
				return "ai";
			case 9:
				return "ei";
			case 10:
				return "oi";
			case 11:
				return "ee";
			case 12:
				return "oo";
			default:
				return "";
		}
	}
}
