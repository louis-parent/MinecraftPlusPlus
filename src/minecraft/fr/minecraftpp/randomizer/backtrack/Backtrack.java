package fr.minecraftpp.randomizer.backtrack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fr.minecraftpp.randomizer.backtrack.engine.CSP;
import fr.minecraftpp.randomizer.backtrack.engine.Network;

public class Backtrack
{

	private static Random rand;

	public static Random getRand()
	{
		return rand;
	}

	public static Map<String, Integer> generateSolution(Random rand, int numberOfOres)
	{
		Backtrack.rand = rand;

		Pretreatment pretreatment = new Pretreatment(rand, numberOfOres);
		return solve(pretreatment.toString());
	}

	private static Map<String, Integer> solve(String network)
	{
		Network myNetwork;
		try
		{
			myNetwork = new Network(network);

			CSP rech = new CSP(myNetwork);
			return rech.searchSolution();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new HashMap<String, Integer>();
		}
	}
}
