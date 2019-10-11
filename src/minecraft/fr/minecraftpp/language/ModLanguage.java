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
		translation.put("tile.blockScenarium.name", "Scenarium Block");
		
		translation.put("item.null.name", "Think About Unlocalized Name");
		translation.put("item.scenarium.name", "Scenarium");
		translation.put("item.scenariumSword.name", "Scenarium Sword");
		translation.put("item.scenariumPickaxe.name", "Scenarium Pickaxe");
		translation.put("item.scenariumAxe.name", "Scenarium Axe");
		translation.put("item.scenariumSpade.name", "Scenarium Shovel");
		translation.put("item.scenariumHoe.name", "Scenarium Hoe");
		translation.put("item.dyePowder.blue.name", "Blue Dye");

		translation.put("death.attack.scenarium", "%1$s tried to know the truth but couldn't bear it");
		return translation;
	}
}
