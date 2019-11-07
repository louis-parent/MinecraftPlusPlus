package fr.minecraftpp.util.nameGenerator;
import java.util.Random;

public class AverageSyllable implements Syllable
{
	private Random rand;
	private int pos;

	public AverageSyllable(Random rand, int pos)
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
			str += Word.getStrongConsonant(rand);
		}
		else if (this.pos == 2)
		{
			str += Word.getTransitionConsonant(rand);
		}

		str += Word.getVowel(rand);
		
		if (this.pos == 1)
		{
			str += Word.getStrongConsonant(rand);
		}
		else if (this.pos == 2)
		{
			str += Word.getTransitionConsonant(rand);
		}
		else if (this.pos == 3)
		{
			str += Word.getConsonant(rand);
		}

		return str;
	}

}
