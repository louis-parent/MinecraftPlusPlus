package fr.minecraftpp.randomizer.backtrack;

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
	
	public static void main(String[] args) {
		System.out.println(new Pretreatment(new Random(), 7));
	}

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
		this.variables.add(OreProperties.COAL + "");
		this.variables.add(OreProperties.IRON + "");
		this.variables.add(OreProperties.DIAMOND + "");
		this.variables.add(OreProperties.FUEL + "1");
		this.variables.add(OreProperties.BEACON + "1");
		this.variables.add(OreProperties.BEACON + "2");
		this.variables.add(OreProperties.ENCHANT_CURRENCY + "1");
	}

	private void addRandomVariables(Random rand)
	{
		addSpecificVariables(OreProperties.FUEL, rand.nextInt(3));
		addSpecificVariables(OreProperties.ENCHANT_CURRENCY, rand.nextInt(3));
		addMaterialVariables(rand);

		int nbOfMaterial = getAllVariablesFrom(OreProperties.MATERIAL).size();
		nbOfMaterial += getAllVariablesFrom(OreProperties.METAL).size();
		
		addSpecificVariables(OreProperties.BEACON, Math.max(0, rand.nextInt(6 - nbOfMaterial)) + nbOfMaterial);
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
			if (string.matches("\\A" + name + "\\d*\\z"))
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
		
		List<String> vanillaGroup = new ArrayList<String>();
		vanillaGroup.add(OreProperties.COAL.toString());
		vanillaGroup.add(OreProperties.IRON.toString());
		vanillaGroup.add(OreProperties.DIAMOND.toString());
		str += allDiffFrom(vanillaGroup);

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
		String str = allVariablesMustBeAssigned();

		str += variablesIsBetween(OreProperties.COAL, OreProperties.FUEL);
		
		
		boolean containsMetal = getAllVariablesFrom(OreProperties.METAL).size() > 0;
		
		if (containsMetal) {
			str += variablesIsBetween(OreProperties.IRON, OreProperties.MATERIAL, OreProperties.METAL);
			str += variablesIsBetween(OreProperties.DIAMOND, OreProperties.MATERIAL, OreProperties.METAL);
		} else {			
			str += variablesIsBetween(OreProperties.IRON, OreProperties.MATERIAL);
			str += variablesIsBetween(OreProperties.DIAMOND, OreProperties.MATERIAL);
		}

		return str;
	}

	private String allVariablesMustBeAssigned()
	{
		String str = "inten\n";

		str += toStringAllVariables(this.variables);

		str += toStringIntension();
		
		return str;
	}

	private String toStringAllVariables(List<String> vars)
	{
		String str = "";
		
		for (int i = 0; i < vars.size(); i++)
		{
			String var = vars.get(i);
			str += var;

			if (i < vars.size() - 1)
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
			str += toStringAllVariablesEqualTo(this.variables, i + "");
			
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

	private String toStringAllVariablesEqualTo(List<String> vars, String value)
	{
		String str = "(";

		for (int j = 0; j < vars.size(); j++)
		{
			String var = vars.get(j);
			str += "($" + var + " == " + value + ")"; 

			if (j < vars.size() - 1)
			{
				str += " || ";
			}
		}
		
		str += ")";
		return str;
	}

	private String variablesIsBetween(OreProperties uniqueType, OreProperties... types)
	{
		String str = "inten\n";

		List<String> multipleTypeVars = new ArrayList<String>();
		for (OreProperties type : types) {
			multipleTypeVars.addAll(getAllVariablesFrom(type));
		}
		
		List<String> vars = getAllVariablesFrom(uniqueType);
		vars.addAll(multipleTypeVars);
		
		str += toStringAllVariables(vars);
		
		str += toStringAllVariablesEqualTo(multipleTypeVars, "$" + uniqueType.toString());
		
		str += "\n";
		
		return str;
	}
}
