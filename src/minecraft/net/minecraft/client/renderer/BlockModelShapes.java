package net.minecraft.client.renderer;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class BlockModelShapes
{
	private final Map<IBlockState, IBakedModel> bakedModelStore = Maps.<IBlockState, IBakedModel>newIdentityHashMap();
	private BlockStateMapper blockStateMapper;
	private final ModelManager modelManager;

	public BlockModelShapes(ModelManager manager)
	{
		this.modelManager = manager;
	}

	public BlockStateMapper getBlockStateMapper()
	{
		return this.blockStateMapper;
	}

	public TextureAtlasSprite getTexture(IBlockState state)
	{
		Block block = state.getBlock();
		IBakedModel ibakedmodel = this.getModelForState(state);

		if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel())
		{
			if (block == Blocks.getBlock(Blocks.WALL_SIGN) || block == Blocks.getBlock(Blocks.STANDING_SIGN) || block == Blocks.getBlock(Blocks.CHEST) || block == Blocks.getBlock(Blocks.TRAPPED_CHEST) || block == Blocks.getBlock(Blocks.STANDING_BANNER) || block == Blocks.getBlock(Blocks.WALL_BANNER) || block == Blocks.getBlock(Blocks.BED))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
			}

			if (block == Blocks.getBlock(Blocks.ENDER_CHEST))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
			}

			if (block == Blocks.getBlock(Blocks.FLOWING_LAVA) || block == Blocks.getBlock(Blocks.LAVA))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
			}

			if (block == Blocks.getBlock(Blocks.FLOWING_WATER) || block == Blocks.getBlock(Blocks.WATER))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
			}

			if (block == Blocks.getBlock(Blocks.SKULL))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
			}

			if (block == Blocks.getBlock(Blocks.BARRIER))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
			}

			if (block == Blocks.getBlock(Blocks.STRUCTURE_VOID))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/structure_void");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_WHITE))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_white");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_ORANGE))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_orange");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_MAGENTA))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_magenta");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_LIGHT_BLUE))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_light_blue");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_YELLOW))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_yellow");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_LIME))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_lime");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_PINK))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_pink");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_GRAY))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_gray");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_SILVER))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_silver");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_CYAN))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_cyan");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_purple");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_BLUE))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_blue");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_BROWN))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_brown");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_GREEN))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_green");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_RED))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_red");
			}

			if (block == Blocks.getBlock(Blocks.SHULKER_BOX_BLACK))
			{
				return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_black");
			}
		}

		if (ibakedmodel == null)
		{
			ibakedmodel = this.modelManager.getMissingModel();
		}

		return ibakedmodel.getParticleTexture();
	}

	public IBakedModel getModelForState(IBlockState state)
	{
		IBakedModel ibakedmodel = this.bakedModelStore.get(state);

		if (ibakedmodel == null)
		{
			ibakedmodel = this.modelManager.getMissingModel();
		}

		return ibakedmodel;
	}

	public ModelManager getModelManager()
	{
		return this.modelManager;
	}

	public void reloadModels()
	{
		this.bakedModelStore.clear();

		for (Entry<IBlockState, ModelResourceLocation> entry : this.blockStateMapper.putAllStateModelLocations().entrySet())
		{
			this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
		}
	}

	public void registerBlockWithStateMapper(Block assoc, IStateMapper stateMapper)
	{
		this.blockStateMapper.registerBlockStateMapper(assoc, stateMapper);
	}

	public void registerBuiltInBlocks(Block... builtIns)
	{
		this.blockStateMapper.registerBuiltInBlocks(builtIns);
	}

	public void registerAllBlocks()
	{
		this.blockStateMapper = new BlockStateMapper();
		this.registerBuiltInBlocks(Blocks.getBlock(Blocks.AIR), Blocks.getBlock(Blocks.FLOWING_WATER), Blocks.getBlock(Blocks.WATER), Blocks.getBlock(Blocks.FLOWING_LAVA), Blocks.getBlock(Blocks.LAVA), Blocks.getBlock(Blocks.PISTON_EXTENSION), Blocks.getBlock(Blocks.CHEST), Blocks.getBlock(Blocks.ENDER_CHEST), Blocks.getBlock(Blocks.TRAPPED_CHEST), Blocks.getBlock(Blocks.STANDING_SIGN), Blocks.getBlock(Blocks.SKULL), Blocks.getBlock(Blocks.END_PORTAL), Blocks.getBlock(Blocks.BARRIER), Blocks.getBlock(Blocks.WALL_SIGN), Blocks.getBlock(Blocks.WALL_BANNER), Blocks.getBlock(Blocks.STANDING_BANNER), Blocks.getBlock(Blocks.END_GATEWAY), Blocks.getBlock(Blocks.STRUCTURE_VOID), Blocks.getBlock(Blocks.SHULKER_BOX_WHITE), Blocks.getBlock(Blocks.SHULKER_BOX_ORANGE), Blocks.getBlock(Blocks.SHULKER_BOX_MAGENTA), Blocks.getBlock(Blocks.SHULKER_BOX_LIGHT_BLUE), Blocks.getBlock(Blocks.SHULKER_BOX_YELLOW), Blocks.getBlock(Blocks.SHULKER_BOX_LIME), Blocks.getBlock(Blocks.SHULKER_BOX_PINK), Blocks.getBlock(Blocks.SHULKER_BOX_GRAY), Blocks.getBlock(Blocks.SHULKER_BOX_SILVER), Blocks.getBlock(Blocks.SHULKER_BOX_CYAN), Blocks.getBlock(Blocks.SHULKER_BOX), Blocks.getBlock(Blocks.SHULKER_BOX_BLUE), Blocks.getBlock(Blocks.SHULKER_BOX_BROWN), Blocks.getBlock(Blocks.SHULKER_BOX_GREEN), Blocks.getBlock(Blocks.SHULKER_BOX_RED), Blocks.getBlock(Blocks.SHULKER_BOX_BLACK), Blocks.getBlock(Blocks.BED));
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STONE), (new StateMap.Builder()).withName(BlockStone.VARIANT).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.PRISMARINE), (new StateMap.Builder()).withName(BlockPrismarine.VARIANT).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.LEAVES), (new StateMap.Builder()).withName(BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.LEAVES2), (new StateMap.Builder()).withName(BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.CACTUS), (new StateMap.Builder()).ignore(BlockCactus.AGE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.REEDS), (new StateMap.Builder()).ignore(BlockReed.AGE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.JUKEBOX), (new StateMap.Builder()).ignore(BlockJukebox.HAS_RECORD).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.COBBLESTONE_WALL), (new StateMap.Builder()).withName(BlockWall.VARIANT).withSuffix("_wall").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DOUBLE_PLANT), (new StateMap.Builder()).withName(BlockDoublePlant.VARIANT).ignore(BlockDoublePlant.FACING).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.OAK_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.SPRUCE_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.BIRCH_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.JUNGLE_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DARK_OAK_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.ACACIA_FENCE_GATE), (new StateMap.Builder()).ignore(BlockFenceGate.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.TRIPWIRE), (new StateMap.Builder()).ignore(BlockTripWire.DISARMED, BlockTripWire.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DOUBLE_WOODEN_SLAB), (new StateMap.Builder()).withName(BlockPlanks.VARIANT).withSuffix("_double_slab").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.WOODEN_SLAB), (new StateMap.Builder()).withName(BlockPlanks.VARIANT).withSuffix("_slab").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.TNT), (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.FIRE), (new StateMap.Builder()).ignore(BlockFire.AGE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.REDSTONE_WIRE), (new StateMap.Builder()).ignore(BlockRedstoneWire.POWER).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.OAK_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.SPRUCE_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.BIRCH_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.JUNGLE_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.ACACIA_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DARK_OAK_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.IRON_DOOR), (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.WOOL), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_wool").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.CARPET), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_carpet").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STAINED_HARDENED_CLAY), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STAINED_GLASS_PANE), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STAINED_GLASS), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_stained_glass").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.SANDSTONE), (new StateMap.Builder()).withName(BlockSandStone.TYPE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.RED_SANDSTONE), (new StateMap.Builder()).withName(BlockRedSandstone.TYPE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.TALLGRASS), (new StateMap.Builder()).withName(BlockTallGrass.TYPE).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.YELLOW_FLOWER), (new StateMap.Builder()).withName(((BlockFlower) Blocks.getBlock(Blocks.YELLOW_FLOWER)).getTypeProperty()).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.RED_FLOWER), (new StateMap.Builder()).withName(((BlockFlower) Blocks.getBlock(Blocks.RED_FLOWER)).getTypeProperty()).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STONE_SLAB), (new StateMap.Builder()).withName(BlockStoneSlab.VARIANT).withSuffix("_slab").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STONE_SLAB2), (new StateMap.Builder()).withName(BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.MONSTER_EGG), (new StateMap.Builder()).withName(BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.STONEBRICK), (new StateMap.Builder()).withName(BlockStoneBrick.VARIANT).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DISPENSER), (new StateMap.Builder()).ignore(BlockDispenser.TRIGGERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DROPPER), (new StateMap.Builder()).ignore(BlockDispenser.TRIGGERED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.LOG), (new StateMap.Builder()).withName(BlockOldLog.VARIANT).withSuffix("_log").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.LOG2), (new StateMap.Builder()).withName(BlockNewLog.VARIANT).withSuffix("_log").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.PLANKS), (new StateMap.Builder()).withName(BlockPlanks.VARIANT).withSuffix("_planks").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.SAPLING), (new StateMap.Builder()).withName(BlockSapling.TYPE).withSuffix("_sapling").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.SAND), (new StateMap.Builder()).withName(BlockSand.VARIANT).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.HOPPER), (new StateMap.Builder()).ignore(BlockHopper.ENABLED).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.FLOWER_POT), (new StateMap.Builder()).ignore(BlockFlowerPot.LEGACY_DATA).build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.CONCRETE), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_concrete").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.CONCRETE_POWDER), (new StateMap.Builder()).withName(BlockColored.COLOR).withSuffix("_concrete_powder").build());
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.QUARTZ_BLOCK), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				BlockQuartz.EnumType blockquartz$enumtype = state.getValue(BlockQuartz.VARIANT);

				switch (blockquartz$enumtype)
				{
					case DEFAULT:
					default:
						return new ModelResourceLocation("quartz_block", "normal");

					case CHISELED:
						return new ModelResourceLocation("chiseled_quartz_block", "normal");

					case LINES_Y:
						return new ModelResourceLocation("quartz_column", "axis=y");

					case LINES_X:
						return new ModelResourceLocation("quartz_column", "axis=x");

					case LINES_Z:
						return new ModelResourceLocation("quartz_column", "axis=z");
				}
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DEADBUSH), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation("dead_bush", "normal");
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.PUMPKIN_STEM), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockStem.FACING) != EnumFacing.UP)
				{
					map.remove(BlockStem.AGE);
				}

				return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), this.getPropertyString(map));
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.MELON_STEM), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());

				if (state.getValue(BlockStem.FACING) != EnumFacing.UP)
				{
					map.remove(BlockStem.AGE);
				}

				return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), this.getPropertyString(map));
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DIRT), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());
				String s = BlockDirt.VARIANT.getName((BlockDirt.DirtType) map.remove(BlockDirt.VARIANT));

				if (BlockDirt.DirtType.PODZOL != state.getValue(BlockDirt.VARIANT))
				{
					map.remove(BlockDirt.SNOWY);
				}

				return new ModelResourceLocation(s, this.getPropertyString(map));
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DOUBLE_STONE_SLAB), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());
				String s = BlockStoneSlab.VARIANT.getName((BlockStoneSlab.EnumType) map.remove(BlockStoneSlab.VARIANT));
				map.remove(BlockStoneSlab.SEAMLESS);
				String s1 = state.getValue(BlockStoneSlab.SEAMLESS).booleanValue() ? "all" : "normal";
				return new ModelResourceLocation(s + "_double_slab", s1);
			}
		});
		this.registerBlockWithStateMapper(Blocks.getBlock(Blocks.DOUBLE_STONE_SLAB2), new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());
				String s = BlockStoneSlabNew.VARIANT.getName((BlockStoneSlabNew.EnumType) map.remove(BlockStoneSlabNew.VARIANT));
				map.remove(BlockStoneSlab.SEAMLESS);
				String s1 = state.getValue(BlockStoneSlabNew.SEAMLESS).booleanValue() ? "all" : "normal";
				return new ModelResourceLocation(s + "_double_slab", s1);
			}
		});
	}
}
