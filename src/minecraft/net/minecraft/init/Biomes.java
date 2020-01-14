package net.minecraft.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public abstract class Biomes
{
	public static final String DEFAULT;
	public static final String OCEAN;
	public static final String PLAINS;
	public static final String DESERT;
	public static final String EXTREME_HILLS;
	public static final String FOREST;
	public static final String TAIGA;
	public static final String SWAMPLAND;
	public static final String RIVER;
	public static final String HELL;

	/** Is the String used for sky world. */
	public static final String SKY;
	public static final String FROZEN_OCEAN;
	public static final String FROZEN_RIVER;
	public static final String ICE_PLAINS;
	public static final String ICE_MOUNTAINS;
	public static final String MUSHROOM_ISLAND;
	public static final String MUSHROOM_ISLAND_SHORE;

	/** Beach biome. */
	public static final String BEACH;

	/** Desert Hills biome. */
	public static final String DESERT_HILLS;

	/** Forest Hills biome. */
	public static final String FOREST_HILLS;

	/** Taiga Hills biome. */
	public static final String TAIGA_HILLS;

	/** Extreme Hills Edge biome. */
	public static final String EXTREME_HILLS_EDGE;

	/** Jungle String identifier */
	public static final String JUNGLE;
	public static final String JUNGLE_HILLS;
	public static final String JUNGLE_EDGE;
	public static final String DEEP_OCEAN;
	public static final String STONE_BEACH;
	public static final String COLD_BEACH;
	public static final String BIRCH_FOREST;
	public static final String BIRCH_FOREST_HILLS;
	public static final String ROOFED_FOREST;
	public static final String COLD_TAIGA;
	public static final String COLD_TAIGA_HILLS;
	public static final String REDWOOD_TAIGA;
	public static final String REDWOOD_TAIGA_HILLS;
	public static final String EXTREME_HILLS_WITH_TREES;
	public static final String SAVANNA;
	public static final String SAVANNA_PLATEAU;
	public static final String MESA;
	public static final String MESA_ROCK;
	public static final String MESA_CLEAR_ROCK;
	public static final String VOID;
	public static final String MUTATED_PLAINS;
	public static final String MUTATED_DESERT;
	public static final String MUTATED_EXTREME_HILLS;
	public static final String MUTATED_FOREST;
	public static final String MUTATED_TAIGA;
	public static final String MUTATED_SWAMPLAND;
	public static final String MUTATED_ICE_FLATS;
	public static final String MUTATED_JUNGLE;
	public static final String MUTATED_JUNGLE_EDGE;
	public static final String MUTATED_BIRCH_FOREST;
	public static final String MUTATED_BIRCH_FOREST_HILLS;
	public static final String MUTATED_ROOFED_FOREST;
	public static final String MUTATED_TAIGA_COLD;
	public static final String MUTATED_REDWOOD_TAIGA;
	public static final String MUTATED_REDWOOD_TAIGA_HILLS;
	public static final String MUTATED_EXTREME_HILLS_WITH_TREES;
	public static final String MUTATED_SAVANNA;
	public static final String MUTATED_SAVANNA_ROCK;
	public static final String MUTATED_MESA;
	public static final String MUTATED_MESA_ROCK;
	public static final String MUTATED_MESA_CLEAR_ROCK;

	public static Biome getBiome(String id)
	{
		Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(id));

		if (biome == null)
		{
			throw new IllegalStateException("Invalid String requested: " + id);
		}
		else
		{
			return biome;
		}
	}

	static
	{
		OCEAN = "ocean";
		DEFAULT = OCEAN;
		PLAINS = "plains";
		DESERT = "desert";
		EXTREME_HILLS = "extreme_hills";
		FOREST = "forest";
		TAIGA = "taiga";
		SWAMPLAND = "swampland";
		RIVER = "river";
		HELL = "hell";
		SKY = "sky";
		FROZEN_OCEAN = "frozen_ocean";
		FROZEN_RIVER = "frozen_river";
		ICE_PLAINS = "ice_flats";
		ICE_MOUNTAINS = "ice_mountains";
		MUSHROOM_ISLAND = "mushroom_island";
		MUSHROOM_ISLAND_SHORE = "mushroom_island_shore";
		BEACH = "beaches";
		DESERT_HILLS = "desert_hills";
		FOREST_HILLS = "forest_hills";
		TAIGA_HILLS = "taiga_hills";
		EXTREME_HILLS_EDGE = "smaller_extreme_hills";
		JUNGLE = "jungle";
		JUNGLE_HILLS = "jungle_hills";
		JUNGLE_EDGE = "jungle_edge";
		DEEP_OCEAN = "deep_ocean";
		STONE_BEACH = "stone_beach";
		COLD_BEACH = "cold_beach";
		BIRCH_FOREST = "birch_forest";
		BIRCH_FOREST_HILLS = "birch_forest_hills";
		ROOFED_FOREST = "roofed_forest";
		COLD_TAIGA = "taiga_cold";
		COLD_TAIGA_HILLS = "taiga_cold_hills";
		REDWOOD_TAIGA = "redwood_taiga";
		REDWOOD_TAIGA_HILLS = "redwood_taiga_hills";
		EXTREME_HILLS_WITH_TREES = "extreme_hills_with_trees";
		SAVANNA = "savanna";
		SAVANNA_PLATEAU = "savanna_rock";
		MESA = "mesa";
		MESA_ROCK = "mesa_rock";
		MESA_CLEAR_ROCK = "mesa_clear_rock";
		VOID = "void";
		MUTATED_PLAINS = "mutated_plains";
		MUTATED_DESERT = "mutated_desert";
		MUTATED_EXTREME_HILLS = "mutated_extreme_hills";
		MUTATED_FOREST = "mutated_forest";
		MUTATED_TAIGA = "mutated_taiga";
		MUTATED_SWAMPLAND = "mutated_swampland";
		MUTATED_ICE_FLATS = "mutated_ice_flats";
		MUTATED_JUNGLE = "mutated_jungle";
		MUTATED_JUNGLE_EDGE = "mutated_jungle_edge";
		MUTATED_BIRCH_FOREST = "mutated_birch_forest";
		MUTATED_BIRCH_FOREST_HILLS = "mutated_birch_forest_hills";
		MUTATED_ROOFED_FOREST = "mutated_roofed_forest";
		MUTATED_TAIGA_COLD = "mutated_taiga_cold";
		MUTATED_REDWOOD_TAIGA = "mutated_redwood_taiga";
		MUTATED_REDWOOD_TAIGA_HILLS = "mutated_redwood_taiga_hills";
		MUTATED_EXTREME_HILLS_WITH_TREES = "mutated_extreme_hills_with_trees";
		MUTATED_SAVANNA = "mutated_savanna";
		MUTATED_SAVANNA_ROCK = "mutated_savanna_rock";
		MUTATED_MESA = "mutated_mesa";
		MUTATED_MESA_ROCK = "mutated_mesa_rock";
		MUTATED_MESA_CLEAR_ROCK = "mutated_mesa_clear_rock";
	}
}
