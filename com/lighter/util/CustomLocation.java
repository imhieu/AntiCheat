package com.lighter.util;

import org.bukkit.*;

public class CustomLocation
{
    private double x;
    private final long timestamp;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getGroundDistanceTo(final CustomLocation customLocation) {
        return Math.sqrt(Math.pow(this.x - customLocation.x, 2.0) + Math.pow(this.z - customLocation.z, 2.0));
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public static CustomLocation fromBukkitLocation(final Location location) {
        return new CustomLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public CustomLocation(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.timestamp = System.currentTimeMillis();
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
}
