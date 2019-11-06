package fr.minecraftpp.generator.backtrack.engine.constraints;

import java.io.BufferedReader;
import java.util.ArrayList;

import fr.minecraftpp.generator.backtrack.engine.Assignment;

public class ConstraintEgalite extends ConstraintPartielle
{

	public ConstraintEgalite(BufferedReader in) throws Exception
	{
		super(in);
	}

	@Override
	public boolean violation(Assignment a)
	{
		boolean areAllTrue = true;

		ArrayList<Object> presents = getPresentValues(a);

		if (presents.size() > 1)
		{
			for (int i = 1; i < presents.size(); i++)
			{
				areAllTrue &= presents.get(i).equals(presents.get(0));
			}
		}

		return !areAllTrue;
	}

	private ArrayList<Object> getPresentValues(Assignment a)
	{
		ArrayList<Object> liste = new ArrayList<Object>();

		for (String var : a.getVars())
		{
			if (this.varList.contains(var))
			{
				liste.add(a.get(var));
			}
		}

		return liste;
	}

	@Override
	public String toString()
	{
		return "\n\t Egalite " + super.toString();
	}
}
