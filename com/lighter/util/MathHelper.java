package com.lighter.util;

public class MathHelper
{
    public static final float[] SIN_TABLE_FAST;
    public static final float[] SIN_TABLE;
    public static boolean fastMath;
    public static final float field_180189_a;
    
    static {
        field_180189_a = sqrt_float(2.0f);
        SIN_TABLE_FAST = new float[4096];
        MathHelper.fastMath = false;
        SIN_TABLE = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.SIN_TABLE[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        for (int j = 0; j < 4096; ++j) {
            MathHelper.SIN_TABLE_FAST[j] = (float)Math.sin((j + 0.5f) / 4096.0f * 6.2831855f);
        }
        for (int k = 0; k < 360; k += 90) {
            MathHelper.SIN_TABLE_FAST[(int)(k * 11.377778f) & 0xFFF] = (float)Math.sin(k * 0.017453292f);
        }
    }
    
    public static float sqrt_double(final double n) {
        return (float)Math.sqrt(n);
    }
    
    public static float abs(final float n) {
        return (n >= 0.0f) ? n : (-n);
    }
    
    public static float sqrt_float(final float n) {
        return (float)Math.sqrt(n);
    }
}
