package fr.minecraftpp.generator.backtrack.engine.constraints;

import java.io.BufferedReader;
import java.util.ArrayList;

public abstract class ConstraintPartielle extends Constraint
{

	public ConstraintPartielle(BufferedReader in) throws Exception
	{
		super(in);
	}

	public ConstraintPartielle(ArrayList<String> var)
	{
		super(var);
	}

	public ConstraintPartielle(ArrayList<String> var, String name)
	{
		super(var, name);
	}

}
