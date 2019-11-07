package fr.minecraftpp.util.nameGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Word
{
	private List<Syllable> syllables;
	private Random rand;

	private Word()
	{
		this.syllables = new ArrayList<>();
	}

	public Word(Random rand, int size)
	{
		this();
		
		
		this.rand = rand;
		int choix;
		int pos = 0;
		
		do {
			choix = this.rand.nextInt(3) + 2;
			pos++;
			
			if (size - choix >= 0) {
				switch (choix)
				{
					case 2:
						syllables.add(new ShortSyllable(this.rand, pos));
						break;
					case 3:
						syllables.add(new AverageSyllable(this.rand, pos));
						break;
					case 4:
						syllables.add(new LongSyllable(this.rand, pos));
						break;
					default:
				}
				size -= choix;
			}
		} while (size > 1 && pos < 3);
	}

	public Word(Random rand)
	{
		this(rand, rand.nextInt(5) + 5);
	}
	
	public static String getWord(Random rand) {
		return new Word(rand) + "";
	}

	@Override
	public String toString()
	{
		String str = "";

		for (Syllable syl : this.syllables)
		{
			str += syl.getString();
		}

		return str;
	}

	protected static String getConsonant(Random rand)
	{
		if (rand.nextBoolean())
		{
			return getStrongConsonant(rand);
		}
		else
		{
			return getTransitionConsonant(rand);
		}
	}

	protected static String getTransitionConsonant(Random rand)
	{
		int choix = rand.nextInt(13);
		switch (choix)
		{
			case 0:
				return "c";
			case 1:
				return "d";
			case 2:
				return "g";
			case 3:
				return "h";
			case 4:
				return "k";
			case 5:
				return "l";
			case 6:
				return "m";
			case 7:
				return "n";
			case 8:
				return "p";
			case 9:
				return "r";
			case 10:
				return "s";
			case 11:
				return "t";
			case 12:
				return "v";
			default:
				return "";
		}
	}

	protected static String getStrongConsonant(Random rand)
	{
		int choix = rand.nextInt(6);
		switch (choix)
		{
			case 0:
				return "b";
			case 1:
				return "f";
			case 2:
				return "j";
			case 3:
				return "z";
			case 4:
				return "w";
			case 5:
				return "x";
			default:
				return "";
		}
	}

	protected static String getVowel(Random rand)
	{
		if (rand.nextBoolean())
		{
			return getLongVowel(rand);
		}
		else
		{
			return getShortVowel(rand);
		}
	}
	
	protected static String getLongVowel(Random rand) {
		int choix = rand.nextInt(8);
		switch (choix)
		{
			case 0:
				return "au";
			case 1:
				return "eu";
			case 2:
				return "ou";
			case 3:
				return "ai";
			case 4:
				return "ei";
			case 5:
				return "oi";
			case 6:
				return "ee";
			case 7:
				return "oo";
			default:
				return "";
		}
	}
	
	protected static String getShortVowel(Random rand) {
		int choix = rand.nextInt(6);
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
			case 5:
				return "y";
			default:
				return "";
		}
	}
}
