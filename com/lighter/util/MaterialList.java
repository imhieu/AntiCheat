package com.lighter.util;

import java.util.*;
import org.bukkit.*;
import com.google.common.collect.*;

public class MaterialList
{
    public static final List<Object> STAIRS;
    public static final List<Object> ICE;
    public static final Set<Object> BAD_VELOCITY;
    public static final List<Object> SLABS;
    public static final Set<Object> INVALID_SHAPE;
    
    static {
        INVALID_SHAPE = (Set)ImmutableSet.of((Object)Material.ACACIA_STAIRS, (Object)Material.BIRCH_WOOD_STAIRS, (Object)Material.BRICK_STAIRS, (Object)Material.COBBLESTONE_STAIRS, (Object)Material.DARK_OAK_STAIRS, (Object)Material.JUNGLE_WOOD_STAIRS, (Object[])new Material[] { Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS, Material.SNOW, Material.STONE_SLAB2, Material.STEP, Material.WOOD_STEP, Material.CARPET, Material.CHEST, Material.ENDER_CHEST, Material.TRAPPED_CHEST, Material.ENDER_PORTAL_FRAME, Material.TRAP_DOOR, Material.SLIME_BLOCK, Material.WATER_LILY, Material.REDSTONE_COMPARATOR, Material.TRAP_DOOR, Material.CAULDRON, Material.STATIONARY_WATER, Material.FENCE, Material.HOPPER, Material.REDSTONE_COMPARATOR });
        BAD_VELOCITY = (Set)ImmutableSet.of((Object)Material.WATER, (Object)Material.STATIONARY_WATER, (Object)Material.LAVA, (Object)Material.STATIONARY_LAVA, (Object)Material.WEB, (Object)Material.SLIME_BLOCK, (Object[])new Material[] { Material.LADDER, Material.VINE, Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE, Material.SNOW, Material.FENCE, Material.STONE_SLAB2, Material.SOUL_SAND, Material.CHEST });
        ICE = (List)ImmutableList.of((Object)Material.PACKED_ICE, (Object)Material.ICE);
        SLABS = (List)ImmutableList.of((Object)Material.STONE_SLAB2, (Object)Material.STEP, (Object)Material.WOOD_STEP);
        STAIRS = (List)ImmutableList.of((Object)Material.ACACIA_STAIRS, (Object)Material.BIRCH_WOOD_STAIRS, (Object)Material.BRICK_STAIRS, (Object)Material.COBBLESTONE_STAIRS, (Object)Material.DARK_OAK_STAIRS, (Object)Material.JUNGLE_WOOD_STAIRS, (Object)Material.NETHER_BRICK_STAIRS, (Object)Material.QUARTZ_STAIRS, (Object)Material.SANDSTONE_STAIRS, (Object)Material.RED_SANDSTONE_STAIRS, (Object)Material.SMOOTH_STAIRS, (Object)Material.SPRUCE_WOOD_STAIRS, (Object[])new Material[] { Material.WOOD_STAIRS });
    }
}
