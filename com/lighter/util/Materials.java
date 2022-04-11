package com.lighter.util;

import org.bukkit.*;

public class Materials
{
    private static final int[] MATERIAL_FLAGS;
    
    static {
        MATERIAL_FLAGS = new int[256];
        for (int i = 0; i < Materials.MATERIAL_FLAGS.length; ++i) {
            final Material material = Material.values()[i];
            if (material.isSolid()) {
                final int[] material_FLAGS = Materials.MATERIAL_FLAGS;
                final int n = i;
                material_FLAGS[n] |= 0x1;
            }
            if (material.name().endsWith("_STAIRS")) {
                final int[] material_FLAGS2 = Materials.MATERIAL_FLAGS;
                final int n2 = i;
                material_FLAGS2[n2] |= 0x10;
            }
        }
        Materials.MATERIAL_FLAGS[Material.SIGN_POST.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.WALL_SIGN.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_OFF.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_ON.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.CARPET.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.SNOW.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ANVIL.getId()] = 1;
        final int id = Material.WATER.getId();
        final int[] material_FLAGS3 = Materials.MATERIAL_FLAGS;
        final int n3 = id;
        material_FLAGS3[n3] |= 0x2;
        final int id2 = Material.STATIONARY_WATER.getId();
        final int[] material_FLAGS4 = Materials.MATERIAL_FLAGS;
        final int n4 = id2;
        material_FLAGS4[n4] |= 0x2;
        final int id3 = Material.LAVA.getId();
        final int[] material_FLAGS5 = Materials.MATERIAL_FLAGS;
        final int n5 = id3;
        material_FLAGS5[n5] |= 0x2;
        final int id4 = Material.STATIONARY_LAVA.getId();
        final int[] material_FLAGS6 = Materials.MATERIAL_FLAGS;
        final int n6 = id4;
        material_FLAGS6[n6] |= 0x2;
        final int id5 = Material.LADDER.getId();
        final int[] material_FLAGS7 = Materials.MATERIAL_FLAGS;
        final int n7 = id5;
        material_FLAGS7[n7] |= 0x5;
        final int id6 = Material.VINE.getId();
        final int[] material_FLAGS8 = Materials.MATERIAL_FLAGS;
        final int n8 = id6;
        material_FLAGS8[n8] |= 0x5;
        final int id7 = Material.FENCE.getId();
        final int[] material_FLAGS9 = Materials.MATERIAL_FLAGS;
        final int n9 = id7;
        material_FLAGS9[n9] |= 0x8;
        final int id8 = Material.FENCE_GATE.getId();
        final int[] material_FLAGS10 = Materials.MATERIAL_FLAGS;
        final int n10 = id8;
        material_FLAGS10[n10] |= 0x8;
        final int id9 = Material.COBBLE_WALL.getId();
        final int[] material_FLAGS11 = Materials.MATERIAL_FLAGS;
        final int n11 = id9;
        material_FLAGS11[n11] |= 0x8;
        final int id10 = Material.NETHER_FENCE.getId();
        final int[] material_FLAGS12 = Materials.MATERIAL_FLAGS;
        final int n12 = id10;
        material_FLAGS12[n12] |= 0x8;
    }
    
    public static boolean checkFlag(final Material material, final int n) {
        return (Materials.MATERIAL_FLAGS[material.getId()] & n) == n;
    }
}
