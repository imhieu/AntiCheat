package com.lighter.util;

import org.bukkit.entity.*;
import java.lang.reflect.*;
import java.util.*;
import org.bukkit.*;

public class ReflectionUtil
{
    private static final Method getBlocks;
    private static final Class<?> CraftPlayer;
    private static final Class<?> Entity;
    private static final String serverVersion;
    private static final Class<?> CraftWorld;
    public static Class<?> EntityPlayer;
    private static final Class<?> World;
    private static final Method getBlocks1_12;
    
    public static Object getBoundingBox(final Player player) {
        return isBukkitVerison("1_7") ? getInvokedField(getField(ReflectionUtil.Entity, "boundingBox"), getEntityPlayer(player)) : getInvokedMethod(getMethod(ReflectionUtil.EntityPlayer, "getBoundingBox", (Class<?>[])new Class[0]), getEntityPlayer(player), new Object[0]);
    }
    
    public static Class<?> getClass(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Object modifyBoundingBox(final Object o, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = (double)getInvokedField(getField(o.getClass(), "a"), o) + n;
        final double n8 = (double)getInvokedField(getField(o.getClass(), "b"), o) + n2;
        final double n9 = (double)getInvokedField(getField(o.getClass(), "c"), o) + n3;
        final double n10 = (double)getInvokedField(getField(o.getClass(), "d"), o) + n4;
        final double n11 = (double)getInvokedField(getField(o.getClass(), "e"), o) + n5;
        final double n12 = (double)getInvokedField(getField(o.getClass(), "f"), o) + n6;
        try {
            return getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(n7, n8, n9, n10, n11, n12);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Class<?> getCBClass(final String s) {
        return getClass(String.valueOf(new StringBuilder().append("org.bukkit.craftbukkit.").append(ReflectionUtil.serverVersion).append(".").append(s)));
    }
    
    public static Object getInvokedField(final Field field, final Object o) {
        try {
            field.setAccessible(true);
            return field.get(o);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean isNewVersion() {
        return isBukkitVerison("1_9") || isBukkitVerison("1_1");
    }
    
    public static Field getField(final Class<?> clazz, final String s) {
        try {
            final Field field = clazz.getField(s);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean isBukkitVerison(final String s) {
        return ReflectionUtil.serverVersion.contains(s);
    }
    
    public static Object getInvokedMethod(final Method method, final Object o, final Object... array) {
        try {
            method.setAccessible(true);
            return method.invoke(o, array);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Collection<?> getCollidingBlocks(final Player player, final Object o) {
        final Object invokedMethod = getInvokedMethod(getMethod(ReflectionUtil.CraftWorld, "getHandle", (Class<?>[])new Class[0]), player.getWorld(), new Object[0]);
        return (Collection<?>)(isNewVersion() ? getInvokedMethod(ReflectionUtil.getBlocks1_12, invokedMethod, null, o) : getInvokedMethod(ReflectionUtil.getBlocks, invokedMethod, o));
    }
    
    static {
        serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        ReflectionUtil.EntityPlayer = getNMSClass("EntityPlayer");
        Entity = getNMSClass("Entity");
        CraftPlayer = getCBClass("entity.CraftPlayer");
        CraftWorld = getCBClass("CraftWorld");
        World = getNMSClass("World");
        getBlocks = getMethod(ReflectionUtil.World, "a", getNMSClass("AxisAlignedBB"));
        getBlocks1_12 = getMethod(ReflectionUtil.World, "getCubes", getNMSClass("Entity"), getNMSClass("AxisAlignedBB"));
    }
    
    public static Class<?> getNMSClass(final String s) {
        return getClass(String.valueOf(new StringBuilder().append("net.minecraft.server.").append(ReflectionUtil.serverVersion).append(".").append(s)));
    }
    
    public static Object getEntityPlayer(final Player player) {
        return getInvokedMethod(getMethod(ReflectionUtil.CraftPlayer, "getHandle", (Class<?>[])new Class[0]), player, new Object[0]);
    }
    
    public static Method getMethod(final Class<?> clazz, final String s, final Class<?>... array) {
        try {
            final Method method = clazz.getMethod(s, (Class[])array);
            method.setAccessible(true);
            return method;
        }
        catch (NoSuchMethodException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Object expandBoundingBox(final Object o, final double n, final double n2, final double n3) {
        return getInvokedMethod(getMethod(o.getClass(), "grow", Double.TYPE, Double.TYPE, Double.TYPE), o, n, n2, n3);
    }
}
