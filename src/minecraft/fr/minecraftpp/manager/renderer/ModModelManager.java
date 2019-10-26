package fr.minecraftpp.manager.renderer;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;

import fr.minecraftpp.block.IDynamicBlock;
import fr.minecraftpp.item.IDynamicItem;
import fr.minecraftpp.manager.block.ModBlock;
import fr.minecraftpp.manager.item.ModItem;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.util.ResourceLocation;

public class ModModelManager
{	
	public static void loadBlockModels(Map<ResourceLocation, ModelBlock> models, Map<ResourceLocation, ModelBlockDefinition> definitions)
	{
		for (IDynamicBlock dynamicBlock : ModBlock.REGISTRY)
		{
			ModelBlockDefinition value = new ModelBlockDefinition(Arrays.asList(ModelBlockDefinition.parseFromReader(new StringReader(ModModelManager.makeBlockStateString(dynamicBlock)))));
			definitions.put(new ResourceLocation("minecraft", "blockstates/" + dynamicBlock.getID().toLowerCase() + ".json") , value);
			models.put(new ResourceLocation("minecraft", "block/" + dynamicBlock.getID().toLowerCase()), ModelBlock.deserialize(ModModelManager.makeBlockModelString(dynamicBlock)));
		}
	}
	
	public static void loadItemModels(Map<ResourceLocation, ModelBlock> models)
	{
		for (IDynamicItem dynamicItem : ModItem.REGISTRY)
		{
			models.put(new ResourceLocation("minecraft", "item/" + dynamicItem.getID().toLowerCase()), ModelBlock.deserialize(ModModelManager.makeItemModelString(dynamicItem)));
		}
		
		for (IDynamicBlock dynamicBlock : ModBlock.REGISTRY)
		{
			models.put(new ResourceLocation("minecraft", "item/" + dynamicBlock.getID().toLowerCase()), ModelBlock.deserialize(ModModelManager.makeItemModelString(dynamicBlock)));
		}
	}
	
	private static String makeItemModelString(IDynamicItem item)
	{
		String str = "{\n";
			str += "\t\"parent\": \"item/handheld\",\n";
			str+= "\t\"textures\": {\n";
				str += "\t\t\"layer0\": \"items/" + item.getTextureName() + "\"\n";
			str += "\t}\n";
		str += "}";
		
		return str;
	}
	
	private static String makeItemModelString(IDynamicBlock block)
	{
		String str = "{\n";
			str += "\t\"parent\": \"block/" + block.getID().toLowerCase() + "\"\n";
		str += "}";
		
		return str;
	}
	
	private static String makeBlockModelString(IDynamicBlock block)
	{
		String str = "{\n";
			str += "\t\"parent\": \"block/cube_all\",\n";
			str+= "\t\"textures\": {\n";
				str += "\t\t\"all\": \"blocks/" + block.getTextureName() + "\"\n";
			str += "\t}\n";
		str += "}";
		
		return str;
	}
	
	private static String makeBlockStateString(IDynamicBlock block)
	{
		String str = "{\n";
			str += "\t\"variants\": {\n";
				str += "\t\t\"normal\": { \"model\": \"" + block.getID().toLowerCase() + "\" }\n";
			str += "\t}\n";
		str += "}";
		
		return str;
	}
}
