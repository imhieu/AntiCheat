package com.lighter.util;

import org.bukkit.util.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

public class AABB
{
    private final Vector max;
    private final Vector min;
    
    public boolean collides(final Ray ray, double n, double n2) {
        for (int i = 0; i < 3; ++i) {
            final double n3 = 1.0 / ray.direction(i);
            double n4 = (this.min(i) - ray.origin(i)) * n3;
            double n5 = (this.max(i) - ray.origin(i)) * n3;
            if (n3 < 0.0) {
                final double n6 = n4;
                n4 = n5;
                n5 = n6;
            }
            n = ((n4 > n) ? n4 : n);
            n2 = ((n5 < n2) ? n5 : n2);
            if (n2 <= n) {
                return false;
            }
        }
        return true;
    }
    
    public Vector getMin() {
        return this.min;
    }
    
    private Vector getMin(final Player player) {
        return player.getLocation().toVector().add(new Vector(-0.3, 0.0, -0.3));
    }
    
    private AABB(final EntityPlayer entityPlayer) {
        this.min = this.getMin(entityPlayer);
        this.max = this.getMax(entityPlayer);
    }
    
    public static AABB from(final Player player) {
        return new AABB(player);
    }
    
    public double min(final int n) {
        switch (n) {
            case 0: {
                return this.min.getX();
            }
            case 1: {
                return this.min.getY();
            }
            case 2: {
                return this.min.getZ();
            }
            default: {
                return 0.0;
            }
        }
    }
    
    private Vector getMin(final EntityPlayer entityPlayer) {
        return entityPlayer.getBukkitEntity().getLocation().toVector().add(new Vector(-0.3, 0.0, -0.3));
    }
    
    private Vector getMax(final EntityPlayer entityPlayer) {
        return entityPlayer.getBukkitEntity().getLocation().toVector().add(new Vector(0.3, 1.8, 0.3));
    }
    
    public Vector getMax() {
        return this.max;
    }
    
    private AABB(final Player player) {
        this.min = this.getMin(player);
        this.max = this.getMax(player);
    }
    
    public double collidesD(final Ray ray, double n, double n2) {
        for (int i = 0; i < 3; ++i) {
            final double n3 = 1.0 / ray.direction(i);
            double n4 = (this.min(i) - ray.origin(i)) * n3;
            double n5 = (this.max(i) - ray.origin(i)) * n3;
            if (n3 < 0.0) {
                final double n6 = n4;
                n4 = n5;
                n5 = n6;
            }
            n = ((n4 > n) ? n4 : n);
            n2 = ((n5 < n2) ? n5 : n2);
            if (n2 <= n) {
                return -1.0;
            }
        }
        return n;
    }
    
    private Vector getMax(final Player player) {
        return player.getLocation().toVector().add(new Vector(0.3, 1.8, 0.3));
    }
    
    public double max(final int n) {
        switch (n) {
            case 0: {
                return this.max.getX();
            }
            case 1: {
                return this.max.getY();
            }
            case 2: {
                return this.max.getZ();
            }
            default: {
                return 0.0;
            }
        }
    }
    
    public AABB(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        this.min = new Vector(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6));
        this.max = new Vector(Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6));
    }
    
    public AABB(final Vector vector, final Vector vector2) {
        this(vector.getX(), vector.getY(), vector.getZ(), vector2.getX(), vector2.getY(), vector2.getZ());
    }
    
    public static AABB from(final EntityPlayer entityPlayer) {
        return new AABB(entityPlayer);
    }
    
    public boolean contains(final Location location) {
        return location.getX() <= this.max.getX() && location.getY() <= this.max.getY() && location.getZ() <= this.max.getZ() && location.getX() >= this.min.getX() && location.getY() >= this.min.getY() && location.getZ() >= this.min.getZ();
    }
}
