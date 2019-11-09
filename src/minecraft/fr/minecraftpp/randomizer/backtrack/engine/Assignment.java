package fr.minecraftpp.randomizer.backtrack.engine;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Assignment extends HashMap<String, Integer>
{

	public Assignment()
	{
		super();
	}

	@Override
	public Assignment clone()
	{
		return (Assignment) super.clone();
	}

	public ArrayList<String> getVars()
	{
		return new ArrayList<String>(keySet());
	}
}
