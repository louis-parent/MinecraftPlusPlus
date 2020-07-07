package fr.minecraftpp.util.nameGenerator;

import java.util.ArrayList;
import java.util.List;

public class Word
{

	private List<Character> symbols;

	public Word()
	{
		this.symbols = new ArrayList<Character>();
	}

	public Word(Character symbol)
	{
		this();
		this.symbols.add(symbol);
	}

	@SafeVarargs
	public Word(Character... tag)
	{
		this();

		for (Character symbol : tag)
		{
			this.symbols.add(symbol);
		}
	}

	public Word(Word word)
	{
		this();

		for (Character symbol : word.symbols)
		{
			this.symbols.add(symbol);
		}
	}

	public int length()
	{
		return this.symbols.size();
	}

	public Character getLast()
	{
		return this.symbols.get(this.symbols.size() - 1);
	}

	public Character getFirst()
	{
		return this.symbols.get(0);
	}

	public Word getAllButFirst()
	{
		Word newWord = new Word();

		for (int i = 1; i < this.symbols.size(); i++)
		{
			newWord.addUnsafely(this.symbols.get(i));
		}

		return newWord;
	}

	private void addUnsafely(Character symbol)
	{
		this.symbols.add(symbol);
	}

	public Word add(Character symbol)
	{
		Word newWord = new Word(this);

		newWord.addUnsafely(symbol);

		return newWord;
	}

	public Character get(int i)
	{
		return this.symbols.get(i);
	}

	public Word set(int index, Character symbol)
	{
		Word newWord = new Word(this);

		newWord.symbols.set(index, symbol);

		return newWord;
	}

	@Override
	public String toString()
	{
		String str = "";

		for (Character symbol : this.symbols)
		{
			str += symbol.toString();
		}

		return str;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj.getClass().equals(Word.class))
		{
			return ((Word) obj).symbols.equals(this.symbols);
		}
		else
		{
			return false;
		}
	}

}
