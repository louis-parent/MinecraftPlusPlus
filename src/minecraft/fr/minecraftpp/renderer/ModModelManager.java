package fr.minecraftpp.renderer;

import java.util.Map;

import fr.minecraftpp.generator.IDynamicItem;
import fr.minecraftpp.item.ModItem;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.util.ResourceLocation;

public class ModModelManager
{	
	public static void loadItemModels(Map<ResourceLocation, ModelBlock> models)
	{
		for (IDynamicItem dynamicItem : ModItem.REGISTRY)
		{
			models.put(new ResourceLocation("minecraft", "item/" + dynamicItem.getID()), ModelBlock.deserialize(ModModelManager.makeModelString(dynamicItem.getTextureName())));
		}
	}
	
	private static String makeModelString(String textureName)
	{
		String str = "{\n";
			str += "\t\"parent\": \"item/handheld\",\n";
			str+= "\t\"textures\": {\n";
				str += "\t\t\"layer0\": \"items/" + textureName + "\"\n";
			str += "\t}\n";
		str += "}";
		
		return str;
	}
}
