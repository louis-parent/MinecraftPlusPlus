package fr.minecraftpp.randomizer.backtrack.engine.constraints;

import java.io.BufferedReader;
import java.util.ArrayList;

import fr.minecraftpp.randomizer.backtrack.engine.Assignment;

public class ConstraintDiff extends ConstraintPartielle
{

	public ConstraintDiff(BufferedReader in) throws Exception
	{
		super(in);
	}

	@Override
	public boolean violation(Assignment a)
	{
		ArrayList<Object> liste = new ArrayList<>();

		for (String variable : a.getVars())
		{
			if (varList.contains(variable))
			{
				if (!liste.contains(a.get(variable)))
				{
					liste.add(a.get(variable));
				}
				else
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "\n\t Diff " + super.toString();
	}
}
