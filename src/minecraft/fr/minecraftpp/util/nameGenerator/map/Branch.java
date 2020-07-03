package fr.minecraftpp.util.nameGenerator.map;

import java.util.Map;

import fr.minecraftpp.util.nameGenerator.Tree;
import fr.minecraftpp.util.nameGenerator.Word;

public class Branch extends WeightedProbabilisticMap<Character>
{
	protected Tree tree;

	protected Word tag;

	public Branch(Character[] tag, Tree tree)
	{
		this(new Word(tag), tree);
	}

	public Branch(Word tag, Tree tree)
	{
		super(tree.getRand());
		this.tree = tree;
		this.tag = tag;
	}

	public Word getTag()
	{
		return this.tag;
	}

	public Character getLastLetter()
	{
		return this.tag.getLast();
	}

	public Branch findNextRandomBranch()
	{
		return getNewBranchWith(getRandomElement());
	}

	public boolean isValid()
	{
		return this.getWeight() > 0 || this.isLastBranch();
	}

	public boolean isFirstBranch()
	{
		return this.tag.getFirst().equals(this.tree.getStart());
	}

	public boolean isLastBranch()
	{
		return this.tag.getLast().equals(this.tree.getEnd());
	}

	protected Branch getNewBranchWith(Character symbol)
	{
		Word newTag = this.tag.getAllButFirst().add(symbol);
		int index = this.tree.getTreeIndex(newTag);

		return this.tree.getBranchAt(index);
	}

	@Override
	public String toString()
	{
		String str = this.tag + ">";

		for (Map.Entry<Character, Integer> entry : this.getLeafs().entrySet())
		{
			str += entry.getKey() + "-" + entry.getValue() + ";";
		}

		return str;
	}

	@Override
	public void buildFromString(String str)
	{
		String[] entries = str.split(";");

		int sum = 0;

		for (String entry : entries)
		{
			Character key = entry.split("-")[0].charAt(0);
			Integer value = Integer.parseInt(entry.split("-")[1]);

			this.leafs.put(key, value);

			sum += value;
		}
		
		this.weight = sum;
	}
}
