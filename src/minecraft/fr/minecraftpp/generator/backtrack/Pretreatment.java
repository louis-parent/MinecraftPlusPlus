package fr.minecraftpp.generator.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import fr.minecraftpp.enumeration.OreProperties;

public class Pretreatment
{
	private final int NUMBER_OF_ORES;

	private List<String> variables;

	public Pretreatment(Random rand, int numberOfOres)
	{
		this.NUMBER_OF_ORES = numberOfOres;
		this.variables = new ArrayList<String>();
		addCertainVariables();
		addRandomVariables(rand);
	}

	private void addCertainVariables()
	{
		this.variables.add(OreProperties.REDSTONE + "");
		this.variables.add(OreProperties.BLUEDYE + "");
		this.variables.add(OreProperties.CURRENCY + "");
		this.variables.add(OreProperties.BEACON + "1");
		this.variables.add(OreProperties.BEACON + "2");
		this.variables.add(OreProperties.FUEL + "1");
		this.variables.add(OreProperties.ENCHANT_CURRENCY + "1");
	}

	private void addRandomVariables(Random rand)
	{
		addSpecificVariables(OreProperties.BEACON, rand.nextInt(4));
		addSpecificVariables(OreProperties.FUEL, rand.nextInt(3));
		addSpecificVariables(OreProperties.ENCHANT_CURRENCY, rand.nextInt(3));
		addMaterialVariables(rand);
	}

	private void addSpecificVariables(OreProperties name, int quantity)
	{
		int startingIndex = getAllVariablesFrom(name).size();

		for (int i = 1; i < quantity + 1; i++)
		{
			this.variables.add("" + name + (i + startingIndex));
		}
	}

	private void addMaterialVariables(Random rand)
	{
		int numberOfMaterials = 2 + rand.nextInt(3);
		int numberOfMetals = rand.nextInt(numberOfMaterials);
		addSpecificVariables(OreProperties.MATERIAL, numberOfMaterials - numberOfMetals);
		addSpecificVariables(OreProperties.METAL, numberOfMetals);

	}

	private List<String> getAllVariablesFrom(OreProperties name)
	{
		List<String> list = new ArrayList<String>();

		for (String string : variables)
		{
			if (string.matches("\\A" + name + "\\d+\\z"))
			{
				list.add(string);
			}
		}

		return list;
	}

	@Override
	public String toString()
	{
		String str = toStringVariables();
		str += toStringContraintes();
		return str;
	}

	private String toStringVariables()
	{
		String str = this.variables.size() + "\n";

		for (String var : this.variables)
		{
			str += var + ";";

			for (int i = 1; i <= NUMBER_OF_ORES; i++)
			{
				str += i;
				if (i != NUMBER_OF_ORES)
				{
					str += ";";
				}
				else
				{
					str += "\n";
				}
			}
		}

		return str;
	}

	private String toStringContraintes()
	{
		int taille = 0;

		String allDiff = toStringContraintesAllDiff();
		taille += StringUtils.countMatches(allDiff, "diff");

		String inten = toStringContraintesInten();
		taille += StringUtils.countMatches(inten, "inten");

		return taille + "\n" + allDiff + inten;
	}

	private String toStringContraintesAllDiff()
	{
		String str = "";

		str += allDiffFrom(getAllVariablesFrom(OreProperties.BEACON));
		str += allDiffFrom(getAllVariablesFrom(OreProperties.FUEL));
		str += allDiffFrom(getAllVariablesFrom(OreProperties.ENCHANT_CURRENCY));

		List<String> metalsAndMaterials = getAllVariablesFrom(OreProperties.MATERIAL);
		metalsAndMaterials.addAll(getAllVariablesFrom(OreProperties.METAL));
		str += allDiffFrom(metalsAndMaterials);

		List<String> nonMetalGroup = getAllVariablesFrom(OreProperties.METAL);
		nonMetalGroup.add(OreProperties.REDSTONE.toString());
		nonMetalGroup.add(OreProperties.BLUEDYE.toString());
		str += allDiffFrom(nonMetalGroup);

		List<String> allProperties = Arrays.asList(new String[] { OreProperties.REDSTONE + "", OreProperties.BEACON + "1", OreProperties.CURRENCY + "", OreProperties.BLUEDYE + "", OreProperties.FUEL + "1", OreProperties.ENCHANT_CURRENCY + "1", OreProperties.MATERIAL + "1" });
		str += allDiffFrom(allProperties);

		return str;
	}

	private String allDiffFrom(List<String> list)
	{
		String str = "";

		if (list.size() > 1)
		{
			str += "diff\n";
			for (int i = 0; i < list.size(); i++)
			{
				str += list.get(i);
				if (i != list.size() - 1)
				{
					str += ";";
				}
				else
				{
					str += "\n";
				}
			}
		}

		return str;
	}

	private String toStringContraintesInten()
	{
		String str = "inten\n";

		str += toStringAllVariables();

		str += toStringIntension();

		return str;
	}

	private String toStringAllVariables()
	{
		String str = "";
		
		for (int i = 0; i < variables.size(); i++)
		{
			String var = variables.get(i);
			str += var;

			if (i < variables.size() - 1)
			{
				str += ";";
			}
			else
			{
				str += "\n";
			}
		}
		return str;
	}

	private String toStringIntension()
	{
		String str = "";
		
		for (int i = 1; i < NUMBER_OF_ORES + 1; i++)
		{
			str += toStringAllVariablesEqualTo(i);
			
			if (i < NUMBER_OF_ORES)
			{
				str += " && ";
			}
			else
			{
				str += "\n";
			}
		}
		
		return str;
	}

	private String toStringAllVariablesEqualTo(int value)
	{
		String str = "(";

		for (int j = 0; j < variables.size(); j++)
		{
			String var = variables.get(j);
			str += "($" + var + " == " + value + ")"; 

			if (j < variables.size() - 1)
			{
				str += " || ";
			}
		}
		
		str += ")";
		return str;
	}
}
