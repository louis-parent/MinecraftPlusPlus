package fr.minecraftpp.util.nameGenerator.map;

import fr.minecraftpp.util.nameGenerator.Tree;
import fr.minecraftpp.util.nameGenerator.Word;

public class InitialBranch extends Branch
{
	public InitialBranch(Tree tree)
	{
		super(getnewWord(tree), tree);
	}

	private static  Word getnewWord(Tree tree)
	{
		Character start = tree.getStart();
		Word word = new Word(start);
		return word;
	}

	@Override
	protected Branch getNewBranchWith(Character symbol)
	{
		Word newTag = this.tag.add(symbol);
		int index = this.tree.getTreeIndex(newTag);

		return this.tree.getBranchAt(index);
	}
}