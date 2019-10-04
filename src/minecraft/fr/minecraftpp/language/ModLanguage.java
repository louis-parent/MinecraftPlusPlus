package fr.minecraftpp.language;

import java.util.HashMap;
import java.util.Map;

import fr.minecraftpp.anotation.Mod;

@Mod("minecraftpp")
public class ModLanguage 
{
	public static Map<String, String> getTranslation()
	{
		Map<String, String> translation = new HashMap<String, String>();
		
		translation.put("tile.scenariteOre.name", "Scenarite Ore");
		translation.put("item.scenarium.name", "Scenarium");
		translation.put("tile.blockScenarium.name", "Scenarium Block");
		
		return translation;
	}
}
