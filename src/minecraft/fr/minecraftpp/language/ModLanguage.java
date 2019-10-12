package fr.minecraftpp.language;

import java.util.HashMap;
import java.util.Map;

import fr.minecraftpp.anotation.Mod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod("minecraftpp")
public class ModLanguage 
{
	
	private static Map<String, String> toAdd = new HashMap();
	
	public static Map<String, String> getTranslation()
	{
		Map<String, String> translation = new HashMap<String, String>();
		
		translation.put("item.null.name", "Think About Unlocalized Name");
		translation.put("item.dyePowder.blue.name", "Blue Dye");

		translation.put("death.attack.scenarium", "%1$s tried to know the truth but couldn't bear it");
		
		translation.putAll(toAdd);
		
		return translation;
	}

	public static void addTranslation(Item item) {
		String unlocalizedName = item.getUnlocalizedName() + ".name";
		toAdd.put(unlocalizedName, unlocalizedNameToProperName(item.getUnlocalizedName()));
	}

	public static void addTranslation(Block block) {
		String unlocalizedName = block.getUnlocalizedName() + ".name";
		toAdd.put(unlocalizedName, unlocalizedNameToProperName(block.getUnlocalizedName()));
	}

	private static String unlocalizedNameToProperName(String unlocalizedName) {
		String itemName = unlocalizedName.split("\\.")[1];
		
		String spacedString = breakCamelCase(itemName);
		
		String firstLetterCapital = firstLetterCapital(spacedString);
		
		return firstLetterCapital;
	}

	private static String breakCamelCase(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				str = str.substring(0, i) + " " + str.substring(i);
				i++;
			}
		}
		return str;
	}

	private static String firstLetterCapital(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
