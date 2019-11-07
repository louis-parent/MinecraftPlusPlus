package fr.minecraftpp.util.nameGenerator;
import java.util.Random;

public class LongSyllable implements Syllable
{
	private Random rand;
	private int pos;

	public LongSyllable(Random rand, int pos)
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

		str += Word.getLongVowel(rand);

		if (this.pos == 1)
		{
			str += Word.getTransitionConsonant(rand);
			str += Word.getTransitionConsonant(rand);
		}
		else if (this.pos == 2)
		{
			str += Word.getTransitionConsonant(rand);
			str += Word.getStrongConsonant(rand);
		}

		return str;
	}

}
