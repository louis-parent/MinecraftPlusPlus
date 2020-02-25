package fr.minecraftpp.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import fr.minecraftpp.manager.SetManager;
import fr.minecraftpp.manager.block.ModBlock;
import fr.minecraftpp.manager.block.OreRegistry;
import fr.minecraftpp.manager.crafting.ModCraftingManager;
import fr.minecraftpp.manager.item.ModItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;

public class ModBootstrap
{

	private static List<IResourceManagerReloadListener> toReload = new ArrayList<IResourceManagerReloadListener>();

	public static void preBootstrap()
	{
		SetManager.generateOre();
		SetManager.register();
	}

	public static void postBootstrap()
	{

		SetManager.setupEffects();
		SetManager.generateRecipes();

		ModItem.addEnchantable();
	}

	public static void redo(Minecraft mc)
	{
		LogManager.getLogger().info("Loading Bootstrap");
		
		IntCache.clear();
		
		ModItem.resetRegistry();
		ModBlock.resetRegistry();
		ModCraftingManager.resetRegistry();
		OreRegistry.resetRegistry();

		
		preBootstrap();
		
		Block.registerBlocks();
		Item.registerItems();

		BlockFire.init();
		PotionHelper.init();
		Bootstrap.registerDispenserBehaviors();
		Biome.registerBiomes();

		postBootstrap();

		if (!CraftingManager.buildAllRecipes())
		{
			Bootstrap.field_194219_b = true;
			LogManager.getLogger().error("Errors with built-in recipes!");
		}

		RecipeBookClient.init();

		StatList.init();

		mc.getModelManager().getBlockModelShapes().registerAllBlocks();
		doAllReloads(mc.getMcResourceManager());

		mc.setBlockColors(BlockColors.init());
		mc.setItemColors(ItemColors.init(mc.getBlockColors()));
		mc.setRenderItem(new RenderItem(mc.getRenderEngine(), mc.getModelManager(), mc.getItemColors()));
		mc.setRenderManager(new RenderManager(mc.getRenderEngine(), mc.getRenderItem()));
		mc.func_193986_ar();
		mc.getMcResourceManager().registerReloadListener(mc.getRenderItem());
		mc.getBlockRenderDispatcher().getFluidRenderer().initAtlasSprites();
		mc.refreshResources();
	}

	public static void addRessourceManger(IResourceManagerReloadListener reloadListener)
	{
		toReload.add(reloadListener);
	}

	private static void doAllReloads(IReloadableResourceManager iReloadableResourceManager)
	{
		for (IResourceManagerReloadListener listener : toReload)
		{
			listener.onResourceManagerReload(iReloadableResourceManager);
		}
	}
}
