package fr.minecraftpp.generator.backtrack.engine.constraints;

import java.io.BufferedReader;
import java.util.ArrayList;

import fr.minecraftpp.generator.backtrack.engine.Assignment;

public abstract class ConstraintTotale extends Constraint
{

	public ConstraintTotale(BufferedReader in) throws Exception
	{
		super(in);
	}

	public ConstraintTotale(ArrayList<String> var)
	{
		super(var);
	}

	public ConstraintTotale(ArrayList<String> var, String name)
	{
		super(var, name);
	}

	@Override
	public boolean violation(Assignment a)
	{
		boolean b = verifierPresence(a) && verifierValeurs(a);
		return b;
	}

	protected abstract boolean verifierValeurs(Assignment a);

	protected boolean verifierPresence(Assignment a)
	{
		for (String nomVariable : varList)
		{
			if (!a.getVars().contains(nomVariable))
			{
				return false;
			}
		}
		return true;
	}
}
