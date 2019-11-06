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
			definitions.put(new ResourceLocation("minecraft", "blockstates/" + dynamicBlock.getID().toLowerCase() + ".json"), value);
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
		str += "\t\"textures\": {\n";
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
		switch (block.getModelType())
		{
			case COLORED:
				return makeBlockColoredModelString(block);

			case NORMAL:
				return makeBlockNormalModelString(block);

			case OVERLAY:
				return makeBlockColoredLayerModelString(block);

			default:
				return "";
		}
	}

	private static String makeBlockNormalModelString(IDynamicBlock block)
	{
		String str = "{\n";
		str += "\t\"parent\": \"block/cube_all\",\n";
		str += "\t\"textures\": {\n";
		str += "\t\t\"all\": \"blocks/" + block.getTextureName() + "\"\n";
		str += "\t}\n";
		str += "}";

		return str;
	}

	private static String makeBlockColoredLayerModelString(IDynamicBlock block)
	{
		String background = "stone";

		String str = "{   \"parent\": \"block/block\",\r\n" + "    \"textures\": {\r\n" + "        \"all\": \"blocks/" + background + "\",\r\n" + "        \"overlay\": \"blocks/" + block.getTextureName() + "\",\r\n" + "        \"particle\": \"blocks/" + block.getTextureName() + "\"\r\n" + "    },\r\n" + "    \"elements\": [\r\n" + "        {   \"from\": [ 0, 0, 0 ],\r\n" + "            \"to\": [ 16, 16, 16 ],\r\n" + "            \"faces\": {\r\n" + "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"down\"  },\r\n" + "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"up\"    },\r\n" + "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"north\" },\r\n" + "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"south\" },\r\n" + "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"west\"  },\r\n" + "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"cullface\": \"east\"  }\r\n" + "            }\r\n" + "        },\r\n" + "        {   \"from\": [ 0, 0, 0 ],\r\n" + "            \"to\": [ 16, 16, 16 ],\r\n" + "            \"faces\": {\r\n" + "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"down\"  },\r\n" + "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"up\"    },\r\n" + "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"north\" },\r\n" + "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"south\" },\r\n" + "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"west\"  },\r\n" + "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#overlay\", \"tintindex\": 0, \"cullface\": \"east\"  }\r\n" + "            }\r\n" + "        }\r\n" + "    ]\r\n" + "}";
		return str;
	}

	private static String makeBlockColoredModelString(IDynamicBlock block)
	{
		String str = "{\n";
		str += "\t\"parent\": \"block/block\",\n";
		str += "\t\"textures\": {\n";
		str += "\t\t\"all\": \"blocks/" + block.getTextureName() + "\",\n";
		str += "\t\t\"particle\": \"blocks/" + block.getTextureName() + "\"\n";
		str += "\t},\n";
		str += "\"elements\": [\r\n" + "        {   \"from\": [ 0, 0, 0 ],\r\n" + "            \"to\": [ 16, 16, 16 ],\r\n" + "            \"faces\": {\r\n" + "                \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"down\" },\r\n" + "                \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"up\" },\r\n" + "                \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"north\" },\r\n" + "                \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"south\" },\r\n" + "                \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"west\" },\r\n" + "                \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"texture\": \"#all\", \"tintindex\": 0, \"cullface\": \"east\" }\r\n" + "            }\r\n" + "        }\r\n" + "    ]";
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
