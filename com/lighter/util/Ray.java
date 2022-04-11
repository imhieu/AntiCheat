package com.lighter.util;

import org.bukkit.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Ray
{
    private final Vector origin;
    private final Vector direction;
    
    public static Location getPoint(final Player player, final double n) {
        final Vector point = from(player).getPoint(n);
        return new Location(player.getWorld(), point.getX(), point.getY(), point.getZ());
    }
    
    public Ray level() {
        return new Ray(this.origin, this.direction.setY(0).normalize());
    }
    
    public Vector getPoint(final double n) {
        return this.direction.clone().normalize().multiply(n).add(this.origin);
    }
    
    public double direction(final int n) {
        switch (n) {
            case 0: {
                return this.direction.getX();
            }
            case 1: {
                return this.direction.getY();
            }
            case 2: {
                return this.direction.getZ();
            }
            default: {
                return 0.0;
            }
        }
    }
    
    public static Ray from(final Player player) {
        return new Ray(player.getEyeLocation().toVector(), player.getLocation().getDirection());
    }
    
    public Vector getOrigin() {
        return this.origin;
    }
    
    public Vector getDirection() {
        return this.direction;
    }
    
    public static Vector right(final Vector vector) {
        final Vector normalize = vector.clone().setY(0).normalize();
        final double x = normalize.getX();
        normalize.setX(normalize.getZ());
        normalize.setZ(-x);
        return normalize;
    }
    
    public Ray(final Vector origin, final Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }
    
    public double origin(final int n) {
        switch (n) {
            case 0: {
                return this.origin.getX();
            }
            case 1: {
                return this.origin.getY();
            }
            case 2: {
                return this.origin.getZ();
            }
            default: {
                return 0.0;
            }
        }
    }
}
