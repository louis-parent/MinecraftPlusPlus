package fr.minecraftpp.util.nameGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import fr.minecraftpp.util.nameGenerator.map.Branch;
import fr.minecraftpp.util.nameGenerator.map.InitialBranch;
import fr.minecraftpp.util.nameGenerator.map.IntegerWeightedProbabilisticMap;

public class Tree
{
	private Random rand;

	private Branch[] branches;

	private IntegerWeightedProbabilisticMap wordSizeMap;

	private Character[] alphabet;
	private Character start;
	private Character end;

	private InitialBranch root;

	public Tree(Character[] alphabet, Character start, Character end)
	{
		this(new Random(), alphabet, start, end);
	}

	public Tree(Random rand, Character[] alphabet, Character start, Character end)
	{

		this.rand = rand;

		this.branches = (Branch[]) new Branch[(int) Math.pow(alphabet.length + 2, 2)];

		this.wordSizeMap = new IntegerWeightedProbabilisticMap(this.rand);

		this.alphabet = alphabet;
		this.start = start;
		this.end = end;

		this.root = new InitialBranch(this);

		buildBranches();
	}

	public Word makeWord()
	{
		return makeWord(this.wordSizeMap.getRandomElement());
	}

	public Word makeWord(int maxSize)
	{
		Word word;

		while ((word = makeRandomWord()).length() > maxSize)
			;

		return word;
	}

	public Word makeRandomWord()
	{
		Word word = new Word();
		Branch next = this.root;
		while (!(next = next.findNextRandomBranch()).isLastBranch())
		{
			word = word.add(next.getLastLetter());
		}

		return word;
	}

	public void insertWord(Word word)
	{
		insertWordLength(word);

		Character first = this.getStart();

		Character second = word.get(0);
		this.root.addBranch(second);

		word = word.getAllButFirst();

		while (word.length() > 0)
		{
			Word tag = new Word(first, second);
			Character newChar = word.get(0);
			word = word.getAllButFirst();

			this.branches[this.getTreeIndex(tag)].addBranch(newChar);

			first = second;
			second = newChar;
		}

		Word tag = new Word(first, second);
		this.branches[this.getTreeIndex(tag)].addBranch(this.end);
	}

	private void insertWordLength(Word word)
	{
		int length = word.length();

		this.wordSizeMap.addBranch(length);
	}

	private void buildBranches()
	{
		Word tag = new Word(this.start, this.start);

		for (int i = 0; i < this.branches.length; i++)
		{
			this.branches[i] = new Branch(tag, this);
			tag = getNextTag(tag);
		}
	}

	public Word getNextTag(Word tag)
	{
		boolean looped = true;
		int index = tag.length() - 1;

		while (looped && index >= 0)
		{
			tag = tag.set(index, nextChar(tag.get(index)));

			if (tag.get(index).equals(this.start))
			{
				looped = true;
				index--;
			}
			else
			{
				looped = false;
			}
		}

		return tag;
	}

	public int getTreeIndex(Word tag)
	{
		int sum = (int) (Math.pow(this.alphabet.length + 2, 1) * getValueOf(tag.get(0)));
		sum += (int) (Math.pow(this.alphabet.length + 2, 0) * getValueOf(tag.get(1)));

		return sum;
	}

	public int getValueOf(Character symbol)
	{
		if (symbol.equals(this.start))
		{
			return 0;
		}
		else if (symbol.equals(this.end))
		{
			return this.alphabet.length + 1;
		}
		else
		{
			return indexOf(symbol) + 1;
		}
	}

	private int indexOf(Character symbol)
	{
		for (int i = 0; i < this.alphabet.length; i++)
		{
			if (this.alphabet[i].equals(symbol))
			{
				return i;
			}
		}

		return -1;
	}

	public Character nextChar(Character symbol)
	{
		int val = getValueOf(symbol);

		if (val == 0)
		{
			return this.alphabet[0];
		}
		else if (val == this.alphabet.length)
		{
			return this.end;
		}
		else if (val == this.alphabet.length + 1)
		{
			return this.start;
		}
		else
		{
			int index = indexOf(symbol);
			return this.alphabet[index + 1];
		}
	}

	public Branch getBranchAt(int index)
	{
		return this.branches[index];
	}

	public Random getRand()
	{
		return this.rand;
	}

	public Character getStart()
	{
		return this.start;
	}

	public Character getEnd()
	{
		return this.end;
	}

	@Override
	public String toString()
	{
		String str = "";

		str += "wordSize:" + this.wordSizeMap + "\n";
		str += "initial:" + this.root + "\n";
		str += "branches:" + branchesToString() + "\n";

		return str;
	}

	private String branchesToString()
	{
		String str = "";

		for (int i = 0; i < this.branches.length; i++)
		{
			if (branches[i].getWeight() > 0)
			{
				str += branches[i] + "&";
			}
		}

		return str;
	}

	public void save(BufferedWriter file) throws IOException
	{
		file.write(this.toString());
	}

	public void buildFromFile(BufferedReader file) throws IOException
	{
		String firstLine = file.readLine();
		String secondLine = file.readLine();
		String thirdLine = file.readLine();

		String wordSizeMapString = firstLine.split(":")[1];
		this.wordSizeMap.buildFromString(wordSizeMapString);

		String initialMapString = secondLine.split(":")[1];
		buildInitial(initialMapString);

		String branchesMapString = thirdLine.split(":")[1];
		buildBranches(branchesMapString);
	}

	private void buildInitial(String initialMapString)
	{
		this.root.buildFromString(initialMapString.split(">")[1]);
	}

	private void buildBranches(String branchesMapString)
	{
		String[] wheightedBranches = branchesMapString.split("&");

		int i = 0;

		for (Branch branch : this.branches)
		{
			if (i < wheightedBranches.length)
			{
				Word tag = new Word(wheightedBranches[i].split(">")[0].chars().mapToObj(c -> (char) c).toArray(Character[]::new));

				if (branch.getTag().equals(tag))
				{
					branch.buildFromString(wheightedBranches[i].split(">")[1]);
					i++;
				}
			}
		}
	}
}
