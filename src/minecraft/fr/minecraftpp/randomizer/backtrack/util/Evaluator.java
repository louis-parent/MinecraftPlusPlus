package fr.minecraftpp.randomizer.backtrack.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Evaluator
{

	public static boolean evaluer(String expression)
	{
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try
		{
			boolean resp = (boolean) engine.eval(expression);
			return resp;
		}
		catch (ScriptException e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
