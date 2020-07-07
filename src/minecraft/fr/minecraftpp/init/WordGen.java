package fr.minecraftpp.init;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import fr.minecraftpp.util.nameGenerator.Tree;

public class WordGen
{
	private static Tree tree;

	public static void init(long seed)
	{
		tree = importTree(new Random(seed));
	}

	private static Tree importTree(Random rand)
	{
		Tree tree = new Tree(rand, new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' }, '$', '#');

		try
		{
			BufferedReader file = new BufferedReader(new FileReader("wordGen.mpp"));
			tree.buildFromFile(file);
			file.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return tree;
	}

	public static String getWord()
	{
		return tree.makeWord().toString();
	}
}
