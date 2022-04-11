package com.lighter.util;

import com.lighter.data.*;
import java.util.*;
import com.google.common.base.*;

public class MathUtil
{
    public static float[] getRotationFromPosition(final PlayerLocation playerLocation, final PlayerLocation playerLocation2) {
        final double n = playerLocation2.getX() - playerLocation.getX();
        final double n2 = playerLocation2.getZ() - playerLocation.getZ();
        return new float[] { (float)(Math.atan2(n2, n) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.atan2(playerLocation2.getY() + 0.81 - playerLocation.getY() - 1.2, (float)Math.sqrt(n * n + n2 * n2)) * 180.0 / 3.141592653589793) };
    }
    
    public static boolean onGround(final double n) {
        return n % 0.015625 == 0.0;
    }
    
    public static double deviation(final Iterable<? extends Number> iterable) {
        return Math.sqrt(deviationSquared(iterable));
    }
    
    public static double deviationSquared(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        int n2 = 0;
        final Iterator<? extends Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            n += ((Number)iterator.next()).doubleValue();
            ++n2;
        }
        final double n3 = n / n2;
        double n4 = 0.0;
        final Iterator<? extends Number> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            n4 += Math.pow(((Number)iterator2.next()).doubleValue() - n3, 2.0);
        }
        return n4 / (n2 - 1);
    }
    
    public static double getHorizontalDistance(final PlayerLocation playerLocation, final PlayerLocation playerLocation2) {
        final double n = playerLocation2.getX() - playerLocation.getX();
        final double n2 = playerLocation2.getZ() - playerLocation.getZ();
        return Math.sqrt(n * n + n2 * n2);
    }
    
    private static long absGCD(long n, long n2) {
        while (n2 > 0L) {
            final long n3 = n2;
            n2 = n % n2;
            n = n3;
        }
        return n;
    }
    
    public static double getDistanceBetweenAngles360(final double n, final double n2) {
        final double abs = Math.abs(n % 360.0 - n2 % 360.0);
        return Math.abs(Math.min(360.0 - abs, abs));
    }
    
    public static double getVerticalDistance(final PlayerLocation playerLocation, final PlayerLocation playerLocation2) {
        return Math.abs(playerLocation.getY() - playerLocation2.getY());
    }
    
    public static float averageFloat(final List<Float> list) {
        float n = 0.0f;
        final Iterator<Float> iterator = list.iterator();
        while (iterator.hasNext()) {
            n += iterator.next();
        }
        if (list.size() > 0) {
            return n / list.size();
        }
        return 0.0f;
    }
    
    public static double variance(final Number n, final Iterable<? extends Number> iterable) {
        return Math.sqrt(varianceSquared(n, iterable));
    }
    
    public static double varianceSquared(final Number n, final Iterable<? extends Number> iterable) {
        double n2 = 0.0;
        int n3 = 0;
        final Iterator<? extends Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            n2 += Math.pow(((Number)iterator.next()).doubleValue() - n.doubleValue(), 2.0);
            ++n3;
        }
        return n2 / (n3 - 1);
    }
    
    public static double hypotSquared(final double... array) {
        double n = 0.0;
        for (int length = array.length, i = 0; i < length; ++i) {
            n += Math.pow(array[i], 2.0);
        }
        return n;
    }
    
    public static Double getMinimumAngle(final PlayerLocation playerLocation, final PlayerLocation... array) {
        Double value = null;
        for (final PlayerLocation playerLocation2 : array) {
            final double distanceBetweenAngles360 = getDistanceBetweenAngles360(playerLocation.getYaw(), (float)(Math.atan2(playerLocation2.getZ() - playerLocation.getZ(), playerLocation2.getX() - playerLocation.getX()) * 180.0 / 3.141592653589793) - 90.0f);
            if (value == null || value > distanceBetweenAngles360) {
                value = distanceBetweenAngles360;
            }
        }
        return value;
    }
    
    public static double getDistanceBetweenAngles(final float n, final float n2) {
        float n3 = Math.abs(n - n2) % 360.0f;
        if (n3 > 180.0f) {
            n3 = 360.0f - n3;
        }
        return n3;
    }
    
    public static long gcd(final long n, final long n2, final long n3) {
        return (n3 <= n) ? n2 : gcd(n, n3, n2 % n3);
    }
    
    public static int pingFormula(final long n) {
        return (int)Math.ceil(n / 50.0);
    }
    
    public static boolean isRoughlyEqual(final double n, final double n2, final double n3) {
        return Math.abs(n - n2) < n3;
    }
    
    public static double hypot(final double... array) {
        return Math.sqrt(hypotSquared(array));
    }
    
    public static double lowestAbs(final Number... array) {
        return lowestAbs(Arrays.asList(array));
    }
    
    public static double sigmoid(final double n) {
        return 1.0 / (1.0 + Math.exp(-n));
    }
    
    public static double lowestAbs(final Iterable<? extends Number> iterable) {
        Double value = null;
        for (final Number n : iterable) {
            if (value == null || Math.abs(n.doubleValue()) < Math.abs(value)) {
                value = n.doubleValue();
            }
        }
        return value;
    }
    
    public static double highest(final Iterable<? extends Number> iterable) {
        Double value = null;
        for (final Number n : iterable) {
            if (value != null && n.doubleValue() <= value) {
                continue;
            }
            value = n.doubleValue();
        }
        return (double)Objects.firstNonNull((Object)value, (Object)0.0);
    }
    
    public static double total(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        final Iterator<? extends Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            n += ((Number)iterator.next()).doubleValue();
        }
        return n;
    }
    
    public static double gcd(final double n, final double n2, final double n3) {
        return (n3 <= n) ? n2 : gcd(n, n3, n2 % n3);
    }
    
    public static long lcd(final long n, final long n2) {
        return n * (n2 / absGCD(n, n2));
    }
    
    public static double average(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        int n2 = 0;
        final Iterator<? extends Number> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            n += ((Number)iterator.next()).doubleValue();
            ++n2;
        }
        return n / n2;
    }
    
    public static double getDelta(final double n, final double n2) {
        return Math.abs(n - n2);
    }
}
