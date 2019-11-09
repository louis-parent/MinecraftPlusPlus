package fr.minecraftpp.randomizer.backtrack.engine.constraints;

import java.io.BufferedReader;

import fr.minecraftpp.randomizer.backtrack.engine.Assignment;
import fr.minecraftpp.randomizer.backtrack.util.Evaluator;

public class ConstraintIntension extends ConstraintTotale
{

	private String expression;

	public ConstraintIntension(BufferedReader in) throws Exception
	{
		super(in);
		expression = in.readLine();
	}

	@Override
	protected boolean verifierValeurs(Assignment a)
	{
		String aEval = expression;
		for (String variable : varList)
		{
			aEval = aEval.replace("$" + variable, a.get(variable) + "");
		}

		boolean evaluer = Evaluator.evaluer(aEval);
		return !evaluer;
	}

	@Override
	public String toString()
	{
		return "\n\t Intension " + super.toString() + ": " + expression;
	}
}
