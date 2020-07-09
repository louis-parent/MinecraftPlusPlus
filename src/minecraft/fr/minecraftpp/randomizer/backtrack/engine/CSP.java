package fr.minecraftpp.randomizer.backtrack.engine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

import fr.minecraftpp.randomizer.backtrack.Backtrack;
import fr.minecraftpp.randomizer.backtrack.engine.constraints.Constraint;
import net.minecraft.util.LoggingPrintStream;

public class CSP
{

	public Network network;
	private Assignment assignment;

	private ArrayList<Assignment> solutions;

	public CSP(Network r)
	{
		network = r;
		solutions = new ArrayList<Assignment>();
		assignment = new Assignment();
		faireArcConsistance();
	}

	public Assignment searchSolution()
	{
		System.setErr(new PrintStream(new ByteArrayOutputStream()));
		assignment.clear();
		Assignment sol = backtrack();
		System.setErr(new LoggingPrintStream("STDERR", System.err));
		return sol;
	}

	private Assignment backtrack()
	{
		if (assignment.getVars().size() == network.getVars().size())
		{
			return assignment;
		}
		else
		{
			return explore();
		}
	}

	private Assignment explore()
	{
		String x = chooseVar();

		for (Integer v : tri(network.getDom(x)))
		{
			assignment.put(x, v);

			if (consistant(x))
			{
				Assignment b = backtrack();
				if (b != null)
				{
					return b;
				}
			}
			assignment.remove(x);
		}

		return null;
	}

	private String chooseVar()
	{
		ArrayList<String> liste = new ArrayList<>();
		for (String var : network.getVars())
		{
			if (!assignment.getVars().contains(var))
			{
				liste.add(var);
			}
		}
		liste = trierParDomDeg(liste);

		return liste.size() == 0 ? null : liste.get(0);
	}

	private ArrayList<String> trierParDomDeg(ArrayList<String> values)
	{
		ArrayList<String> liste = new ArrayList<>();

		while (values.size() > 0)
		{
			String meilleur = prendreMeilleurDom(values);
			values.remove(meilleur);
			liste.add(meilleur);
		}

		return liste;
	}

	private String prendreMeilleurDom(ArrayList<String> values)
	{
		ArrayList<String> liste = new ArrayList<>();

		int min = Integer.MAX_VALUE;

		for (String variable : values)
		{
			int taille = network.getDom(variable).size();
			if (taille < min)
			{
				liste.clear();
				liste.add(variable);
				min = taille;
			}
			else if (taille == min)
			{
				liste.add(variable);
			}
		}

		return getMeilleurDeg(liste);
	}

	private String getMeilleurDeg(ArrayList<String> liste)
	{
		String meilleureVal = "";
		int max = -1;
		for (String variable : liste)
		{
			int valActuel = 0;

			for (Constraint contrainte : network.getConstraints())
			{
				if (contrainte.getVars().contains(variable))
				{
					valActuel++;
				}
			}

			if (valActuel > max)
			{
				max = valActuel;
				meilleureVal = variable;
			}
		}

		return meilleureVal;
	}

	private boolean consistant(String lastAssignedVar)
	{
		for (Constraint contrainte : network.getConstraints())
		{
			if (contrainte.getVars().contains(lastAssignedVar) && contrainte.violation(assignment))
			{
				return false;
			}
		}

		return true;
	}

	private ArrayList<Integer> tri(ArrayList<Integer> values)
	{
		ArrayList<Integer> clone = (ArrayList<Integer>) values.clone();

		clone.sort(new Comparator()
		{

			@Override
			public int compare(Object o1, Object o2)
			{
				return Backtrack.getRand().nextInt(3) - 1;
			}

		});

		return clone;
	}

	private void faireArcConsistance()
	{
		for (Constraint c : network.getConstraints())
		{
			for (String var : c.getVars())
			{
				ArrayList<Integer> dom = network.getDom(var);
				for (int i = 0; i < dom.size(); i++)
				{
					Assignment assign = new Assignment();
					assign.put(var, dom.get(i));
					if (c.violation(assign))
					{
						dom.remove(dom.get(i));
						i--;
					}
				}
			}
		}
	}

}
