package net.minecraft.item;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import fr.minecraftpp.anotation.Mod;
import fr.minecraftpp.item.tool.IToolMaterial;
import fr.minecraftpp.item.tool.IToolMaterial.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ItemPickaxe extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON_BLOCK = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
    
    @Mod("Minecraftpp")
    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.ROCK, Material.IRON, Material.ANVIL, Material.ICE, Material.PACKED_ICE, Material.RAIL);
    @Mod("Minecraftpp")
    private static final Map<Block, Integer> HARVEST_LEVEL = new HashMap<Block, Integer>() {{
    	put(Blocks.OBSIDIAN, 3);
    	put(Blocks.DIAMOND_BLOCK, 2);
    	put(Blocks.DIAMOND_ORE, 2);
    	put(Blocks.EMERALD_ORE, 2);
    	put(Blocks.EMERALD_BLOCK, 2);
    	put(Blocks.GOLD_BLOCK, 2);
    	put(Blocks.GOLD_ORE, 2);
    	put(Blocks.IRON_BLOCK, 1);
    	put(Blocks.IRON_ORE, 1);
    	put(Blocks.LAPIS_BLOCK, 1);
    	put(Blocks.LAPIS_ORE, 1);
    	put(Blocks.REDSTONE_ORE, 2);
    	put(Blocks.LIT_REDSTONE_ORE, 2);
    }}; 
    
    public ItemPickaxe(IToolMaterial material)
    {
        super(1.0F, -2.8F, material);
    }

    /**
     * Check whether this Item can harvest the given Block
     */    
    public boolean canHarvestBlock(IBlockState state)
    {
    	return EFFECTIVE_ON.contains(state.getMaterial()) && this.toolMaterial.getHarvestLevel() >= (HARVEST_LEVEL.containsKey(state.getBlock()) ? HARVEST_LEVEL.get(state.getBlock()) : state.getBlock().getRequiredHarvestLevel());
    }
    
    // TODO Réparer les différences
    @Mod("Minecraftpp")
    public void testHarvest()
    {
    	for(Field field : Blocks.class.getFields())
    	{
    		try
    		{
				if(field.get(null) instanceof Block)
				{
					Block block = (Block) field.get(null);
					Material material = block.getDefaultState().getMaterial();
					
					if((EFFECTIVE_ON_BLOCK.contains(block) || material == Material.IRON || material == Material.ANVIL || material == Material.ROCK) != this.canHarvestBlock(block.getDefaultState()))
					{
						System.err.println("---------- " + block.getUnlocalizedName());
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) { e.printStackTrace(); }
    	}
    }
    
    
    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        return EFFECTIVE_ON.contains(state.getMaterial()) ? this.efficiencyOnProperMaterial : 1.0F;
    }

	@Override
	public ToolType getToolType()
	{
		return ToolType.PICKAXE;
	}
}
