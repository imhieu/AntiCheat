package com.lighter.data;

import com.lighter.util.*;

public class PlayerLocation
{
    private double y;
    private float yaw;
    private long timestamp;
    private double x;
    private double z;
    private float pitch;
    private Boolean onGround;
    private int tickTime;
    
    public Boolean getOnGround() {
        return this.onGround;
    }
    
    public double distanceXZSquared(final PlayerLocation playerLocation) {
        return Math.pow(this.x - playerLocation.x, 2.0) + Math.pow(this.z - playerLocation.z, 2.0);
    }
    
    public void setOnGround(final Boolean onGround) {
        this.onGround = onGround;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    public double getZ() {
        return this.z;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final PlayerLocation playerLocation = (PlayerLocation)o;
            return this.timestamp == playerLocation.timestamp && this.tickTime == playerLocation.tickTime && Double.compare(playerLocation.x, this.x) == 0 && Double.compare(playerLocation.y, this.y) == 0 && Double.compare(playerLocation.z, this.z) == 0 && Float.compare(playerLocation.yaw, this.yaw) == 0 && Float.compare(playerLocation.pitch, this.pitch) == 0;
        }
        return false;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    public Cuboid hitbox() {
        return new Cuboid(this.x, this.y, this.z).add(new Cuboid(-0.3, 0.3, 0.0, 1.8, -0.3, 0.3));
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public PlayerLocation clone() {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x, this.y, this.z, this.yaw, this.pitch, this.onGround);
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public double getX() {
        return this.x;
    }
    
    public PlayerLocation add(final double n, final double n2, final double n3) {
        return new PlayerLocation(this.timestamp, this.tickTime, this.x + n, this.y + n2, this.z + n3, this.yaw, this.pitch, this.onGround);
    }
    
    public int getTickTime() {
        return this.tickTime;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public boolean sameLocation(final PlayerLocation playerLocation) {
        return this.x == playerLocation.x && this.y == playerLocation.y && this.z == playerLocation.z;
    }
    
    public boolean sameDirection(final PlayerLocation playerLocation) {
        return this.yaw == playerLocation.yaw && this.pitch == playerLocation.pitch;
    }
    
    public Cuboid to(final PlayerLocation playerLocation) {
        return new Cuboid(Math.min(this.x, playerLocation.x), Math.max(this.x, playerLocation.x), Math.min(this.y, playerLocation.y), Math.max(this.y, playerLocation.y), Math.min(this.z, playerLocation.z), Math.max(this.z, playerLocation.z));
    }
    
    public PlayerLocation(final long timestamp, final int tickTime, final double x, final double y, final double z, final float yaw, final float pitch, final Boolean onGround) {
        this.timestamp = timestamp;
        this.tickTime = tickTime;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    @Override
    public int hashCode() {
        final int n = 31 * (int)(this.timestamp ^ this.timestamp >>> 32) + this.tickTime;
        final long doubleToLongBits = Double.doubleToLongBits(this.x);
        final int n2 = 31 * n + (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
        final long doubleToLongBits2 = Double.doubleToLongBits(this.y);
        final int n3 = 31 * n2 + (int)(doubleToLongBits2 ^ doubleToLongBits2 >>> 32);
        final long doubleToLongBits3 = Double.doubleToLongBits(this.z);
        return 31 * (31 * (31 * n3 + (int)(doubleToLongBits3 ^ doubleToLongBits3 >>> 32)) + ((this.yaw != 0.0f) ? Float.floatToIntBits(this.yaw) : 0)) + ((this.pitch != 0.0f) ? Float.floatToIntBits(this.pitch) : 0);
    }
    
    public void setTickTime(final int tickTime) {
        this.tickTime = tickTime;
    }
}
