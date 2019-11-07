package fr.minecraftpp.util.nameGenerator;
import java.util.Random;

public class ShortSyllable implements Syllable
{
	private Random rand;
	private int pos;

	public ShortSyllable(Random rand, int pos)
	{
		this.rand = rand;
		this.pos = pos;
	}

	@Override
	public String getString()
	{
		String str = "";

		if (this.pos == 1)
		{
			str += Word.getConsonant(rand);
		}
		else if (this.pos == 3)
		{
			str += Word.getTransitionConsonant(rand);
		}

		str += Word.getShortVowel(rand);

		if (this.pos == 1)
		{
			str += Word.getTransitionConsonant(rand);
		}

		return str;
	}

}
